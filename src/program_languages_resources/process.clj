(ns program-languages-resources.process
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.data.json :as json]))

(def data-source "resources/data/programming-languages-resources.csv")

(defn format-key
  "Handle keys that countain a space"
  [key]
  ((comp 
    keyword
    clojure.string/lower-case
    #(clojure.string/replace % #" " "_"))
   key))

(defn split-tutorials-links [d]
  (update d :tutorials
          (fn [s] (mapv #(.trim %)
                        (clojure.string/split s #",")))))

(defn clean-clj-data [data]
  (mapv split-tutorials-links
   data))

(defn csv->clj-data
  "Load csv data into clojure"
  [csv-input]
  (let [data-seq (with-open [in-file (io/reader csv-input)]
               (vec (csv/read-csv in-file)))]
    (mapv #(zipmap (mapv format-key                          
                        (first data-seq)) %)
         (rest data-seq))))

(defn clj-data->edn
  "Convert clojure data to edn file"
  [clj-input file-out]
  (spit file-out (prn-str clj-input)))


(defn csv->edn [in-path out-path]
  (-> in-path
      csv->clj-data
      clean-clj-data
      (clj-data->edn out-path)))


(comment
  (csv->edn
   "resources/data/programming-languages-resources.csv"
   "resources/data/programming-languages-resources.edn"))



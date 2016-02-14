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

(defn string->vec-of-strings
  "Takes in a map of data for one programming language.
  Returns it with the tutorials data update from one 
  string to a vector of string(s)."
  [data-map key]
  (update data-map key
          (fn [tutorials-str] (mapv #(.trim %)
                        (clojure.string/split tutorials-str #",")))))

(defn clean-clj-data [data]
  (mapv #(-> %
             (string->vec-of-strings :tutorials)
             (string->vec-of-strings :use))
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



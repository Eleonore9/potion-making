(ns program-languages-resources.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.edn :as edn]
            [clojure.data.json :as json]
            [program-languages-resources.controller :as ctrl]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Programming languages resources API"})


(def lang-data
  (edn/read-string
   (slurp
    (io/file
     "resources/data/programming-languages-resources.edn"))))

(defn get-all-data
  [langs-info info-type]
  (->> langs-info
       (map #(get % info-type))
       (filterv #(not (clojure.string/blank? %)))
       set
       vec))

(defn get-all-uses
  [langs-info]
  (->> langs-info
       (map #(:use %))
       (reduce concat)
       set
       (filterv #(not (clojure.string/blank? %)))))

(defn to-json [data]
  (json/write-str data :escape-slash false))

(defn clean-json [json]
  (mapv clojure.walk/stringify-keys json))

(defroutes app
  (GET "/all-data" []
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (clean-json lang-data))})
  (GET "/all-names" []
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (get-all-data lang-data :name))})
  (GET "/all-paradigms" []
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (get-all-data lang-data :paradigm))})
  (GET "/all-types" []
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (get-all-data lang-data :type))})
  (GET "/all-uses" []
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (get-all-uses lang-data))})
  (GET "/name" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (ctrl/get-info-by
                        {:input-type :name :input-value input :all-info lang-data}))})
  (GET "/paradigm" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (ctrl/get-info-by
                        {:input-type :paradigm :input-value input :all-info lang-data}))})
  (GET "/type" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (ctrl/get-info-by
                        {:input-type :type :input-value input :all-info lang-data}))})
  (GET "/use" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "application/json"
                  "Access-Control-Allow-Origin" "*"}
        :body (to-json (ctrl/get-info-by
                        {:input-type :use :input-value input :all-info lang-data}))})
  (GET "/" []
       (splash)
       (slurp (io/resource "index.html")))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

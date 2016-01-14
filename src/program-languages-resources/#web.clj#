(ns program-languages-resources.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.edn :as edn]
            [clojure.data.json :as json]))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Programming languages resources"})

(def lang-data
  (edn/read-string
   (slurp
    (io/file
     "resources/data/programming-languages-resources.edn"))))

(defn get-all-data [langs-info]
  (pr-str langs-info))

(defn get-all-names [langs-info]
  (pr-str (map :name langs-info)))

(defn get-by-name [name langs-info]
  (pr-str
   (filter #(= name (:name %))
           langs-info)))

(defroutes app
  (GET "/all-data" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-data lang-data)})
  (GET "/all-names" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-names lang-data)})
  (GET "/get-by-name" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-by-name input lang-data)})
  ;; (GET "/get-by-paradigm" {{input :input} :params}
  ;;      {:status 200
  ;;       :headers {"Content-Type" "text/plain"}
  ;;       :body (:paradigm test-lang)})
  ;; (GET "/type" {{input :input} :params}
  ;;      {:status 200
  ;;       :headers {"Content-Type" "text/plain"}
  ;;       :body (:type test-lang)})
  ;; (GET "/use" {{input :input} :params}
  ;;      {:status 200
  ;;       :headers {"Content-Type" "text/plain"}
  ;;       :body (:use test-lang)})
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

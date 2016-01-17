(ns program-languages-resources.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.edn :as edn]
            [clojure.data.json :as json]
            [ring.middleware.cors :refer [wrap-cors]]
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
  ([langs-info]
   (pr-str langs-info))
  ([langs-info info-type]
   (->> langs-info
        (map #(get % info-type))
        (filterv #(not (clojure.string/blank? %)))
        set
        vec
        pr-str)))

(defn clean-json [json]
  (mapv clojure.walk/stringify-keys json))

(defroutes app
  (GET "/all-data" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-data (clean-json lang-data))})
  (GET "/all-names" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-data lang-data :name)})
  (GET "/all-paradigms" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-data lang-data :paradigm)})
  (GET "/all-types" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-data lang-data :type)})
  (GET "/all-uses" []
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (get-all-data lang-data :use)})
  (GET "/name" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (ctrl/get-info-by
               {:input-type :name :input-value input :all-info lang-data})})
  (GET "/paradigm" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (ctrl/get-info-by
               {:input-type :paradigm :input-value input :all-info lang-data})})
  (GET "/type" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (ctrl/get-info-by
               {:input-type :type :input-value input :all-info lang-data})})
  (GET "/use" {{input :input} :params}
       {:status 200
        :headers {"Content-Type" "text/plain"}
        :body (ctrl/get-info-by
               {:input-type :use :input-value input :all-info lang-data})})
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(def handler
  (wrap-cors app
             :access-control-allow-origin [#"http://github.io" #"http://github.com"
                                           #"https://github.io" #"https://github.com"]
             :access-control-allow-methods [:get]))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

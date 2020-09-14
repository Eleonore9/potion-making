(defproject program-languages-resources "1.0.0-SNAPSHOT"
  :description "Clojure mini API"
  :url "http://potion-making.herokuapp.com/"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.2"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [environ "1.2.0"]
                 [camel-snake-kebab "0.4.1"]
                 [org.clojure/data.json "1.0.0"]
                 [org.clojure/data.csv "1.0.0"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "1.2.0"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "program-languages-resources-standalone.jar"
  :profiles {:production {:env {:production true}}})

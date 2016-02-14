(ns program-languages-resources.process-test
  (:require [clojure.test :refer :all]
            [program-languages-resources.process :refer :all]))

(def data-map
  {:family "C" :paradigm "Imperative" :homepage "" :use "hardware, desktop"
   :other "" :name "C" :docs "" :type "static" :tutorials "http://www.learn-c.org/, http://www.dsc.ufcg.edu.br/~rangel/Beginning%20C%20For%20Arduino%20%282012%29.pdf" :characteristics ""
   :created_in "" :vm_host "" :created_by "" :created_at ""})

(deftest format-key-test
  (testing "formatting of keys"
    (is (= :name (format-key "Name")))
    (is (= :language_name (format-key "Language name")))))

(deftest string->vec-of-strings-test
  (testing "The tutorials are returned in a vector"
    (is (vector? (:tutorials (string->vec-of-strings data-map :tutorials))))
    (is (= {:family "C" :paradigm "Imperative" :homepage "" :use "hardware, desktop"
            :other "" :name "C" :docs "" :type "static" :tutorials ["http://www.learn-c.org/" "http://www.dsc.ufcg.edu.br/~rangel/Beginning%20C%20For%20Arduino%20%282012%29.pdf"]
            :characteristics "" :created_in "" :vm_host "" :created_by "" :created_at ""}
           (string->vec-of-strings data-map :tutorials)))
    (is (= {:family "C", :paradigm "Imperative", :homepage "", :use ["hardware" "desktop"], :other "", :name "C", :docs "", :type "static", :tutorials "http://www.learn-c.org/, http://www.dsc.ufcg.edu.br/~rangel/Beginning%20C%20For%20Arduino%20%282012%29.pdf", :characteristics "", :created_in "", :vm_host "", :created_by "", :created_at ""}
           (string->vec-of-strings data-map :use)))))

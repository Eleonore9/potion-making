(ns program-languages-resources.web-test
  (:require [clojure.test :refer :all]
            [program-languages-resources.web :refer :all]))

(deftest get-all-data-test
  (testing "Retrieve relevant info"
    (is (= ["three" "five" "one"]
           (get-all-data [{:a "one" :b "two"}
                          {:a "three" :b "four"} 
                          {:a "five" :b "six"}] :a)))))

(deftest get-all-uses-test
  (testing "The uses info are in the right format"
    (is (= ["desktop" "science" "web" "data"]
           (get-all-uses [{:a "one" :use ["web" "data"]} 
                          {:a "three" :use ["web" "desktop"]} 
                          {:a "five" :use ["science" "data"]}])))))

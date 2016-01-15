(ns program-languages-resources.controller)

;; example of input-info:
;; {:input-type :name :input-value "Python" :all-info lang-data}

(defmulti get-info-by
  (fn [input-info]
    (:input-type input-info)))

(defmethod get-info-by :name
  [input-info]
  (let [{:keys [input-type input-value all-info]}
        input-info]
    (pr-str
     (filter #(= input-value (:name %))
             all-info))))

(defmethod get-info-by :paradigm
  [input-info]
  (let [{:keys [input-type input-value all-info]}
        input-info]
    (pr-str
     (filter #(= input-value (:paradigm %))
             all-info))))

(defmethod get-info-by :type
  [input-info]
  (let [{:keys [input-type input-value all-info]}
        input-info]
    (pr-str
     (filter #(= input-value (:type %))
             all-info))))

(defmethod get-info-by :use
  [input-info]
  (let [{:keys [input-type input-value all-info]}
        input-info]
    (pr-str
     (filter #(not (clojure.string/blank?
                    (re-find (re-pattern input-value) (:use %))))
             all-info))))


(comment
  ;; from program-languages-resources.web ns
  (ctrl/get-info-by
   {:input-type :name :input-value "Python" :all-info lang-data})
  
  (ctrl/get-info-by
   {:input-type :paradigm :input-value "Imperative" :all-info lang-data})
  
  (ctrl/get-info-by
   {:input-type :type :input-value "static" :all-info lang-data})
  
  (ctrl/get-info-by
   {:input-type :use :input-value "web" :all-info lang-data}))

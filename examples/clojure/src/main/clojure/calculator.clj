(ns calculator)

(def numbers (ref (vector)))
(def symbol-values (atom {}))
     
(defn push-number [num]
  (dosync (alter numbers conj num)))

(defn push-symbol [name]
  (let [price (get @symbol-values name 0)]
    (push-number price)))

(defn calculate [operator]
  (dosync
   (ref-set numbers [(apply operator @numbers)])))

(defn current-value []
  (last @numbers))


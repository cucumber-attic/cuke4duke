(use 'calculator)
;; We're not using test-is yet, since failures are just printed - nothing is raised.
;; (use 'clojure.contrib.test-is)

;; Just test that it works
(After)

(Before
 (dosync (ref-set numbers []))
 (reset! symbol-values {}))

(Given #"^I have entered ([\d.]+) into the calculator$"
  #(push-number (Float. %1)))

(defn hashes [table]
  (map #(into {} %) (.hashes table)))

(Given #"^the following price list$"
       (fn [table]
	 (let [name-to-price-map (reduce #(assoc %1 (%2 "Name") (Float. (%2 "Price"))) {} (hashes table))]
	   (reset! symbol-values name-to-price-map))))

(Given #"^I have entered \"([\w.]+)\" into the calculator$"
  #(push-symbol %1))

(When #"^I press (\w+)$"
      #(calculate ({"divide" /,
		    "add" +,
		    "multiply" *} %)))

;; Verbose style
(Then #"^the current value should be ([\d.]+)$"
  (fn [expected]
    ;;    (is (= (current-value) (Float. expected)))))
    (assert (= (current-value) (Float. expected)))))

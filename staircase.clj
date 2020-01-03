(ns staircase.core)

(defn gen-n-char [ns c]
  (apply str (repeat ns c)))

(defn staircase [n]
  (dotimes [i n]
    (println (str
              (gen-n-char (- n i 1) \space)
              (gen-n-char (inc i) \#)))))

(def n (Integer/parseInt (clojure.string/trim (read-line))))

(staircase n)

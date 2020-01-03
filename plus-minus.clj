(ns test.core)

(defn print-ratio [n arr] (println (str (float (/ (count arr) n)))))

(def n (Integer/parseInt (clojure.string/trim (read-line))))

(defn plusMinus [arr]
  (let [neg (filter neg? arr)
        pos (filter pos? arr)
        zero (filter zero? arr)
        printr (partial print-ratio n)]
    (printr pos)
    (printr neg)
    (printr zero)))

(def arr (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))

(plusMinus arr)

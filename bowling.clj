(ns bowling.core
  (:gen-class))

(defn remove-miss [coll] (clojure.string/replace coll #"-" ""))
(defn replace-strike [coll] (map #(clojure.string/replace % #"X" "10") coll))
(defn replace-spare [coll] (map #(clojure.string/replace % #"/" "10") coll))

(defn calc [coll] (reduce #(+ (read-string %2) %1) 0
                          ((comp replace-strike replace-spare) coll)))

(defn parse
  [coll score]
  (let [f (first coll)]
    (+ score
       (cond
         (= f "X") (calc  (take 3 coll))
         (= f "/") (calc (take 2 coll))
         :else (calc (take 1 coll))))))

(defn count-score
  [coll score]
    (if (seq coll)
      (count-score (rest coll)
                   (parse coll score))
      score))

(defn -main
  [input & args]
  (->
   (clojure.string/split (remove-miss input) #" ")
   (count-score 0)))

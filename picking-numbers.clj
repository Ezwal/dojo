(ns picking.numbers.core)

(defn extract-n+1-array [a n]
  (filter #(or (= % n)
               (= % (inc n)))
          a))

(defn picking-numbers [a]
  (->> (sort a)
       (distinct)
       (map (partial extract-n+1-array a))
       (map count)
       (apply max)))

(def fptr (get (System/getenv) "OUTPUT_PATH"))

(def n (Integer/parseInt (clojure.string/trim (read-line))))

(def a (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))

(def result (picking-numbers a))

(spit fptr (str result "\n") :append true)

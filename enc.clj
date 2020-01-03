(ns enc.core)

(defn get-dim [s]
  (let [word-count (count s)
        lower-bound (int (Math/floor (Math/sqrt word-count)))
        higher-bound (int (Math/ceil (Math/sqrt word-count)))
        range-bound [lower-bound higher-bound]]
    (for [row-count range-bound
          column-count range-bound
          :when (and (<= row-count column-count)
                     (and (<= lower-bound row-count)
                          (>= higher-bound column-count))
                     (>= (* row-count column-count) word-count))]
      [row-count column-count])))

(defn complete-partition [p nb]
  (let [last (last p)
        last-count (count last)]
    (into (rest p)
          (into last ((comp vec repeat) (- nb last-count) \space)))))

(defn encryption [s]
  (let [trimmed (clojure.string/replace s #" " "")
        [row column] (first (get-dim trimmed))
        partitioned-vec (mapv vec (partition-all column trimmed))
        encrypted-str (apply interleave partitioned-vec)]
    (clojure.string/join " " encrypted-str)))

(def fptr (get (System/getenv) "OUTPUT_PATH"))

(def s (read-line))

(def result (encryption s))

(spit fptr (str result "\n") :append true)

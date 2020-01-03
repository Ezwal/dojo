(ns climbing-leaderboard)

;; (defn get-position [rss score]
;;   (let [superior-scores (take-while #(>= % score) rss)
;;         c (count superior-scores)]
;;     (if (= (last superior-scores) score)
;;       c
;;       (inc c))))

(defn dedupe-and-superior [coll limit]
  (loop [actual coll
         c 0]
    (let [[f s r] actual]
      (if (< f limit)
        c
        (recur r
               (if (and f s (= f s))
                 c
                 (inc c)))))))

(defn get-position [rss score]
  (let [superior-scores (dedupe-and-superior rss score)
        c (count superior-scores)]
    (if (= (last superior-scores) score)
      c
      (inc c))))

(defn climbing-leaderboard [scores alice]
  (let [rss scores]
    (map #(get-position rss %) alice)))

(def fptr (get (System/getenv) "OUTPUT_PATH"))
(def scores-count (Integer/parseInt (clojure.string/trim (read-line))))
(def scores (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))
(def alice-count (Integer/parseInt (clojure.string/trim (read-line))))
(def alice (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))
(def result (climbing-leaderboard scores alice))

(spit fptr (clojure.string/join "\n" result) :append true)
(spit fptr "\n" :append true)

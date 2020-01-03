(ns magicsquare.core)

(def sum (partial reduce +))

(def all-magic-square
  [[[4 9 2] [3 5 7] [8 1 6]]
   [[2 7 6] [9 5 1] [4 3 8]]
   [[6 1 8] [7 5 3] [2 9 4]]
   [[8 3 4] [1 5 9] [6 7 2]]
   [[2 9 4] [7 5 3] [6 1 8]]
   [[6 7 2] [1 5 9] [8 3 4]]
   [[8 1 6] [3 5 7] [4 9 2]]
   [[4 3 8] [9 5 1] [2 7 6]]])

(defn is-square? [square]
  (let [line-nb (count square)]
    (every? (partial = line-nb)
            (map count square))))

(defn is-magic-square? [square]
  (let [fs (flatten square)]
    (and (is-square? square)
         (every? (partial = (sum (take 3 fs))) ;; verifying all diags and lines are equal to first line sum
                 (map sum
                      [(take-last 3 fs)
                       (take 3 (drop 3 fs))
                       (take-nth 3 fs)
                       (take-nth 3 (drop 1 fs))
                       (take-nth 3 (drop 2 fs))
                       (drop 1 (butlast (take-nth 2 fs)))
                       ])))))

(defn create-magic-square [a b c]
  [[(+ c a) (- c a b) (+ c b)]
   [(+ (- c a) b) c (+ c (- a b))]
   [(- c b) (+ c a b) (- c a)]])

(defn get-square-distance [fsq ssq]
  (reduce +
          (map #(Math/abs (- %1 %2))
               (flatten fsq) (flatten ssq))))

(defn formingMagicSquare [test-square]
  (apply min (map #(get-square-distance test-square %)
            all-magic-square)))

(def fptr (get (System/getenv) "OUTPUT_PATH"))

(def s [])

(doseq [_ (range 3)]
  (def s (conj s (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))))

(def result (formingMagicSquare s))

(spit fptr (str result "\n") :append true)

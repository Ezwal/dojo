(ns grading)

(defn get-5-multiple [nb]
  (let [m (mod nb 5)]
    (if (and (>= m 3) (not (zero? m)))
      (+ nb (- 5 m)) nb)))

(defn grading-students [grades]
  (map #(if (>= % 38)
          (get-5-multiple %)
          %) grades))

(def fptr (get (System/getenv) "OUTPUT_PATH"))

(def n (Integer/parseInt (clojure.string/trim (read-line))))

(def grades [])

(doseq [_ (range n)]
  (def grades (conj grades (Integer/parseInt (read-line)))))

(def result (grading-students grades))

(spit fptr (clojure.string/join "\n" result) :append true)
(spit fptr "\n" :append true)

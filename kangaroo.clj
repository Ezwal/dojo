(ns kangaroo.core)

(defn kangaroo [x1 v1 x2 v2]
  (if (= (- v1 v2) 0) "NO"
  (let [criteria (/ (- x2 x1) (- v1 v2))]
  (if (and (= (type criteria) java.lang.Long) (pos? criteria))
  "YES" "NO"))))

(def fptr (get (System/getenv) "OUTPUT_PATH"))

(def x1V1X2V2 (clojure.string/split (read-line) #" "))

(def x1 (Integer/parseInt (clojure.string/trim (nth x1V1X2V2 0))))
(def v1 (Integer/parseInt (clojure.string/trim (nth x1V1X2V2 1))))
(def x2 (Integer/parseInt (clojure.string/trim (nth x1V1X2V2 2))))
(def v2 (Integer/parseInt (clojure.string/trim (nth x1V1X2V2 3))))

(spit fptr (str (kangaroo x1 v1 x2 v2) "\n") :append true)

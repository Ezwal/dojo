(ns timeconversion.core)

(def time-reg #"(\d{2}:\d{2}:\d{2})[AP]M")

(defn extract-time [s] (last (re-matches time-reg s)))

(defn is-am? [s] (re-find #"AM" s))

(defn add-twelve [arr]
  (let [[hours minutes seconds] arr]
    [(str (+ (Integer/parseInt hours) 12)) minutes seconds]))

(defn split-time-division [s]
  (clojure.string/split s #":"))

(defn timeConversion [s]
  (let [time-array (split-time-division (extract-time s))]
    (clojure.string/join \:
                         (if (is-am? s)
                           (if (= 12 (Integer/parseInt (first time-array)))
                             ["00" (second time-array) (last time-array)]
                             time-array)
                           (if (= 12 (Integer/parseInt (first time-array)))
                             time-array
                             (add-twelve time-array))))))

(def fptr (get (System/getenv) "OUTPUT_PATH"))

(def s (read-line))

(def result (timeConversion s))

(spit fptr (str result "\n") :append true)

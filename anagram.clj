(ns anagram.core
  (:gen-class))

(defn -main
  [filepath & args]
  (->>
   (slurp filepath)
   (clojure.string/split-lines)
   (map #(clojure.string/lower-case (clojure.string/replace % "'" "")))
   (reduce #(assoc %1 (frequencies %2) (conj  (%1 (frequencies %2)) %2)) {})
   (vals)))

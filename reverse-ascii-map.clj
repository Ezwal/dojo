(ns reverse-ascii-map.core
  (:gen-class))

(def ascii-table (map char (range 32 126)))

(defn print-table
  [coll]
  (->>
      (partition 16 coll)
      (map (comp println (partial clojure.string/join ""))))) 

(defn replace-char
  [coll char]
  (map #(if (= % char) \space %) coll)) 
  
(defn is-over?
  [coll]
  (seq (filter #(not= % \space) coll)))
  
(defn -main
  []
  (loop [at ascii-table]
    (println ascii-table)
    (when (is-over? at) 
        (print-table at)
        (recur (replace-char at (first (read-line))))))) 

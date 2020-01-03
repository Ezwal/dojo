(ns bdchocolate)

(defn is-n-eq-sum [sum nb-el]
  #(= sum (reduce + (take nb-el %))))

;; sum = d and nb of sum =m
(defn birthday [vals sum nb-el]
  (let [pred (is-n-eq-sum sum nb-el)]
    (loop [rest-s vals
           match-count 0]
      (if (< (count rest-s) nb-el)
        match-count
        (recur (rest rest-s)
               (if (pred rest-s)
                 (inc match-count)
                 match-count))))))

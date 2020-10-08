(ns knight)

; 1 2 3
; 4 5 6
; 7 8 9
;   0
(def correspondance
  {0 [4 6]
   1 [8 6]
   2 [7 9]
   3 [4 8]
   4 [3 9 0]
   5 []
   6 [7 1 0]
   7 [2 6]
   8 [1 3]
   9 [2 4]})

(def N 10)

(defn dial-sequence
  ([f] (dial-sequence f []))
  ([f seq]
   (let [nexts (get correspondance f)]
    (if (or (<= N (count seq))
            (= 0 (count nexts)))
        seq
        (for [n nexts]
            (dial-sequence n (conj seq n)))))))

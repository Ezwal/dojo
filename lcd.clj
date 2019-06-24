(ns lcd-display.core
  (:gen-class))

(def left 0)
(def flat 1)
(def right 2)

; number are drawn by stage and element
(def numbers {0 {0 [flat], 1 [left right], 2 [left flat right]},
              1 {0 [], 1 [right], 2 [right]},
              2 {0 [flat], 1 [right flat], 2 [left flat]},
              3 {0 [flat], 1 [right flat], 2 [right flat]},
              4 {1 [right flat left], 2 [right]},
              5 {0 [flat], 1 [left flat], 2  [right flat]},
              6 {0 [flat], 1 [left flat], 2 [right flat left] },
              7 {0 [flat], 1 [right], 2 [right]},
              8 {0 [flat], 1 [left right flat], 2 [left flat right]},
              9 {0 [flat], 1 [right flat left], 2 [right flat]}})

(defn is-here?
  [stage el]
  (some #(= % el) stage))

(defn gen-horizontal
  "gen one line as a string"
  [stage width]
  (let [fill_char (if (is-here? stage flat) \_ \ )]
  (conj []
       (str (if (is-here? stage left) \| fill_char)
       (apply str (repeat (- width 2) fill_char))
       (if (is-here? stage right) \| fill_char)
       " "))))

(defn gen-vertical
  "gen multiple line (for vertical line)"
  [stage height width]
  (vec (flatten (repeat height
          (gen-horizontal 
            (remove #(= flat %) stage) width)))))

(defn gen-number
  ([number width height]
    (flatten
      (for [level (range 3)
          :let [schema (numbers number)
                stage (schema level) 
                inter-size (Math/ceil (/ (- height 3) 2))
                number-map []]]
      (concat number-map 
            (if (not= level 0) (gen-vertical stage inter-size width))
            (gen-horizontal stage width)))))
  ([number]
   (gen-number number 3 2)))

(defn print-number
  [coll]
  (map print coll))

(defn cat-line
    [coll]
   (apply interleave coll))

(defn -main
  "take a string chain of character and display it"
  [chain & args]
  (let [[width height] args]
  (->
    (mapv (comp #(gen-number % width height) #(Character/digit % 10)) chain)
    (conj (repeat height "\n"))
    (cat-line) 
    (print-number))))

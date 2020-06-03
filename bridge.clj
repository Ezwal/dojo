(ns bridge
  (:require [clojure.string :as string]))


; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.
(def sample [".............................."
             ".............................."
             "...........0.................."
             ".............................."])

(defn action-speed [action]
  (case action
    "SPEED" 1 "SLOW" -1 0))
(defn action-movement [action]
  (case action "UP" -1 "DOWN" 1 0))

(def road \.) (def hole \0)

(defn is-path-ok? [lane a b] ;; TODO add error handling to this ?
  (every? #(= road %)
        (subs lane a b)))

(defn is-bike-ok? [lanes [o-x o-y] [n-x n-y] jump]
  (let [starting-pos (if jump n-x (inc o-x))]
   (every? true?
           (list (is-path-ok? (get lanes n-y) starting-pos (inc n-x))
                 (is-path-ok? (get lanes o-y) starting-pos n-x)))))

(defn bounded-y [y] (min (max 0 y) 3))

;; update bike coords if everything is OK otherwise returns nil
(defn bike-upd [lanes speed turn jump [x y]]
  (let [new-bike-coords [(+ x speed)
                         (bounded-y (+ turn y))]]
    (if (is-bike-ok? lanes [x y] new-bike-coords jump)
      new-bike-coords)))

(defn kill-if-same [bike-coords] ;; serve to cut early recursion for useless move
  (if (not= (count (dedupe bike-coords)) (count bike-coords))
    '() bike-coords))

;; do the transition between action to bike coords
(defn all-bike-upd [lanes speed bike-coords action]
  (let [new-speed (+ (action-speed action) speed)
        turn (action-movement action)]
    (kill-if-same (filter identity ;; remove all bikes that have fallen or move illegal
            (map (partial bike-upd lanes new-speed turn (= action "JUMP"))
                 bike-coords)))))

(def actions ["SPEED" "JUMP" "SLOW" "UP" "DOWN" "WAIT"])

;; defines when the loop cycle actually did it
(defn win-pred [minimal-bikes max-x bikes-coords]
  (and
   (has-minimum-bikes? minimal-bikes bikes-coords)
   (is-finished? max-x bikes-coords)))
(defn is-finished? [max-x bikes] (>= (first (first bikes)) max-x))
(defn has-minimum-bikes? [minimal-bikes bikes-coords] (>= (count bikes-coords) minimal-bikes))

(defn best-sol [lanes max-x speed bikes minimal-bikes & action-list]
  (if (win-pred minimal-bikes max-x bikes)
    action-list
    (for [new-action actions
          :let [new-bikes (all-bike-upd lanes speed bikes new-action)]]
      (when (has-minimum-bikes? minimal-bikes new-bikes)
        (best-sol lanes max-x
                (+ (action-speed new-action) speed)
                new-bikes
                minimal-bikes
                (conj action-list new-action))))))

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn -main [& args]
  (let [M (read) V (read) lanes (vec (repeatedly 4 read))]
    ; M: the amount of motorbikes to control
    ; V: the minimum amount of motorbikes that must survive

    (let [S (read)] ;; taking initial data
      (loop [i M data []]
        (if (= i 0)
          data
          (let [X (read) Y (read) A (read)]
            (recur (dec i)
                   (cons [X Y] data)))))) ;; idefc about A for the first iteration tbh
    ;; before
    (while true
      (let [S (read)]
        ; S: the motorbikes' speed
        (loop [i M]
          (when (> i 0)
            (let [X (read) Y (read) A (read)]
              ; X: x coordinate of the motorbike
              ; Y: y coordinate of the motorbike
              ; A: indicates whether the motorbike is activated "1" or detroyed "0"
            (recur (dec i)))))

        ; (debug "Debug messages...")

        ; A single line containing one of 6 keywords: SPEED, SLOW, JUMP, WAIT, UP, DOWN.
        (output "SPEED")))))

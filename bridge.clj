(ns Player
  (:require [clojure.string :as string])
  (:use clojure.walk))

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

(defn is-path-ok? [lane a b]
    (every? #(= road %)
            (subs lane a b)))

(defn is-bike-ok? [lanes [o-x o-y] [n-x n-y] jump]
  (let [starting-pos (if jump n-x (inc o-x))
        ending-pos (bounded-count (inc n-x) (first lanes))] ;; if jump then only check final pos
   (every? true?
           (list (is-path-ok? (get lanes n-y) starting-pos ending-pos)
                 (is-path-ok? (get lanes o-y) starting-pos (dec ending-pos))))))

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

(def actions ["SPEED" "JUMP" "UP" "DOWN"]) ;; remove WAIT and SLOW just as experiment

(def ff (comp first first))
(defn is-finished? [max-x bikes] (>= (ff bikes) max-x))
(defn has-minimum-bikes? [minimal-bikes bikes-coords] (>= (count bikes-coords) minimal-bikes))
(defn is-not-too-long? [actions-list] (< (count actions-list) 50))

;; defines when the loop cycle actually did it
(defn win-pred [minimal-bikes max-x bikes-coords]
  (and
   (has-minimum-bikes? minimal-bikes bikes-coords)
   (is-finished? max-x bikes-coords)))

;; (best-sol sample 20 1 [[0 1] [0 2]] 1)
(defn best-sol
  ([lanes max-x speed bikes minimal-bikes]
   (loop [sol (best-sol lanes max-x speed bikes minimal-bikes [])]
     (if (vector? (first sol))
       (first sol)
       (recur (first sol)))))
  ([lanes max-x speed bikes minimal-bikes action-list]
   (if (win-pred minimal-bikes max-x bikes)
     action-list
     (for [new-action actions
           :let [new-bikes (all-bike-upd lanes speed bikes new-action)]
           :when (and (has-minimum-bikes? minimal-bikes new-bikes)
                      (is-not-too-long? action-list))]
       (best-sol lanes max-x (+ (action-speed new-action) speed) new-bikes minimal-bikes
                 (conj action-list new-action))))))

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(def fc (comp count first))
(defn read-times [n] (repeatedly n read))
(defn -main [& args]
  (let [M (read) V (read) lanes [(str (read)) (str (read)) (str (read)) (str (read))]] ;; V: the minimum amount of motorbikes that must survive
    (debug (type (first lanes)))
    (let [S (read)
          bikes (loop [i M data []]
                  (if (= i 0)
                    data
                    (let [X (read) Y (read) A (read)]
                      (recur (dec i)
                             (cons [X Y] data)))))
          sols (best-sol lanes (dec (fc lanes)) S bikes M)] ;; taking initial data
      (doseq [sol-action sols]
        (output sol-action)
        (read-times 4)))))

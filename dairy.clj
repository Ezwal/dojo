(ns dairy (:require [clojure.string :as str]))

(defn long-string [coll] (str/join "\n" coll))
(def cow (long-string
        ["       \\"
         "        \\   ^__^"
         "         \\  (oo)\\_______"
         "            (__)\\       )\\/\\ "
         "                ||----w |"
         "                ||     ||"]))

(defn b2b [a] (str/join "" a))
(defn right-pad [l s] (format "%s%s" s
                             (b2b (repeat (- l (count s)) " "))))
(defn h-bar [l s] (b2b (repeat (+ 2 l) s)))
(defn v-bar [coll] (b2b ["|" coll "|"]))

(defn max-line-length [coll]
  (reduce (fn [acc l] (max acc (count l))) 0 coll))

(defn bubble-say [say]
  (let [say-coll (str/split say #"\n")
        l (max-line-length say-coll)
        up-bar (h-bar l "▔") down-bar (h-bar l "▁")
        up (v-bar up-bar)    down (v-bar down-bar)]
    (long-string (flatten [up
                  (map #(v-bar (right-pad (+ 2 l) %)) say-coll)
                  down]))))

(defn thing-gen [thing say] (format "%s\n%s" (bubble-say say) thing))
(defn thing-say [thing say] (println (thing-gen thing say)))

(def cow-say (partial thing-say cow))

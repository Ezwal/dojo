
;; SAMPLE :
(do-let
 a 1
 c 19
 (print (+ a c)))

(defmacro do-let
  "takes everything before last (which will be the body) and assign as it was a let"
  [& items]
  `(let [~@(butlast items)]
        ~(last items)))


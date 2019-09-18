(ns clojure-brave.exercises.section-7)

;1
(eval (list 'str "Mladjan " "Interstellar"))

(eval '(str "Mladjan " "Interstellar"))

(eval (read-string "(str \"Mladjan \" \"Interstellar\")"))

;2
(defmacro infix
  [[op1 plus op2 times op3 minus op4]]
  (list minus
        (list plus
              op1
              (list times op2 op3))
        op4))

(infix (1 + 3 * 4 - 5))

(macroexpand '(infix (1 + 3 * 4 - 5))) ;(1 + 3 * 4 - 5) --> (- (+ 1 (* 3 4) ) 5)
(ns clojure-brave.exercises.section-8)

;setup for exercise 1
(def order-details-validation
  {:name
   ["Please enter a name" not-empty]
   :email
   ["Please enter an email address" not-empty
    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

(def order-details-good {:name "user" :email "user@mail.com"})
(def order-details-bad {:name "user" :email "usermail.com"})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))
(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

;we need if-valid macro in order to implement when-valid in most straightforward way,
;similar to how 'when' macro from `clojure.core` is implemented in terms of 'if'
(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

;1
(defmacro when-valid
  [data data-validation & actions]
  `(if-valid ~data ~data-validation ~'err (do ~@actions) false))

;Should execute both functions
(when-valid order-details-good order-details-validation
            (println "It's a success!")
            (println :success))

;Should return false
(when-valid order-details-bad order-details-validation
            (println "It's a success!")
            (println :success))

;Check expanded forms
(macroexpand '(when-valid order-details order-details-validation
                          (println "It's a success!")
                          (println :success)))
;2
(defmacro my-or
  "macro for or logic"
  ([] nil)
  ([x] x)
  ([form & forms]
   `(let [sym# ~form]
      (if sym# sym# (my-or ~@forms)))))

(my-or nil false 2 1)
(macroexpand '(my-or nil false 2 1))

;3
(defmacro defattrs
  [& assignments]
  `(do
     ~@(map
        (fn [[retr attr]] `(def ~retr ~attr))
        (partition 2 assignments))))

(defattrs c-int :intelligence wokring? :should-work)

(print wokring? c-int)

(macroexpand '(defattrs c-int :intelligence test :should-work))


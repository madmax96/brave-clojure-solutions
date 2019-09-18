(ns clojure-brave.exercises.section_3)

;1
(str "My " "awesome " "string")
(vector 1 2 3)
(list 1 2 3)
(hash-map :name "madmax" :age 23)
(hash-set :clojure :javascript :php :c++ :python)

;2
(defn add-100
  [num]
  (+ 100 num))
(add-100 1)

;3
(defn dec-maker
  [num]
  #(- % num))
(def dec-10 (dec-maker 10))
(dec-10 100)

;4
(defn mapset [f coll] (set (map f coll)))
(mapset inc [1 1 2 2])

;5 skipped
;Since exercise 6 is generalisation of exercise 5, i solved only exercise 6.

;6
(def alien-hobbit-body-parts [{:name "head" :size 3}
                              {:name "eye-1" :size 1}
                              {:name "ear-1" :size 1}
                              {:name "mouth" :size 1}
                              {:name "nose" :size 1}
                              {:name "neck" :size 2}
                              {:name "shoulder-1" :size 3}
                              {:name "upper-arm-1" :size 3}
                              {:name "chest" :size 10}
                              {:name "back" :size 10}
                              {:name "forearm-1" :size 3}
                              {:name "abdomen" :size 6}
                              {:name "kidney-1" :size 1}
                              {:name "hand-1" :size 2}
                              {:name "knee-1" :size 2}
                              {:name "thigh" :size 4}
                              {:name "lower-leg-1" :size 3}
                              {:name "achilles-1" :size 1}
                              {:name "foot-1" :size 2}])

(defn matching-alien-parts
  [{name :name size :size} n]
  (reduce (fn [all-parts n] (conj
                             all-parts
                             {:size size :name (clojure.string/replace name #"-(.*)" (str "-" n))}))
          #{}
          (range 1 (inc n))))

(defn symetrize-alien-body-parts
  "this is a generalisation of function symmetrize-body-parts''that is described in book.
   Function receives a collection of body parts and number of matching body parts to add"
  [alien-body-parts n]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (matching-alien-parts part n)))
          []
          alien-body-parts))

(symetrize-alien-body-parts alien-hobbit-body-parts 3)
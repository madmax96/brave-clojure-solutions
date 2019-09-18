(ns clojure-brave.exercises.section-10
  (:require [clojure.string :as str]))

;1
(def a (atom 10))
(swap! a + 5)
(swap! a + 7)
(print @a)

;2
(defn quote-word-count
  [n]
  (let [state (atom {})
        promises (map (fn [_]
                        (future (let [quote (slurp "https://www.braveclojure.com/random-quote") curr-state @state]
                                  (let [new-state (reduce
                                                   (fn [word-map word]
                                                     (assoc word-map word (inc (get word-map word 0))))
                                                   curr-state
                                                   (str/split quote #" "))]
                                    (reset! state new-state)))))

                      (range n))]
    (doseq [p promises] @p)
    @state))

(quote-word-count 5)

;3
(def doctor (ref {:num-of-healing-potions 1}))
(def soldier (ref {:name "Mladjan" :health 15}))
(def MAX-HEALTH 40)

(defn doctor-heal-soldier
  [doctor soldier]
  (dosync
   (alter doctor update-in [:num-of-healing-potions] dec)
   (alter soldier assoc-in [:health] MAX-HEALTH)))

(doctor-heal-soldier doctor soldier)

(print @doctor)
(print @soldier)
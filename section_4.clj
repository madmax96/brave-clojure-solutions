(ns clojure-brave.exercises.section-4)

;Setup for this exercises, below are functions from book.

(def filename "suspects.csv")
(def file-content (slurp filename))
(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})
(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn csv-parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(def parsed-content (csv-parse file-content))

(defn mapify
  "Return a seq of maps like {:name 'Edward Cullen' :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(mapify parsed-content)

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def filtered (glitter-filter 5 (mapify parsed-content)))
(print filtered)

;1
(def names (map :name filtered))
(print names)

;3
(def suspect-validations {:name string?
                          :glitter-index integer?})

(defn validate [key-validations record]
  (every? true? (map (fn [[key validation-fn]] (validation-fn (key record))) key-validations)))

;2 This fn could also append to a list in memory, but i decided to append to csv file.
(defn append
  [suspect]
  (if (validate suspect-validations suspect)
    (spit filename (str "\n"  (clojure.string/join ","  [(:name suspect) (:glitter-index suspect)])) :append true)))

(append {:name "madmax" :glitter-index 10})

;4
;This could be further generalised to accept maps with arbitrary keys.
(defn to-csv
  [coll]
  (reduce
   (fn [csv-string {name :name index :glitter-index}]
     (str csv-string (clojure.string/join "," [name (str index "\n")])))
   ""
   coll))
(to-csv [{:name "madmax" :glitter-index 10} {:name "test" :glitter-index 11}])
(ns clojure-brave.exercises.section-5)

;from book
(def character {:attributes {:intelligence 10}})
(def get-intelligence (comp :intelligence :attributes))
(get-intelligence character)

;1
(defn attr
  [k]
  (fn [obj] ((comp k :attributes) obj)))

(def get-intelligence-2 (attr :intelligence))
(get-intelligence-2 character)

;2
(defn my-comp
  [& fns]
  (fn [& args]
    (reduce
     (fn [prev-result cur-fn] (if (= prev-result args)
                                (apply cur-fn prev-result)
                                (cur-fn prev-result)))
     args
     (reverse fns))))

(defn my-comp-simpler
  ([f] f)
  ([f & fs]
   (fn [& args] (f (apply (apply my-comp-simpler fs) args)))))

((my-comp inc inc +) 10 11)  ;23
((my-comp-simpler inc inc +) 10 11)  ;23
((comp inc inc +) 10 11)  ;23

;3
(defn my-assoc-in
  [m [k & ks] val]
  (let [kv (get m k {})]
    (if (pos? (count ks))
      (assoc m k (my-assoc-in kv ks val))
      (assoc m k val))))

(def some-map {:languages {:clojurescript :awesome}})
(my-assoc-in some-map [:languages :javascript] :not-awesome)

;4
(update-in {:a 10} [:age] (fnil + 0) 10)
(update-in {:age 10} [:info :name] str "cljs")

;5
(defn my-update-in
  [m [k & ks] f & args]
  ; we could omit default param {} when getting the value in which case kv will sometimes return nil,
  ; this will work because when assoc get nil as first param, it will create new hash-map
  (let [kv (get m k {})]
    (if (pos? (count ks))
      (assoc m k (apply my-update-in kv ks f args))
      (assoc m k (apply f (get m k) args)))))

(my-update-in {:age 100} [:age] / 2 5 3)
(my-update-in {:info {:len 100}} [:info :len] / 2 5 3)
(my-update-in {:age 10} [:info :name] str "clj")


(ns clojure-brave.exercises.section-9)

; NOTE:
;Rather than sending requests to Bing or Google,
;i will use simpler approach and just simulate some long running action with wait function.
;Sending request to Google/Bing won't always work.

(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

; 1 skipped, Exercise 2 is generalisation of 1, so I implemented just exercise 2.

; 2
; If you are familiar with Javascript's promises, this is basically Promise.race pattern,
; we have N concurrent processes, and we should return promise that resolves to result of process that finishes first
(defn p-race
  [values]
  (let [p (promise)]
    (doseq [v values]
      (future (wait (* v 1000) (deliver p v))))
    p))

(def p (p-race [3 2 5 7]))
(println @p)

; 3
; If you are familiar with Javascript's promises, this is basically Promise.all pattern
; We have N concurrent processes, and we should return promise that resolves to array of data returned by all processes
(defn p-all
  [values]
  (let [promises (map (fn [v]
                        (future (wait (* v 1000) v))) values)]
    (future (map deref promises))))

(def p-data (p-all [3 2 2 1]))
(println @p-data)

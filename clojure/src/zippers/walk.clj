(ns zippers.walk
  (:require [clojure.string :as string]
            [clojure.walk :as walk])
  (:use [zippers.domain]))

;; generic walk example

(def data [[1 :foo] [2 [3 [4 "abc"]] 5]])

(def output
     (walk/postwalk
      #(if (number? %) (inc %) %)
      data))

(walk/postwalk
 (fn [m]
   (println "walking to" m)
   (if (integer? m) (+ m 1) m))
 {:a 1 :b 2 :c {:d 2}})

;; walk variant

(defn eval-concat [node]
  (if (and (= :concat (node-type node))
            (every? string? (:args node)))
    (string/join (:args node))
     node))

(defn walk-example
  [node]
  (walk/postwalk eval-concat node))



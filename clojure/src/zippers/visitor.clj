(ns zippers.visitor
  (:require [clojure.string :as string])
  (:use [zippers.domain])
  (:import [zippers.domain]))

;; partial implementation of a manual recursive visitor

(defmulti eval-concat node-type)
(defmethod eval-concat :default [node] node)
(defmethod eval-concat :concat [concat]
           (let [arg-eval (map eval-concat (:args concat))]
             (if (every? string? arg-eval)
               (string/join arg-eval)
               (new-concat {:args arg-eval}))))
(defmethod eval-concat :compare-criteria [{:keys (left right) :as crit}]
           (new-compare-criteria {:left (eval-concat left)
                                  :right (eval-concat right)}))

;; running the example

(defn eval-concat-example []
  (println " result = " (eval-concat crit)))

(ns zippers.zip
  (:require [clojure.string :as string]
            [clojure.zip :as zip])
  (:use [zippers.domain]
        [zippers.walk :only (eval-concat)]
        [clojure.pprint]))


;; PUT generic zipper stuff here in the article 

(def vz (zip/vector-zip [:compare [:concat "a" "b"] "ab"]))

(println (-> vz
             zip/down
             zip/node
;;  ;           zip/right
  ;;           zip/down
     ;;        zip/node
             ))


;; create zipper on crit

(def cz (zip/seq-zip (seq crit)))

;; manually walking the tree via the zipper

(def concat-loc (-> cz
                    zip/down
                    zip/right))

;; ... and applying an edit function to modify the tree

(defn eval-concat-zip [[key val]]
  [key (eval-concat val)])

(def new-tree (zip/root (zip/edit concat-loc eval-concat-zip)))

;; generic tree editing

(defn tree-edit [zipper matcher editor]
  (loop [loc zipper]
    (if (zip/end? loc)
      (zip/root loc)
      (if-let [matcher-result (matcher loc)]
        (let [new-loc (zip/edit loc (partial editor matcher-result))]
          (if (not (= (zip/node new-loc) (zip/node loc)))
            (zip/root new-loc)
            (recur (zip/next loc))))
        (recur (zip/next loc))))))

;; matcher 
(defn can-simplify-concat [loc]
  (let [[key val] (zip/node loc)]
                             (and (= :concat (node-type val))
                                  (every? string? (:args val)))))

;; editor function
(defn simplify-concat [_ node]
  [key (string/join (:args val))])

(def mod-tree (tree-edit cz can-simplify-concat simplify-concat))


;; then move on to a more generic function stack


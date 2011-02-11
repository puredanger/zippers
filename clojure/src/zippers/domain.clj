(ns zippers.domain
  (:require [clojure.set :as set]))

(defn- expected-keys? [map expected-key-set]
  (not (seq (set/difference (set (keys map)) expected-key-set))))

(defmacro defnode
  "Create a constructor function for a typed map and a well-known set of
   fields (which are validation checked). Constructor will be
     (defn new-<node-type> [field-map])."
  [node-type [& fields]]
  (let [constructor-name (symbol (str "new-" node-type))]
    `(defn ~constructor-name [nv-map#]
       {:pre [(map? nv-map#)
              (expected-keys? nv-map# ~(set (map keyword fields)))]}
       (assoc nv-map# :type (keyword '~node-type)))))

(defn node-type [ast-node] (:type ast-node))

(defnode column [table column])
(defnode compare-criteria [left right])
(defnode concat [args])
(defnode filter [criteria child])
(defnode join [type left right])
(defnode project [projections child])
(defnode table [name])
(defnode value [value])

;; example nodes

(def concat-ab (new-concat {:args ["a" "b"]}))
(def crit (new-compare-criteria {:left concat-ab
                                 :right "ab"}))


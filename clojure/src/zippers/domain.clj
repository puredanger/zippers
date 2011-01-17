(ns zippers.core)

(defrecord Column [table column])
(defrecord CompareCriteria [left-expr right-expr])
(defrecord Concat [args])
(defrecord Filter [criteria child])
(defrecord Join [type criteria left right])
(defrecord Project [projections child])
(defrecord Table [name])
(defrecord Value [value])


(ns zippers.zip
  (:require [clojure.string :as string]
            [clojure.zip :as zip])
  (:use [zippers.domain]
        [zippers.walk :only (eval-concat)]
        [clojure.pprint])
  (:import [clojure.lang IPersistentVector IPersistentMap IPersistentList ISeq]))


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

;; tree zipper

(defmulti tree-branch? class)
(defmethod tree-branch? :default [_] false)
(defmethod tree-branch? IPersistentVector [v] true)
(defmethod tree-branch? IPersistentMap [m] true)
(defmethod tree-branch? IPersistentList [l] true)
(defmethod tree-branch? ISeq [s] true)

(defmulti tree-children class)
(defmethod tree-children IPersistentVector [v] v)
(defmethod tree-children IPersistentMap [m] (seq m))
(defmethod tree-children IPersistentList [l] l)
(defmethod tree-children ISeq [s] s)

(defmulti tree-make-node (fn [node children] (class node)))
(defmethod tree-make-node IPersistentVector [v children]
           (vec children))
(defmethod tree-make-node IPersistentMap [m children]
           (apply hash-map (apply concat children)))
(defmethod tree-make-node IPersistentList [_ children]
           children)
(defmethod tree-make-node ISeq [node children]
           (apply list children))
(prefer-method tree-make-node IPersistentList ISeq)

(defn tree-zipper [node]
  (zip/zipper tree-branch? tree-children tree-make-node node))

;; generic tree editing

(defn tree-edit [zipper matcher editor]
  (loop [loc zipper]
    (println "iter " (zip/node loc) "class" (class (zip/node loc)) "end?" (zip/end? loc))
    (if (zip/end? loc)
      (zip/root loc)
      (if-let [matcher-result (matcher (zip/node loc))]
        (let [new-loc (zip/edit loc (partial editor matcher-result))]
          (recur (zip/next new-loc))
          #_(if (not (= (zip/node new-loc) (zip/node loc)))
              (zip/root new-loc)
              (recur (zip/next loc))))
        (recur (zip/next loc))))))

;; matcher 
(defn can-simplify-concat [node]
  ;;(println "match node = " node "concat=" (= :concat (node-type node)) "strings=" (every? string? (:args val)))
  (and (= :concat (node-type node))
       (every? string? (:args val))))

;; editor function
(defn simplify-concat [_ node]
  ;;(println "edit " node)
  (string/join (:args node)))

(defn simplify-concat-zip [loc]
  (tree-edit loc can-simplify-concat simplify-concat))

(defn zip-example []
  (simplify-concat-zip (tree-zipper crit)))

;; then moving on to a more generic function stack

;; useful features:
;; - visitor stacking with "next"
;; - early abort with return "stop"
;; - carry state through iteration
;; - pre/post iteration

(defn new-context []
  {:state nil
   :node nil
   :stop true
   :next true})

(comment
  (defn visitor [node state]
    context))

(defn visit-node 
  [start-node start-state visitors]
  (loop [node start-node
         state start-state
         [first-visitor & rest-visitors] visitors]
    (let [context (merge {:node node, :state state, :stop false, :next false}
                         (first-visitor node state))
          {new-node :node
           new-state :state
           :keys (stop next)} context]
      (if (or next stop (nil? rest-visitors))
        {:node new-node, :state new-state, :stop stop}
        (recur new-node new-state rest-visitors)))))

(defn tree-visitor
  ([zipper visitors]
     (tree-visitor zipper nil visitors))
  ([zipper initial-value visitors]
     (loop [loc zipper
            state initial-value]
       (let [context (visit-node (zip/node loc) state visitors)
             new-node (:node context)
             new-state (:state context)
             stop (:stop context)          
             new-loc (if (= new-node (zip/node loc))
                       loc
                       (zip/replace loc new-node))
             next-loc (zip/next new-loc)]
         (if (or (zip/end? next-loc) (= stop true))
           {:node (zip/root new-loc) :state new-state}
           (recur next-loc new-state))))))

;; rewritten example

(defn of-type
  "Is the node passed of the specified type?"
  [node type]
  (= type (node-type node)))

(defn eval-concat-visitor [node state]
  (when (and (of-type node :concat)
             (every? string? (:args val)))
    {:node (string/join (:args node))}))

(defn visitor-example [node]
  (:node
   (tree-visitor (tree-zipper node) [eval-concat-visitor])))

;; collector example

(defn string-visitor
  [node state]
  (when (string? node)
    {:state (conj state node)}))

(defn string-finder [node]
  (:state
   (tree-visitor (tree-zipper node) #{} [string-visitor])))

;; finder example

(defn matched [type node state]
  (when (of-type node type)
    {:stop true
     :state node}))

(defn find-first [node type]
  (:state
   (tree-visitor (tree-zipper node) [(partial matched type)])))

;; stacked

(defn on [type]
  (fn [node state]
    (when-not (of-type node type)
      {:jump true})))

(defn all-strings []
  (fn [{args :args} _]
    (when-not (every? string? args)
      {:jump true})))

(defmulti eval-expr node-type)
(defmethod eval-expr :default [x] x)
(defmethod eval-expr :concat [{args :args :as node}]
           (string/join args))
(defmethod eval-expr :compare-criteria [{:keys (left right) :as node}]
           (if (= left right) true node))

(defn node-eval [node state]
  {:node (eval-expr node)})

(defn stacked-example [node]
  (:node
   (tree-visitor (tree-zipper node)
                 [(on :concat)
                  (all-strings)
                  node-eval])))

;; post visit



;; variations
;; - post/pre visit
;; - records

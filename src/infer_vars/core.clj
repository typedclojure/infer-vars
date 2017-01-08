(ns infer-vars.core
  {:core.typed {:experimental #{:infer-vars}}}
  (:require [clojure.core.typed :as t]))

(t/tc-ignore
  (defn untyped-fn [a b]
    (str a b)))

(untyped-fn 1 2)
(untyped-fn "a" 2)

(comment
  ;; With {:core.typed {:experimental #{:infer-vars}}}
  ;; metadata, `untyped-fn` will be allowed to be unannotated.
  ;;
  ;; If we call check-ns, `untyped-fn` will be given a very
  ;; permissive type, and the type checker will try and
  ;; infer its annotation based on usages.
  (t/check-ns)

  ;; To retrieve the annotation core.typed thinks `untyped-fn`
  ;; should have, run:
  (t/infer-unannotated-vars)
  ;=> [(t/ann infer-vars.core/untyped-fn [t/Any t/Any -> t/Any])]

  ;; Copy and paste this annotation into your namespace
  ;; and change the ns metadata to: {:core.typed {}}
  (t/check-ns)

  ;; Now `untyped-fn` is checked at [t/Any t/Any -> t/Any]
  )

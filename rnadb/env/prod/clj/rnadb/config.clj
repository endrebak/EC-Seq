(ns rnadb.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[rnadb started successfully]=-"))
   :middleware identity})

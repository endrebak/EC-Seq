(ns rna.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[rna started successfully]=-"))
   :middleware identity})

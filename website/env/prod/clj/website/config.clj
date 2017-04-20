(ns website.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[website started successfully]=-"))
   :middleware identity})

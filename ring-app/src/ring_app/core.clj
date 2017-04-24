(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.http-response :as response]
            [compojure.core :as compojure]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.format :refer [wrap-restful-format]]))

(defn display-profile [id]
  ;;
  )

(defn display-settings [id]
  ;;
  )

(defn change-password [id]
  ;;
  )

(def user-routes
  (compojure/context "/user/:id" [id]
           (compojure/GET "/profile" [] (display-profile id))
           (compojure/GET "/settings" [] (display-settings id))
           (compojure/GET "/change-password" [] (change-password id))))


(defn response-handler [request]
  (response/ok
   (str "<html><body> your IP is: "
        (:remote-addr request)
        "</body></html>")))

(compojure/defroutes handler
  (compojure/GET "/foo" request (interpose ", " (keys request)))
  (compojure/GET "/" request response-handler)
  (compojure/GET "/:id" [id] (str "<p>the id is: " id "</p>"))
  (compojure/POST "/json" [id] (response/ok {:result id})))

(defn wrap-nocache [handler]
  (fn [request]
    (-> request
        handler
        (assoc-in [:headers "Pragma"] "no-cache"))))

(defn wrap-formats [handler]
  (wrap-restful-format
   handler
   {:formats [:json-kw :transit-json :transit-msgpack]}))


(defn -main []
  (jetty/run-jetty
   (-> #'handler wrap-nocache wrap-reload wrap-formats)
   ;; (-> handler var wrap-nocache wrap-reload)
   {:port 3000
    :join? false}))

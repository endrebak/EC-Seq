(ns rna.routes.home
  (:require [rna.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [rna.db.core :as db]
            ))

(defn home-page []
  (layout/render
   "home.html"))


(defn about-page []
  (layout/render "about.html"))


(defn gene-info-page [{{gene-name :name} :params}]
  (do
    (println gene-name)
    (let [gene-name-cleaned (clojure.string/trim gene-name)
          query-result (db/get-gene {:id gene-name-cleaned})]
      (println query-result)
      (layout/render "gene.html"
                     {:results query-result}))))


(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/search" request (gene-info-page request))
  (GET "/about" [] (about-page)))

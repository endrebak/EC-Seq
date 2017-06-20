(ns rna.routes.home
  (:require [rna.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [rna.db.core :as db]
            [clojure.data.json :as json]))

(defn home-page []
  (layout/render
   "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defn lookup-gene [term]
  "Lookup gene name in local db, then do name-lookup in mygene, then local db
  search again."
  (let [gene-name-cleaned (clojure.string/trim term)
        query-result (db/get-gene {:id gene-name-cleaned})]
    (if (seq query-result)
      query-result)))

(defn lookup-gene-local [term]
  "Lookup gene name in local db"
  (let [gene-name-cleaned (clojure.string/trim term)]
    (db/get-gene {:id gene-name-cleaned})))

(defn lookup-gene-mygene [term]
  "Lookup gene in mygene.info for name conversion"
    (let [term (clojure.string/replace term " " "%20")
          url (format "https://mygene.info/v3/query?q=%s&species=rat" term)
          result (json/read-str (slurp url) :key-fn keyword)]
      result))

(defn genes-in-local-db [mygene-results]
  "Need to ensure that the genes are in the local database before we display
  them as a suggestion."
    (filter #(seq (lookup-gene-local %1)) (map :symbol mygene-results)))

(defn gene-info-page [{{gene-name :name} :params}]
  "Display either the result page for the gene or suggestions for synonyms"
    (let [gene-name-cleaned (clojure.string/trim gene-name)
          query-result (db/get-gene {:id gene-name-cleaned})]

      ;; found in local database so display page
      (if (seq query-result)
        (layout/render "gene.html"
                       {:results query-result
                        :term gene-name-cleaned})
        ;; need to search for gene in mygene
        (let [mygene-results (lookup-gene-mygene gene-name-cleaned)
              mygene-in-local-db (genes-in-local-db (:hits mygene-results))]
          (if (== (count mygene-in-local-db) 1)
            ;; if only one mygene hit, look up graph
            (layout/render "gene.html"
                           {:results (db/get-gene {:id (first mygene-in-local-db)})
                            :term gene-name-cleaned})
            ;; otherwise show page with links
            (layout/render "mygene.html"
                           {:results mygene-in-local-db
                            :term gene-name-cleaned}))))))


(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/search" request (gene-info-page request))
  (GET "/about" [] (about-page)))

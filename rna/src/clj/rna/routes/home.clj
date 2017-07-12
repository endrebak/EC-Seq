(ns rna.routes.home
  (:require [rna.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [rna.db.core :as db]
            [clojure.data.json :as json]
            [clojure.set :as set]))

(defn home-page []
  (layout/render
   "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defn browse-page [layer layer-results]
  (layout/render "browse.html" layer layer-results))



(defn lookup-gene [term]
  "Lookup gene name in local db, then do name-lookup in mygene, then local db
  search again."
  (let [search-term-cleaned (clojure.string/trim term)
        query-result (db/get-gene {:id search-term-cleaned})]
    (if (seq query-result)
      query-result)))


(defn lookup-gene-local [term]
  "Lookup gene name in local db"
  (let [search-term-cleaned (clojure.string/trim term)]
    (db/get-gene {:id search-term-cleaned})))


(defn lookup-gene-mygene [term]
  "Lookup gene in mygene.info for name conversion"
    (let [term (clojure.string/replace term " " "%20")
          url (format "https://mygene.info/v3/query?q=%s&species=rat" term)
          result (json/read-str (slurp url) :key-fn keyword)]
      result))


(defn genes-in-local-db [mygene-results]
  "Need to ensure that the genes are in the local database before we display
  them as a suggestion."
  (filter #(seq (lookup-gene-local (:symbol %1))) (map #(select-keys %1 [:name :symbol]) mygene-results)))


(defn query-results [search-term-cleaned mygene-in-local-db]
  (let [query-result (db/get-gene {:id search-term-cleaned})]
    ;; if hit in local db
    (if (seq query-result)
      query-result
      ;; if only one mygene result, show it
      (if (= (count mygene-in-local-db) 1)
        (db/get-gene {:id (:symbol (first mygene-in-local-db))})))))


(defn add-gene-name-to-query-results [query-result mygene-in-local-db]
    (let [query-result-symbol (map #(set/rename-keys %1 {:id :symbol}) query-result)]
          (map #(into {} %1) (set/join query-result-symbol mygene-in-local-db))))


(defn gene-info-page [{{gene-name :name} :params}]
  "Display either the result page for the gene or suggestions for synonyms"
  (let [search-term-cleaned (clojure.string/trim gene-name)
        mygene-results (lookup-gene-mygene search-term-cleaned)
        mygene-in-local-db (genes-in-local-db (:hits mygene-results))
        query-result (query-results search-term-cleaned mygene-in-local-db)
        query-results-name (add-gene-name-to-query-results query-result mygene-in-local-db)]

      (if (seq query-result)
        (layout/render "gene.html"
                       {:results query-results-name
                        :term search-term-cleaned})
        ;; no query results, show potential matches from mygene.info
        (layout/render "mygene.html"
                       {:results mygene-in-local-db
                        :term search-term-cleaned}))))

(defn toptable-row-relevant-keys [layer toptable-row]
    (map #(keyword %1) (filter #(.contains %1 layer)  (map name (keys toptable-row)))))

(defn toptable-relevant-items [layer toptable-results]
  (let [tt-keys (toptable-row-relevant-keys layer (first toptable-results))]
    (map #(select-keys %1 tt-keys) toptable-results)))

(defn toptable-rename-items [layer toptable-results]
  nil)

(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/search" request (gene-info-page request))
  ;; (POST "/toptable" request (toptable-page request))
  (GET "/about" [] (about-page))
  (GET "/browse" [] (browse-page)))

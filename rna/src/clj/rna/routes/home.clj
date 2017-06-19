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
  (let [gene-name-cleaned (clojure.string/trim term)
        query-result (db/get-gene {:id gene-name-cleaned})]))

(defn lookup-gene-mygene [term]
  "Lookup gene in mygene.info for name conversion"
    (let [term (clojure.string/replace term " " "%20")
          url (format "https://mygene.info/v3/query?q=%s&species=rat" term)
          result (json/read-str (slurp url) :key-fn keyword)]
      result))

(defn are-genes-in-local-db [mygene-results]
  "Need to ensure that the genes are in the local database before we display
  them as a suggestion."
  )

(defn gene-info-page [{{gene-name :name} :params}]
    (let [gene-name-cleaned (clojure.string/trim gene-name)
          query-result (db/get-gene {:id gene-name-cleaned})]

      ;; found in local database so display page
      (if (seq query-result)
        (layout/render "gene.html"
                       {:results query-result
                        :term gene-name-cleaned})
        ;; need to search for gene in mygene
        (let [mygene-results (lookup-gene-mygene gene-name-cleaned)

              ]
           (layout/render "mygene.html"
                          {:results (:hits mygene-results)
                           :term gene-name-cleaned})))))

      ;;   (do (print "query result empty")
      ;;       (print gene-name-cleaned)
      ;;       (print (lookup-mygene gene-name-cleaned))))
      ;; (layout/render "gene.html"
      ;;                {:results query-result
      ;;                 :term gene-name-cleaned}))))


(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/search" request (gene-info-page request))
  (GET "/about" [] (about-page)))

;; :max_score 26.687895, :took 7, :total 8, :hits [{:_id 309035, :_score 26.687895, :entrezgene 309035, :name defective in cullin neddylation 1 domain containing 3,
;; :symbol Dcun1d3, :taxid 10116} {:_id 315405, :_score 26.687895, :entrezgene
;;  315405, :name defective in cullin neddylation 1 domain containing 5, :symbol
;;  Dcun1d5, :taxid 10116} {:_id 688913, :_score 26.66662, :entrezgene
;;  688913, :name defective in cullin neddylation 1 domain containing 2, :symbol
;;  Dcun1d2, :taxid 10116} {:_id 103694000, :_score 26.133953, :entre zgene
;;  103694000, :name DCN1, defective in cullin neddylation 1, domain containing
;;  2, :symbol NEWGENE_1582994, :taxid 10116} {:_id 310324, :_score
;;  26.133953, :entrezgene 310324, :nam e defective in cullin neddylation 1 domain
;;  containing 1, :symbol Dcun1d1, :taxid 10116} {:_id 360928, :_score
;;  25.975481, :entrezgene 360928, :name defective in cullin neddylation 1 domain
;;  containing 4, :symbol Dcun1d4, :taxid 10116} {:_id ENSRNOG00000052837, :_score
;;  25.975481, :name defective in cullin neddylation 1 domain containing 2, :symbol
;;  Dcun1d2, :taxid 10116} {:_id 117152, :_score 9.431691, :entrezgene
;;  117152, :name cullin-associated and neddylation-dissociated 1, :symbol
;;  Cand1, :taxid 10116}

(ns paste-bin.pastes
  (require [taoensso.faraday :as far]))

(def conn
  {:access-key "Soup"
   :secret-key "Or Secret"
   :endpoint "http://localhost:8000"})

(defn find [id]
  (:string (far/get-item conn :pastes {:id id})))

(defn post [id string]
  (let [new-id (if (empty? id) (.toString (java.util.UUID/randomUUID)) id)]
    (far/put-item conn :pastes {:id new-id :string string})
    new-id))

(defn init-tables []
  (if-not (some #(= % :pastes) (far/list-tables conn))
    (far/create-table conn :pastes
                      [:id :s]
                      {:throughput {:read 25 :write 25} :block? true})))
(init-tables)

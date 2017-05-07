(ns paste-bin.pastes
  (require [taoensso.faraday :as far]))

(def conn
  {:access-key "Soup"
   :secret-key "Or Secret"
   :endpoint "http://localhost:8000"})

(defn recover [id]
  "Get your strings here, we use recover because get is in the standard library"
  (:string (far/get-item conn :pastes {:id id})))

(defn post [id string]
  "Put a string in your storage thing"
  (let [new-id (if (empty? id) (.toString (java.util.UUID/randomUUID)) id)]
    (far/put-item conn :pastes {:id new-id :string string})
    new-id))

(defn init-tables []
  "This allows us to initialize the tables or throw if the configuration is wrong"
  (if-not (some #(= % :pastes) (far/list-tables conn))
    (far/create-table conn :pastes
                      [:id :s]
                      {:throughput {:read 25 :write 25} :block? true})))

;; Which we will do when the server is brought up
(init-tables)

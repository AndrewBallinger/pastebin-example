(ns paste-bin.pastes
  (require [clojure.java.jdbc :as sql]
           [clojure.pprint :refer (pprint)]
           [hikari-cp.core :refer :all]))

(def datasource (make-datasource {:jdbc-url "jdbc:sqlite:pastes.db"}))
(def conn {:datasource datasource})
(def tables (sql/query conn "SELECT * FROM sqlite_master WHERE type='table';"))

(def db-schema
  (sql/create-table-ddl :pastes
                        [[:_id "text primary key"]
                         [:string "text not null"]]))

(defn recover [id]
  "Get your strings here, we use recover because get is in the standard library"
  (-> (sql/query conn ["SELECT string FROM pastes WHERE _id = ?;" id])
      first
      :string))

(defn post [id string]
  "Put a string in your storage thing"
  (let [new-id (if (empty? id) (.toString (java.util.UUID/randomUUID)) id)]
    (if-not (recover id)
      (sql/insert! conn :pastes {:_id new-id :string string})
      (sql/update! conn :pastes {:string string} ["_id = ?" new-id]))
    new-id))

;; If this is the first time through, go ahead and create the table
(if-not (some #(-> % :name (= "pastes")) tables)
  (sql/db-do-commands conn db-schema))

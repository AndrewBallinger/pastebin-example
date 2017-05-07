(ns paste-bin.core
  (require [ring.adapter.jetty :refer (run-jetty)]
           [ring.middleware.defaults :refer :all]
           [ring.middleware.x-headers :refer :all]
           [clojure.pprint :refer (pprint)]
           [taoensso.faraday :as far])
  (:gen-class))

(def conn
  "The client location"
  {:access-key "Soup"
   :secret-key "Secret"
   :endpoint "http://localhost:8000"})

(defn paste [id string]
  (pprint id)
  (pprint string)
  (let [new-id (if (empty? id) (.toString (java.util.UUID/randomUUID)) id)]
    (far/put-item conn :pastes {:id new-id :string string})
    new-id))

(defn on-post [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (paste (subs (:uri request) 1)
                (slurp (:body request)))})

(defn on-get [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (or (:string (far/get-item conn :pastes
                           {:id (subs (:uri request) 1)}))
             "")})

(defn handler [request]
  (pprint request)
  (if (= (:request-method request) :post)
    (on-post request)
    (on-get request)))

(def site
  (-> handler
      (wrap-defaults api-defaults)
      (wrap-content-type-options :nosniff)
      (wrap-frame-options :deny)))

(defn -main
  "When executed, runs a jetty server"
  [& args]
  (run-jetty site {:port 3000 :join? false}))

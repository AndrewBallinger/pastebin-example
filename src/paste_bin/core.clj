(ns paste-bin.core
  (require [ring.adapter.jetty :refer (run-jetty)]
           [ring.middleware.defaults :refer :all]
           [ring.middleware.x-headers :refer :all]
           [clojure.pprint :refer (pprint)]
           [paste-bin.pastes :as pastes]
           [taoensso.faraday :as far])
  (:gen-class))

(defn on-post [key body]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pastes/post key (slurp body))})

(defn on-get [key]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (or (pastes/find key) "")})

(defn handler [request]
  (let [key (subs (:uri request) 1)
        body (:body request)]
  (if (= (:request-method request) :post)
    (on-post key body)
    (on-get key))))

(def site
  (-> handler
      (wrap-defaults api-defaults)
      (wrap-content-type-options :nosniff)
      (wrap-frame-options :deny)))

(defn -main
  "When executed, runs a jetty server"
  [& args]
  (pprint "Listening on port 3000")
  (run-jetty site {:port 3000 :join? true}))

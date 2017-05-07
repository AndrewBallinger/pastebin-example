(ns paste-bin.core
  (require [ring.adapter.jetty :refer (run-jetty)]
           [ring.middleware.defaults :refer :all]
           [ring.middleware.x-headers :refer :all]
           [clojure.pprint :refer (pprint)]
           [clojure.core.async :refer (alts!! <!! >!! go timeout chan close!)]
           [paste-bin.pastes :as pastes]
           [taoensso.faraday :as far])
  (:gen-class))

(defn on-post [key body]
  (let [c (chan)]
    (go (>!! c {:status 200
                :headers {"Content-Type" "text/plain"}
                :body (pastes/post key (slurp body))})
        (close! c))
    (let [[result source] (alts!! [c (timeout 10000)])]
          (if (= source c) result {:status 500}))))

(defn on-get [key]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (or (pastes/recover key) "")})

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

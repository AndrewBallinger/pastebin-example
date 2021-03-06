(ns paste-bin.core
  (require [ring.adapter.jetty :refer (run-jetty)]
           [ring.middleware.defaults :refer :all]
           [ring.middleware.x-headers :refer :all]
           [clojure.pprint :refer (pprint)]
           [paste-bin.nanny :refer (scrub)]
           [paste-bin.pastes :as pastes])
  (:gen-class))

(defn on-post [key body]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pastes/post key (scrub (slurp body)))})

(defn on-get [key]
  (let [result (pastes/recover key)]
    (if (nil? result)
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (pastes/recover "index")}
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body result})))

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
  (let [port (Integer/parseInt (first args))]
    (pprint (str "Attaching to port " port))
    (run-jetty site {:port port :join? true})))

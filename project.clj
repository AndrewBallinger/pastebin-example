(defproject paste-bin "0.1.0-SNAPSHOT"
  :description "A simple paste application. Mother approved."
  :url "paste.stratisopt.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [ring/ring-defaults "0.3.0"]
                 [com.taoensso/faraday "1.9.0"]]
  :main ^:skip-aot paste-bin.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

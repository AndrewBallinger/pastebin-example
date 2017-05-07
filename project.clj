(defproject paste-bin "0.1.0-SNAPSHOT"
  :description "A simple paste application. Mother approved."
  :url "paste.stratisopt.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.xerial/sqlite-jdbc "3.16.1"]
                 [hikari-cp "1.7.5"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [ring/ring-defaults "0.3.0"]]
  :main ^:skip-aot paste-bin.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

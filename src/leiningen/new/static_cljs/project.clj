(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "{{raw-name}} static frontend"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [cyg "0.1.4"]
                 [stasis "1.0.0"]
                 [ring "1.2.1"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]
                 [enlive "1.1.5"]
                 [clygments "0.1.1"]
                 [optimus "0.14.2"]
                 [optimus-less "0.1.0"]]
  :ring {:handler {{raw-name}}.web/app}
  :aliases {"export" ["with-profile" "prod" "run" "-m" "build.core/export"]}
  :source-paths ["src" "build-src"]
  :profiles {:prod {:resource-paths ["config/prod"]}
             :dev {:resource-paths ["config/dev"]
                   :plugins [[lein-ring "0.8.10"]]
                   :dependencies [[me.raynes/conch "0.5.0"]]}}
  :target-path "target/%s")

(ns {{raw-name}}.web
  (:require
    [optimus.assets :as assets]
    [optimus.link :as link]
    [optimus.optimizations :as optimizations]
    [optimus.prime :as optimus]
    [optimus.hiccup]
    [cyg.core :refer [cf] :as cyg]
    [optimus.strategies :refer [serve-live-assets serve-frozen-assets]]
    [optimus-less.core]
    [clojure.java.io :refer [resource]]
    [clojure.java.io :as io]
    [hiccup.page :refer [html5]]
    [stasis.core :as stasis]))

(def env (cf :env))
(def dev? (= env :dev))
; enable reloadable config
(when dev? (cyg/autoreload!))

(defn layout-page [request page]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     [:title "{{name}}"]
     (optimus.hiccup/link-to-css-bundles request ["bootstrap.css" "site.css"])
     (optimus.hiccup/link-to-js-bundles request ["site.js"])]
    [:body
     [:div.hero
      [:div.container
       [:h1 "{{name}}"]]]
     [:div.container [:div.body page]]
     [:footer.container "Copyright &copy; {{year}} {{name}}"]]))

(defn get-assets []
  (concat (assets/load-bundles "public"
                               {"bootstrap.css" ["/css/bootstrap.css"
                                                 "/css/font-awesome.css"]
                                "site.css" ["/css/site.less"]})
          (assets/load-bundles "public"
                               {"site.js" ["/js/jquery-2.1.0.js"
                                           "/js/bootstrap.min.js"]})
          (assets/load-assets "public" [#"/fonts/.+"])))

(defn partial-pages [pages]
  (zipmap (keys pages)
          (map #(fn [req] (layout-page req %)) (vals pages))))

(defn get-pages []
  (stasis/merge-page-sources
   {:public (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
    :partials (partial-pages (stasis/slurp-directory "resources/partials" #".*\.html$"))}))

(def app
  (optimus/wrap
    (stasis/serve-pages get-pages)
    get-assets
    (if dev? optimizations/none optimizations/all)
    (if dev? serve-live-assets serve-frozen-assets)))


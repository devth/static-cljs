(ns leiningen.new.static-cljs
  (:require
    [clojure.java.io :as io]
    [leiningen.new.templates :refer [renderer year project-name
                                     ->files sanitize-ns name-to-path
                                     multi-segment]]
    [leiningen.core.main :as main]))

(def resource-list
  ["public/css/bootstrap.css"
   "public/css/bootstrap.css.map"
   "public/css/bootstrap.min.css"
   "public/css/font-awesome.css"
   "public/css/site.less"
   "public/fonts/fontawesome-webfont.eot"
   "public/fonts/fontawesome-webfont.svg"
   "public/fonts/fontawesome-webfont.ttf"
   "public/fonts/fontawesome-webfont.woff"
   "public/fonts/FontAwesome.otf"
   "public/fonts/glyphicons-halflings-regular.eot"
   "public/fonts/glyphicons-halflings-regular.svg"
   "public/fonts/glyphicons-halflings-regular.ttf"
   "public/fonts/glyphicons-halflings-regular.woff"
   "public/js/bootstrap.min.js"
   "public/js/jquery-2.1.0.js"])

(defn resources []
  (map (fn [path]
         [(str "resources/" path)
          (-> path io/resource io/input-stream)])
       resource-list))

(defn static-cljs
  "Generate a static site that uses ClojureScript and Lesscss."
  [name]
  (let [sanitized (name-to-path name)
        render (renderer "static-cljs")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :sanitized (name-to-path name)
              :year (year)}]
    (main/info "Generating fresh static-cljs project.")
    (apply ->files data
           [".gitignore" (render "gitignore" data)]
           ["project.clj" (render "project.clj" data)]
           ["README.md" (render "README.md" data)]
           ["build-src/build/core.clj" (render "build.clj" data)]
           ["config/dev/config.clj" (render "config_dev.clj" data)]
           ["config/prod/config.clj" (render "config_prod.clj" data)]
           ["src/{{sanitized}}/web.clj" (render "web.clj" data)]
           ["resources/partials/index.html" (render "index.html" data)]
           ; static resources
           (resources))))

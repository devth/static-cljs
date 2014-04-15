(ns build.core
  (:require
    [{{raw-name}}.web :refer :all]
    [optimus.export]
    [optimus.optimizations :as optimizations]
    [stasis.core :as stasis]))

;; export

(def export-dir "dist")

(defn export []
  (println "Exporting site build to" export-dir)
  (let [assets (optimizations/all (get-assets) {})]
    (stasis/empty-directory! export-dir)
    (optimus.export/save-assets assets export-dir)
    (stasis/export-pages (get-pages) export-dir {:optimus-assets assets})
    (println "Export complete")))


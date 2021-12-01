(ns uix.benchmark
  (:require-macros [uix.benchmark :refer [bench]])
  (:require [reagent.core :as r]
            ["react-dom/server" :as rserver]
            [react :as react]
            [uix.hiccup :as hiccup]
            [uix.react :refer [Editor]]))

(defn render [el]
      (rserver/renderToString el))

(def reagent-compiler
  (r/create-compiler {:function-components true}))

(js/console.log "Warming up...")
(bench :react 100000 (render (react/createElement Editor)))
(bench :uix-compiled 100000 (render #el [hiccup/editor-compiled]))
(bench :reagent-interpret 100000 (render (r/as-element [hiccup/editor])))

(js/console.log "Running the benchmark...")
(let [react-t (bench :react 100000 (render (react/createElement Editor)))
      uix-t (bench :uix-compiled 100000 (render #el [hiccup/editor-compiled]))
      reagent-t (bench :reagent-interpret 100000 (render (r/as-element [hiccup/editor])))]
  (js/testsDone #js [react-t uix-t reagent-t]))

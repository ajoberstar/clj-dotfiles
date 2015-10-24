(ns org.ajoberstar.clj-dotfiles
  (:require [ike.cljj.file :as file]
            [clojure.edn :as edn]
            [clojure.set :refer [map-invert]]
            [clojure.pprint :refer [pprint]])
  (:import (java.nio.file LinkOption))
  (:gen-class))

(defn read-file
  "Reads from an EDN file."
  [path-str]
  (->> path-str
       (file/read-bytes)
       (String.)
       (edn/read-string)))

(defn process-config
  "Parses EDN config file and translates to the actual paths within the home
   and dotfiles directories that should be used."
  [home-path dotfiles-path config-path]
  (let [config (read-file config-path)
        home (file/as-path home-path)
        dotfiles (file/as-path dotfiles-path)]
    (->> config-path
         read-file
         (map (fn [[src dest]] [(.resolve dotfiles src) (.resolve home dest)]))
         (into {}))))

(defn classifier
  "Determines whether a src/dest pair is in an invalid state (:invalid), already
  configured as requested (:done) or needs to be linked (:todo)."
  [[src dest]]
  (cond
    (not (file/exists? src)) :invalid
    (and (file/exists? dest) (file/same? src dest)) :done
    (file/exists? dest) :invalid
    :else :todo))

(defn fancy-path
  "Returns a 'fancy' suffix for a file."
  [path]
  (cond
    (not (file/exists? path)) "(missing)"
    (file/link? path) (str "(=> " (.toRealPath path (into-array LinkOption [])) ")")
    (file/dir? path) "(dir exists)"
    :else "(file exists)"))

(defn print-chunk
  "Prints a set of src/dest pairs in a human-friendly format."
  [msg config]
  (when (seq config)
    (println msg)
    (doseq [[src dest] config]
      (println "* Src: " (str src) (fancy-path src))
      (println "  Dest:" (str dest) (fancy-path dest)))
    (println)))

(defn install
  "Links the passed src/dest pairs"
  [config]
  (println "Beginning install...")
  (doseq [[src dest] config]
    (println "  Linking" (str dest) "to" (str src))
    (file/make-dir (.getParent dest) true)
    (file/make-link dest src)))

(defn -main [home-path dotfiles-path config-path & args]
  (let [config (process-config home-path dotfiles-path config-path)
        classified (group-by classifier config)]
    (print-chunk "Invalid:" (:invalid classified))
    (print-chunk "Correct:" (:done classified))
    (print-chunk "To Do:" (:todo classified))
    (if (seq (:invalid classified))
      (do
        (println "ERROR: Invalid state (see above). Cancelling install.")
        (System/exit 1))
      (install (:todo classified)))))

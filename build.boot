(set-env! :source-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.7.0"]
                  [ike/ike.cljj "0.2.2"]])

(deftask build
  "Builds the project."
  []
  (comp
    (aot :all true)
    (pom :project 'org.ajoberstar/clj-dotfiles
         :version "0.1.0-SNAPSHOT")
    (uber :exclude #{#"(?i)^META-INF\\[^\\]*\.(MF|SF|RSA|DSA)$"
                     #"^((?i)META-INF)\\.*pom\.(properties|xml)$"
                     #"(?i)^META-INF\\INDEX.LIST$"})
    (jar :main 'clj-dotfiles)))

(deftask release-local [] (comp (build) (install)))

(deftask release-snapshot []
  (comp
    (build)
    (push :ensure-clean true
          :ensure-snapshot true
          :repo "clojars")))

(deftask release-final []
  (comp
    (build)
    (push :ensure-clean true
          :ensure-release true
          :repo "clojars"
          :tag true)))

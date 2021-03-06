(def tk-version "0.3.0-SNAPSHOT")
(def ks-version "0.4.1")

(defproject puppetlabs/trapperkeeper-webserver-jetty7 "0.1.0-SNAPSHOT"
  :description "We are trapperkeeper.  We are one."
  ;; Abort when version ranges or version conflicts are detected in
  ;; dependencies. Also supports :warn to simply emit warnings.
  ;; requires lein 2.2.0+.
  :pedantic? :abort
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [puppetlabs/kitchensink ~ks-version]
                 [puppetlabs/trapperkeeper ~tk-version]

                 ;; Jetty Webserver
                 [org.eclipse.jetty/jetty-servlet "7.6.1.v20120215"]
                 [org.eclipse.jetty/jetty-server "7.6.1.v20120215"
                  :exclusions [org.eclipse.jetty.orbit/javax.servlet]]

                 [ring/ring-servlet "1.1.8"]]


  ;; By declaring a classifier here and a corresponding profile below we'll get an additional jar
  ;; during `lein jar` that has all the code in the test/ directory. Downstream projects can then
  ;; depend on this test jar using a :classifier in their :dependencies to reuse the test utility
  ;; code that we have.
  :classifiers [["test" :testutils]]

  :test-paths ["test/clj"]

  :profiles {:dev {:test-paths ["test-resources"]
                   :source-paths ["examples/ring_app/src"
                                  "examples/servlet_app/src/clj"]
                   :java-source-paths ["examples/servlet_app/src/java"]}

             :test {:dependencies [[clj-http "0.5.3"]
                                   [org.slf4j/slf4j-log4j12 "1.7.5" :exclusions [log4j]]
                                   [puppetlabs/kitchensink ~ks-version :classifier "test"]
                                   [puppetlabs/trapperkeeper ~tk-version :classifier "test"]]
                    :java-source-paths ["test/java"]}

             :testutils {:source-paths ^:replace ["test/clj"]
                         :java-source-paths ^:replace ["test/java"]}
             :uberjar {:aot [puppetlabs.trapperkeeper.main]
                       :classifiers ^:replace []}}

  :main puppetlabs.trapperkeeper.main
  )


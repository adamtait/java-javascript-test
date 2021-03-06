(defproject lacinia-api "0.0.1-SNAPSHOT"
  :description "Lacinia-based GraphQL API service"
  :url "http://github.com/adamtait/lacinia-api"

  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/core.async "0.4.500"]

                 ;; important libs
                 [com.stuartsierra/component "0.4.0"]
                 [com.walmartlabs/lacinia "0.36.0"]

                 ;; logging
                 [ch.qos.logback/logback-classic "1.2.3"
                  :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.26"]
                 [org.slf4j/jcl-over-slf4j "1.7.26"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]
                 ]


  :source-paths ["sources/app" "sources/base"]
  :resource-paths ["resources/schemas" "resources/javascript"]

  :profiles {
             :java
             {:java-source-paths ["sources/java"]
              :main sample2.Mustache
              :dependencies [;; https://mvnrepository.com/artifact/org.mozilla/rhino
                             [org.mozilla/rhino "1.7.12"]
                             [com.amazonaws/aws-lambda-java-core "1.2.0"]
                             [com.amazonaws/aws-lambda-java-events "2.2.6"]
                             [com.amazonaws/aws-lambda-java-log4j2 "1.0.0"]]
              }

             
             :dev
             {:source-paths ["sources/dev"]
              :resource-paths ["resources/configuration/dev" "resources/logging/dev"]
              :dependencies [[org.clojure/tools.namespace "0.3.1"]
                             [com.stuartsierra/component.repl "0.2.0"
                              :exclusions [org.clojure/clojure
                                           com.stuartsierra/component
                                           org.clojure/tools.namespace]]
                             [clj-http "3.10.0"]]}


             :pedestal
             {:source-paths ["sources/pedestal"]
              :dependencies [[io.pedestal/pedestal.service "0.5.7"
                              :exclusions  [org.clojure/tools.analyzer.jvm
                                            org.clojure/core.async]]
                             [io.pedestal/pedestal.jetty "0.5.7"
                              :exclusions [org.eclipse.jetty/jetty-util]]
                             [org.eclipse.jetty/jetty-util "9.4.20.v20190813"]
                             [io.pedestal/pedestal.service-tools "0.5.7"]
                             [io.pedestal/pedestal.log "0.5.7"]
                             
                             [com.walmartlabs/lacinia-pedestal "0.13.0"
                              :exclusions [io.pedestal/pedestal.service
                                           io.pedestal/pedestal.jetty
                                           com.walmartlabs/lacinia]]]}

             :pedestal-standalone
             {:source-paths ["sources/standalone/pedestal"]
              :resource-paths ["resources/configuration/pedestal"]}
             
             
             
             :lambda {}

             :lambda-standalone
             {:source-paths ["sources/standalone/lambda"]
              :resource-paths ["resources/configuration/lambda"]
              :dependencies [[com.amazonaws/aws-lambda-java-core "1.2.0"]
                             [com.amazonaws/aws-lambda-java-events "2.2.6"]
                             [com.amazonaws/aws-lambda-java-log4j2 "1.0.0"]]}

             

             :uberjar
             {:resource-paths ["resources/logging/prod"]
              :aot :all}})

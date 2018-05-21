(defproject simple-tokenize "0.0.1"
  :description "Word tokenization function with unit tests"
  :url "https://github.com/jtwool/"
    :license {:name "Mozilla Public License 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot hyena-tokenize.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

(ns hyena-tokenize.core
  (require [clojure.string :as str]
           [clojure.java.io :as io])
  (:gen-class))

(defn replace-many
  "Perform many replaces at once"
  [s rs]
  (reduce 
    (fn [s r]
      (str/replace s (r :match) (r :replace)))
    s rs))

(defn tokenize
  "Tokenize a sentence to words."
  [s]
  (str/split
    (replace-many
     (str/lower-case s)
    [{:match #"(\w+)(n't)\b" :replace "$1 nt"} ;;don't
     {:match #"\d" :replace "0"} ;;numbers
     {:match #"[0,]+000" :replace "0000"} ;;big numbers
     {:match #"0*\.0+" :replace "0.0"} ;;decimals
     {:match #"(https?://)?(www\.)?(\w+?\.)+\w{2,3}(/\S+)?"
      :replace"URL"} ;;urls
     {:match #"[\(\[](.*?)[\]\)]"  :replace " (PH) $1"} ;;parens
     {:match #"[\"'](.*?)[\"']"  :replace " QUOTE $1"} ;;quote
     {:match #"([\w\d]+)([?;:!.,]+)(\s|$)"
      :replace " $1 $2 "} ;;end puncts
     ]) #"\s+"))

(defn -main
  "Tokenize a file line by line."
  [& args]
  (let [fp (first args)]
    (with-open 
      [rdr (io/reader fp)]
      (doseq [x (line-seq rdr)] (println (str/join " " (tokenize x)))))))

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
    [{:match #"(\w+)(n't)\b" :replace "$1 nt"}
     {:match #"\d" :replace "0"}
     {:match #"[0,]+000" :replace "0000"}
     {:match #"0*\.0+" :replace "0.0"}
     {:match #"(https?://)?(www\.)?(\w+?\.)+\w{2,3}(/\S+)?" 
      :replace"URL"}
     {:match #"[\(\[](.*?)[\]\)]"  :replace " (PH) $1"}
     {:match #"[\"'](.*?)[\"']"  :replace " QUOTE $1"}
     {:match #"([\w\d]+)([?;:!.,]+)(\s|$)" :replace " $1 $2 "}])
   #"\s+")
)


(defn -main
  "Tokenize a file line by line."
  [& args]
  (let [fp (first args)]
    (with-open 
      [rdr (io/reader fp)]
      (doseq [x (line-seq rdr)] (println (str/join " " (tokenize x)))))))

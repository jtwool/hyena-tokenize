(ns hyena-tokenize.core
  (require [clojure.test :refer :all]
           [clojure.string :as str]
           [clojure.java.io :as io])
  (:gen-class))

  (defn tokenize
    "Tokenize a sentence to words."
    [s]
    (str/split
      (str/replace
      (str/replace
      (str/replace
      (str/replace
      (str/replace  
      (str/replace 
       (str/lower-case s)
      #"(\w+)(n't)\b" "$1 nt")
      #"\d" "0")
      #"[0,]+000" "0000")
      #"0*\.0+" "0.0")
      #"(https?://)?(www\.)?(\w+?\.)+\w{2,3}(/\S+)?" "URL")
      #"([\w\d]+)([?;:!.,]+)(\s|$)" " $1 $2 ") 
     #"\s+")
  )

(deftest tokenize-test

  (testing "Lowercase"
    (is (= ["lowercase" "sentence"] (tokenize "LowErcAse SenTenCe"))))
  (testing "Whitespace"
    (is (= ["the" "dog" "can" "walk"] (tokenize "the dog can walk")))
    (is (= ["the" "dog" "is" "good"] (tokenize "the  dog\tis\ngood"))))
  (testing "Conjunctions"
    (is (= ["i" "would" "nt" "could" "nt" "on" "a" "house"] (tokenize "i wouldn't couldn't on a house")) "Conjunction: n't"))
  (testing "Numbers"
    (is (= ["0" "00" "000" "0000" "0000" "0.0" "0.0" "0.0"]
           (tokenize "3 50 912 1298 515341031 100.32 32100.411 .932"))))
    (is (= ["0000" "0000"] (tokenize "10,000 537,231,131")))
  (testing "URLs"
    (is (= ["URL" "URL"]
           (tokenize "www.google.com www.docs.google.com")))
    (is (= ["URL" "URL"]
           (tokenize "http://www.google.com https://www.google.com")))
    (is (= ["URL" "URL"]
           (tokenize "http://go.espn.com/nba https://go.espn.com/nba/celtics"))))
  (testing "End punctuation"
    (is (= ["i" "love" "dogs" "!!"]  (tokenize "I love dogs!!"))))
  (testing "Sentences"
    (is (= ["this" "is" "several" "sentences" "." "i" "hope" "my" "tokenizer"
            "can" "handle" "0" "sentences" "at" "once" "!!" "can" "yours" "??"]
           (tokenize "This is several sentences. I hope my tokenizer can handle 3 sentences at once!! Can yours??"))))
)

(defn -main
  "Tokenize a file line by line."
  [& args]
  (with-open 
    [rdr (io/reader (first args))]
    (doseq [x (line-seq rdr)] (println (str/join " " (tokenize x))))))

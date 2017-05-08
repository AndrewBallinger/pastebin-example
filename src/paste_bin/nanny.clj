(ns paste-bin.nanny
  (:require
   [clojure.test :refer (is deftest)]
   [clojure.string :refer (join split-lines lower-case split)]))

(def various-naughty-bad-bad-matcher
  (re-pattern (str "\\("
                   (join
                    "|"
                    (split-lines
                     (slurp "https://www.cs.cmu.edu/~biglou/resources/bad-words.txt"))
                    )
                   "\\)")))

(defn replace-naughty [word]
  (let [target (lower-case word)]
    (cond
      (= "password" target) "perfectly legitimate and public information"
      (= "fuck" target) "frak"
      (= "damn" target) "drat"
      (= "shit" target) "poo"
      (re-matches various-naughty-bad-bad-matcher target) "!@#$%"
      :default word)))

(defn scrub-line [string]
  (join " "
        (map replace-naughty (split string #" "))))

(defn scrub [string]
  (join "\n" (map scrub-line (split-lines string))))

(ns paste-bin.nanny-test
  (:require [clojure.test :refer :all]
            [paste-bin.nanny :refer :all]))

(deftest test-matcher
  (is (re-matches various-naughty-bad-bad-matcher "buttpirate")))

(deftest check-replacement
  (is (= (scrub "fuck\nyou") "frak\nyou")))

(deftest check-extended
  (is (= (scrub "The prime minister is a buttpirate yo") "The prime minister is a !@#$% yo")))

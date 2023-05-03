(ns me.sonyahon.polyserv.crypto.interface-test
  (:require [clojure.test :as test :refer :all]
            [me.sonyahon.polyserv.crypto.interface.hashing :as hashing]))

(deftest should-encrypt-password
  (let [password "password"
        hashed (hashing/encrypt password)]
    (is (not= hashed password))
    (is (not (hashing/compare (str "incorrect" password) hashed)))
    (is (hashing/compare password hashed))))
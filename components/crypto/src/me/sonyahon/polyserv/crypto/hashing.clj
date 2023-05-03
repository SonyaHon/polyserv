(ns me.sonyahon.polyserv.crypto.hashing
  (:require [buddy.hashers :as hashers]))

(defn encrypt [password]
  (hashers/derive password))

(defn compare [password hash]
  (hashers/check password hash))

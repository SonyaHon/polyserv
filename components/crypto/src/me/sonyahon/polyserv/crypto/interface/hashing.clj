(ns me.sonyahon.polyserv.crypto.interface.hashing
  (:require [me.sonyahon.polyserv.crypto.hashing :as impl]))

(defn encrypt
  "Encrypts a string via bcrypt"
  [data]
  (impl/encrypt data))

(defn compare
  "Validates that passed data is equivalent of the hash"
  [data hash]
  (impl/compare data hash))

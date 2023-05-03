(ns me.sonyahon.polyserv.user.domain
  (:require [me.sonyahon.polyserv.crypto.interface.hashing :as hashing]
            [clj-time.core :as time])
  (:import (org.bson.types ObjectId)))

(defrecord User [id email password meta data])

(defn from-credentials [{email :email password :password}]
  (let [id (ObjectId.)
        encrypted-password (hashing/encrypt password)]
    (->User id email encrypted-password {:created-at (time/now)} {})))

(defn serialize [user]
  (-> user
      (assoc :id (str (:id user)))
      (dissoc :password)))
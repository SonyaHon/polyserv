(ns me.sonyahon.polyserv.user.interface.domain
  (:require [me.sonyahon.polyserv.user.domain :as domain]
            [clojure.spec.alpha :as s])
  (:use [me.sonyahon.polyserv.user.spec]))

(defn from-credentials
  "Creates a new user from credentials"
  [credentials]
  (domain/from-credentials credentials))

(s/fdef from-credentials
        :args (s/cat (s/keys :req-un [:user/email :user/password]))
        :ret :user/instance)

(defn serialize
  "Serializes user to JSON like map, removing sensitive data"
  [user]
  (domain/serialize user))

(s/fdef serialize
        :args (s/cat :user/instance)
        :ret map?)
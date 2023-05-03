(ns me.sonyahon.polyserv.user.spec
  (:require [clojure.spec.alpha :as s])
  (:import (me.sonyahon.polyserv.user.domain User)))

(s/def :user/email string?)
(s/def :user/password string?)

(s/def :user/instance #(instance? User %))

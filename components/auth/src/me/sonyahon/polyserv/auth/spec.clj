(ns me.sonyahon.polyserv.auth.spec
  (:require [clojure.spec.alpha :as s]))

(s/def :auth/audience string?)
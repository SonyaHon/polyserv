(ns me.sonyahon.polyserv.user.prelude
  (:require [me.sonyahon.polyserv.mongo.interface :as mongo]
            [monger.collection :as coll]))

(defn prelude []
  (mongo/run-op* coll/ensure-index "users" {:email 1} {:unique true}))

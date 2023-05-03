(ns me.sonyahon.polyserv.mongo.core
  (:require [monger.core :as monger])
  (:import (com.mongodb MongoOptions ServerAddress)))

(defonce connection (atom nil))
(defonce database (atom nil))

(defn disconnect! []
  (when (some? @connection)
    (monger/disconnect @connection))
  (reset! connection nil)
  (reset! database nil)
  :ok)

(defn connect! []
  (disconnect!)
  (let [^MongoOptions opts (monger/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        ^ServerAddress addr (monger/server-address "mongo" 27017)
        conn (monger/connect addr opts)
        db (monger/get-db conn "polyserv")]
    (reset! connection conn)
    (reset! database db))
  :ok)

(defmacro run-op* [fn args]
  `(~fn @database ~@args))

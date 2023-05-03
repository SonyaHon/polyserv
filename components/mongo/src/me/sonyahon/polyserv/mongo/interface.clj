(ns me.sonyahon.polyserv.mongo.interface
  (:require [me.sonyahon.polyserv.mongo.core :as impl]))

(defn connect!
  "Connects to mongodb. If there was a connection before
  it will be closed."
  []
  (impl/connect!))

(defn disconnect!
  "Disconnects from mongodb"
  []
  (impl/disconnect!))

(defmacro run-op* [fn & args]
  `(impl/run-op* ~fn ~args))

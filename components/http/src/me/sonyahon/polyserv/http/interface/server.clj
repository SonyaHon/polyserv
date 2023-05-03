(ns me.sonyahon.polyserv.http.interface.server
  (:require [me.sonyahon.polyserv.http.server :as server-impl]))

(defn stop-server!
  "Stops currently running server"
  []
  (server-impl/stop-server!))

(defn start-server!
  "Starts a http server with reitit routes.
  Currently running server (if any) will be stopped."
  [port routes]
  (#(server-impl/start-server! port routes)))

(defn json-resp
  ([status body] (server-impl/json-resp status body {}))
  ([status body opts] (server-impl/json-resp status body opts)))
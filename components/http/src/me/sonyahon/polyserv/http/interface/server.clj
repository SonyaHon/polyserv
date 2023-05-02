(ns me.sonyahon.polyserv.http.interface.server
  (:require [me.sonyahon.polyserv.http.server :as server-impl]))

(defn stop-server!
  "Stops currently running server"
  []
  (server-impl/stop-server!))

(defn start-server!
  "Starts a http server with handler.
  Currently running server (if any) will be stopped."
  [port handler]
  (#(server-impl/start-server! port handler)))


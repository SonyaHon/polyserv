(ns me.sonyahon.polyserv.user.core
  (:require [me.sonyahon.polyserv.http.interface.server :as server]
            [me.sonyahon.polyserv.user.domain :as domain]
            [me.sonyahon.polyserv.http.interface.http-status :as status]))

(defn self-handler [request]
  (let [user (:user request)]
    (server/json-resp
      status/ok
      {:data (domain/serialize user)})))
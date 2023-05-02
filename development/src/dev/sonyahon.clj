(ns dev.sonyahon
  (:require [me.sonyahon.polyserv.http.interface.server :as web-server]))

(defn demo-app [request]
  (prn "Params yay 2:" (:params request))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "{\"ok\": true}"})

(comment

  (web-server/start-server! 6969 #(demo-app %))

  69)


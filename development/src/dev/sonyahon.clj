(ns dev.sonyahon
  (:require [me.sonyahon.polyserv.http.interface.server :as web-server]
            [me.sonyahon.polyserv.mongo.interface :as mongo]
            [monger.collection :refer [insert-and-return]]))

(defn demo-app [request]
  (prn "Params yay 2:" (:params request))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "{\"ok\": true}"})

(comment

  (web-server/start-server! 6969 #(demo-app %))

  (mongo/connect!)

  (mongo/run-op* insert-and-return "demo" {:count 0 :data 10})

  69)


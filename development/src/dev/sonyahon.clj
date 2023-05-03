(ns dev.sonyahon
  (:require [me.sonyahon.polyserv.http.interface.server :as web-server]
            [me.sonyahon.polyserv.mongo.interface :as mongo]
            [me.sonyahon.polyserv.user.interface.prelude :as user-prelude]
            [me.sonyahon.polyserv.user.interface.repository :as user-repository]
            [me.sonyahon.polyserv.user.interface.domain :as user-domain]))

(defn demo-app [request]
  (prn "Params yay 2:" (:params request))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    "{\"ok\": true}"})

(comment

  (web-server/start-server! 6969 #(demo-app %))

  (mongo/connect!)
  (user-prelude/prelude)

  (def user (user-domain/from-credentials {:email    "volkov030@gmail.com"
                                           :password "password"}))

  (user-repository/save user)
  (user-repository/find-one {:email "volkov030@gmail.com"})
  (user-repository/save (user-domain/from-credentials {:email    "volkov030@gmail.com"
                                                       :password "password"}))

  (mongo/run-op* insert-and-return "demo" {:count 0 :data 10})

  69)


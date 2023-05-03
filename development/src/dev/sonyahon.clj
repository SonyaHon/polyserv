(ns dev.sonyahon
  (:require [me.sonyahon.polyserv.http.interface.server :as web-server]
            [me.sonyahon.polyserv.mongo.interface :as mongo]
            [me.sonyahon.polyserv.user.interface.prelude :as user-prelude]
            [me.sonyahon.polyserv.user.interface.repository :as user-repository]
            [me.sonyahon.polyserv.user.interface.domain :as user-domain]
            [me.sonyahon.polyserv.auth.interface.http :as auth-http]
            [me.sonyahon.polyserv.user.interface.http :as user-http]))


(comment

  (user-http/gen-routes "/api/users" {})

  (web-server/start-server! 6969
                            [[(auth-http/gen-routes "/api/auth")]
                             [(user-http/gen-routes "/api/users")]])

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


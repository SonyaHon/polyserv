(ns me.sonyahon.polyserv.user.http
  (:require [me.sonyahon.polyserv.auth.interface.core :as auth]
            [me.sonyahon.polyserv.user.core :as core]))

(defn gen-routes [prefix {include-admin :include-admin}]
  [[prefix
    ["/self" {:middleware [[auth/wrap-parse-jwt] [auth/wrap-fetch-user]]
              :get        #(core/self-handler %)}]]])
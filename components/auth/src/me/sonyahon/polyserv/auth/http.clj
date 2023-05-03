(ns me.sonyahon.polyserv.auth.http
  (:require [me.sonyahon.polyserv.auth.core :as core]))

(defn gen-routes [prefix]
  [[prefix
    ["/login" {:post #(core/login-handler %)}]
    ["/logout" {:middleware [[core/wrap-parse-jwt] [core/wrap-fetch-user]]
                :post       #(core/logout-handler %)}]
    ["/refresh" {:post #(core/refresh-handler %)}]]])


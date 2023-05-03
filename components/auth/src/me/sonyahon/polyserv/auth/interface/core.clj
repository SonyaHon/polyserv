(ns me.sonyahon.polyserv.auth.interface.core
  (:require [me.sonyahon.polyserv.auth.core :as core]))

(defn wrap-parse-jwt
  "Parses JWT from request headers, throwing Unauthorized if could not parse.
  Populates :jwt field, containing jwt data"
  [handler]
  (core/wrap-parse-jwt handler))

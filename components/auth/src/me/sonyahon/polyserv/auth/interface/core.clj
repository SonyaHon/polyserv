(ns me.sonyahon.polyserv.auth.interface.core
  (:require [me.sonyahon.polyserv.auth.core :as core]))

(defn wrap-parse-jwt
  "Parses JWT from request headers, throwing Unauthorized if could not parse.
  Populates :jwt field, containing jwt data"
  [handler]
  (core/wrap-parse-jwt handler))

(defn wrap-fetch-user
  "Populates :user field, containing user from parsed JWT.
  `wrap-parse-jwt` must be run before.
  If :jwt is not found or user itself is missing returns unauthorized."
  [handler]
  (core/wrap-fetch-user handler))
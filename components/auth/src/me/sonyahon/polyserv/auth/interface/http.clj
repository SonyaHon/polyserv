(ns me.sonyahon.polyserv.auth.interface.http
  (:require [me.sonyahon.polyserv.auth.http :as http]))

(defn gen-routes
  "Generates necessary auth routes under prefix
  Routes generated:
  - POST prefix/login
  - POST prefix/logout
  - POST prefix/refresh"
  [prefix]
  (http/gen-routes prefix))
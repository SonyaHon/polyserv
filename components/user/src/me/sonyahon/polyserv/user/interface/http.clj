(ns me.sonyahon.polyserv.user.interface.http
  (:require [me.sonyahon.polyserv.user.http :as http]))

(defn gen-routes
  ([prefix]
   (http/gen-routes prefix {}))
  ([prefix opts]
   (http/gen-routes prefix opts)))

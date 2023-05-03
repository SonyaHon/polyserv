(ns me.sonyahon.polyserv.user.interface.repository
  (:require [me.sonyahon.polyserv.user.repository :as repo]))

(defn save
  "Saves this user to the database. Creates new record if not presented."
  [user]
  (repo/save user))

(defn find-one
  "Fetches a user by filter. Yields nil if not presented.
  Filter is either {:id ObjectID} or {:email String}"
  [filter]
  (repo/find-one filter))

(ns me.sonyahon.polyserv.user.repository
  (:require
    [me.sonyahon.polyserv.mongo.interface :as mongo]
    [me.sonyahon.polyserv.user.domain :as domain]

    [clojure.walk]
    [monger.collection :as coll]
    [monger.joda-time]))

(defn into-domain [user-doc]
  (domain/->User (:_id user-doc)
                 (:email user-doc)
                 (:password user-doc)
                 (clojure.walk/keywordize-keys (:meta user-doc))
                 (clojure.walk/keywordize-keys (:data user-doc))))

(defn into-doc [user]
  (-> user
      (assoc :_id (:id user))
      (dissoc :id)))

(defn save [user]
  (let [user-doc
        (mongo/run-op* coll/upsert "users" {:_id (:id user)}
                       (into-doc user))]
    (prn user-doc)))

(defn find-one [filter]
  (let [filter (if (some? (:id filter))
                 {:_id (:id filter)}
                 {:email (:email filter)})
        docs (mongo/run-op* coll/find-maps "users" filter)]
    (if (empty? docs)
      nil
      (into-domain (first docs)))))

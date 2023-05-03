(ns me.sonyahon.polyserv.auth.repository
  (:require
    [monger.collection :as coll]
    [monger.joda-time]
    [clj-time.core :as time]
    [me.sonyahon.polyserv.mongo.interface :as mongo]))

(defn create-refresh-token [user-id]
  (let [token (str (random-uuid))]
    (mongo/run-op* coll/insert "refresh_tokens" {:token   token
                                                 :exp     (time/plus (time/now) (time/hours 24))
                                                 :user_id user-id})
    token))

(defn delete-user-tokens [user]
  (let [id (:id user)]
    (mongo/run-op* coll/remove "refresh_tokens" {:user_id id})))

(defn invalidate-token [refresh-token]
  (let [token (mongo/run-op* coll/find-maps "refresh_tokens" {:token refresh-token})]
    (when (not (empty? token))
      (mongo/run-op* coll/remove "refresh_tokens" {:token refresh-token}))
    (if (or (empty? token)
            (let [exp (:exp (first token))]
              (time/before? exp (time/now))))
      nil
      (:user_id (first token)))))

(comment

  (invalidate-token "65c53dfa-b069-4b80-99b9-34a9f4d643ca")

  69)

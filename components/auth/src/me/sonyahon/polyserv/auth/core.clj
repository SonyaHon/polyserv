(ns me.sonyahon.polyserv.auth.core
  (:require
    [buddy.sign.jwt :as jwt]
    [clj-time.core :as time]
    [clojure.string :as string]

    [me.sonyahon.polyserv.auth.repository :as repo]
    [me.sonyahon.polyserv.crypto.interface.hashing :as hashing]
    [me.sonyahon.polyserv.user.interface.domain :as user-domain]
    [me.sonyahon.polyserv.user.interface.repository :as user-repo]
    [me.sonyahon.polyserv.http.interface.server :as server]
    [me.sonyahon.polyserv.http.interface.http-status :as status])
  (:import (org.bson.types ObjectId)))


(def jwt-secret "secret")

(defn gen-jwt [user aud]
  (jwt/sign {:sub (str (:id user))
             :aud aud
             :iss "polyserv::auth_service"
             :exp (time/plus (time/now) (time/minutes 5))}
            jwt-secret))

(defn gen-rt [user]
  (repo/create-refresh-token (:id user)))

(defn wrap-parse-jwt [handler]
  (fn [request]
    (let [headers (:headers request)
          auth-header (or (get headers "authorization")
                          (get headers "Authorization"))]
      (if (nil? auth-header)
        (server/json-resp
          status/unauthorized
          {:error "Invalid JWT"})
        (let [[_ jwt] (string/split auth-header #" ")]
          (try
            (let [parsed (jwt/unsign jwt jwt-secret)]
              (handler (assoc request :jwt parsed)))
            (catch Exception e
              (let [data (ex-data e)]
                (if (= :validation (:type data))
                  (server/json-resp
                    status/unauthorized
                    {:error "Invalid JWT"})
                  (throw e))))))))))

(defn wrap-fetch-user [handler]
  (fn [request]
    (let [sub (get-in request [:jwt :sub])]
      (if (nil? sub)
        (server/json-resp
          status/unauthorized
          {:error "Invalid JWT"})
        (let [user (user-repo/find-one {:id (ObjectId. ^String sub)})]
          (if (nil? user)
            (server/json-resp
              status/unauthorized
              {:error "Invalid JWT"})
            (handler (assoc request :user user))))))))

(defn login-handler [request]
  (let [{email :email password :password audience :audience} (:params request)
        dummy-password (hashing/encrypt "dummy")]
    (let [user (user-repo/find-one {:email email})
          password (if (nil? user)
                     "invalid"
                     password)
          hashed-password (if (nil? user)
                            dummy-password
                            (:password user))]
      (if (hashing/compare password hashed-password)
        (let [access-token (gen-jwt user audience)
              refresh-token (gen-rt user)]
          (server/json-resp
            status/ok
            {:data     {:access_token  access-token
                        :refresh_token refresh-token}
             :included {:user (user-domain/serialize user)}}))
        (server/json-resp
          status/forbidden
          {})))))

(defn logout-handler [request]
  (let [user (:user request)]
    (repo/delete-user-tokens user)
    (server/json-resp
      status/no-content
      {})))

(defn refresh-handler [request]
  (let [headers (:headers request)
        audience (get-in request [:params :audience])
        auth-header (or (get headers "authorization")
                        (get headers "Authorization"))]
    (if (nil? auth-header)
      (server/json-resp
        status/unauthorized
        {:error "Invalid Refresh token"})
      (let [[_ rt] (string/split auth-header #" ")
            user-id (repo/invalidate-token rt)]
        (if (nil? user-id)
          (server/json-resp
            status/unauthorized
            {:error "Invalid Refresh token"})
          (let [user (user-repo/find-one {:id user-id})]
            (if (nil? user)
              (server/json-resp
                status/unauthorized
                {:error "Invalid Refresh token"})
              (let [access-token (gen-jwt user audience)
                    refresh-token (gen-rt user)]
                (server/json-resp
                  status/ok
                  {:data     {:access_token  access-token
                              :refresh_token refresh-token}
                   :included {:user (user-domain/serialize user)}})))))))))
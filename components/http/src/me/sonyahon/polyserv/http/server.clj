(ns me.sonyahon.polyserv.http.server
  (:require [me.sonyahon.polyserv.http.status :as http-status]
            [cheshire.core :as json]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [ring.middleware.keyword-params :as kw-params]
            [ring.middleware.multipart-params :as mp-params]
            [ring.middleware.cookies :as cookies]
            [reitit.ring :as reitit]))

(defonce server (atom nil))

(defn stop-server! []
  (when (some? @server)
    (.stop @server)
    (reset! server nil))
  nil)

(defn start-server! [port routes]
  (stop-server!)
  (println (str "Starting at: 0.0.0.0:" port))
  (reset! server (jetty/run-jetty (-> #((reitit/ring-handler
                                          (reitit/router
                                            routes)
                                          (reitit/create-default-handler))
                                        %)
                                      (kw-params/wrap-keyword-params)
                                      (mp-params/wrap-multipart-params)
                                      (params/wrap-params)
                                      (cookies/wrap-cookies))
                                  {:port  port
                                   :join? false}))
  nil)

(defmulti json-resp
          (fn [status _ _]
            (first (str status))))
(defmethod json-resp \2
  [status body opts]
  (let [headers (merge {"Content-Type" "application/json"} (:headers opts))]
    {:status  status
     :headers headers
     :body    (json/generate-string (merge {:ok true} body))}))

(defmethod json-resp \4
  [status body opts]
  (let [headers (merge {"Content-Type" "application/json"} (:headers opts))]
    {:status  status
     :headers headers
     :body    (json/generate-string (merge {:ok false} body))}))
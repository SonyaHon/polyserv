(ns me.sonyahon.polyserv.http.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as params]
            [ring.middleware.keyword-params :as kw-params]
            [ring.middleware.multipart-params :as mp-params]
            [ring.middleware.cookies :as cookies]))

(defonce server (atom nil))

(defn stop-server! []
  (when (some? @server)
    (.stop @server)
    (reset! server nil))
  nil)

(defn start-server! [port handler]
  (stop-server!)
  (println (str "Starting at: 0.0.0.0:" port))
  (reset! server (jetty/run-jetty (-> handler
                                      (kw-params/wrap-keyword-params)
                                      (mp-params/wrap-multipart-params)
                                      (params/wrap-params)
                                      (cookies/wrap-cookies))
                                  {:port  port
                                   :join? false}))
  nil)

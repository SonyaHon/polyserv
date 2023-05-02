(ns me.sonyahon.polyserv.http.specs
  (:require
    [me.sonyahon.polyserv.http.interface.server]
    [clojure.spec.alpha :as s]))

(s/fdef me.sonyahon.polyserv.http.interface.server/start-server!
        :args (s/cat :port int? :handler fn?)
        :ret nil?)
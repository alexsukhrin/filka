(ns alexsukhrin.infra.server
  (:require
   [org.httpkit.server :as http]
   [mount.core :as mount :refer [defstate]]
   [alexsukhrin.api.app :as fa]))

(defn get-web-port
  []
  (let
   [port
    (or (System/getenv "SERVER_PORT") "4000")]
    (Integer/parseInt port)))

(defn start-server
  []
  (when-let [server
             (http/run-server #'fa/app {:port (get-web-port)})]

    (println "Server has started!")
    server))

(defstate my-server
  :start (start-server)
  :stop  (my-server :timeout 100))

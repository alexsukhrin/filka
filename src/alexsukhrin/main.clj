(ns alexsukhrin.main
  (:require
   [mount.core :as mount]
   alexsukhrin.infra.server)
  (:gen-class))

(defn start
  []
  (mount/start))

(defn stop
  []
  (mount/stop))

(defn -main [& args]
  (start))

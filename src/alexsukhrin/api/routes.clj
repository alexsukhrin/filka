(ns alexsukhrin.api.routes
  (:require
   [compojure.core :refer [defroutes DELETE GET POST context]]
   [compojure.route :refer [not-found]]
   [alexsukhrin.api.handlers :as h]))

(defroutes
  app-routes
  (GET "/ping" _ {:status 200 :body "pong"})
  (POST "/filter" {:keys [body]} (h/new-filter body))
  (GET "/filter" [id] (h/get-filters-or-messages id))
  (DELETE "/filter" [id] (h/remove-filter id))
  (not-found "<h1>404 Error!</h1>"))

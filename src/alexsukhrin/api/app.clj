(ns alexsukhrin.api.app
  (:require
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [ring.logger :as logger]
   [alexsukhrin.api.routes :refer [app-routes]]))

(def app
  (-> #'app-routes
      (wrap-keyword-params)
      (wrap-params)
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (logger/wrap-with-logger)))

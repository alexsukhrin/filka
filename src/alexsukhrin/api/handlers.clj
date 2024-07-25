(ns alexsukhrin.api.handlers
  (:require
   [alexsukhrin.domains.messages :refer [get-messages, get-filters, start-consumer, add-filter, delete-filter]]))

(defn new-filter
  [{:keys [topic q]}]
  (let [id (add-filter topic q)]
    (start-consumer)
    {:status 200 :body {:id id}}))

(defn get-filters-or-messages
  [id]
  (let [body
        (if (nil? id)
          (get-filters)
          (get-messages (Integer/parseInt id)))]
    {:status 200
     :body body}))

(defn remove-filter
  [id]
  (delete-filter (Integer/parseInt id))
  {:status 200
   :body {:message "Filter deleted"}})

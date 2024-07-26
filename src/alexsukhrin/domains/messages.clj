(ns alexsukhrin.domains.messages
  (:require
   [clojure.string :as str]
   [alexsukhrin.domains.kafka.consumer :as kc]
   [alexsukhrin.domains.kafka.config :as kf]))

(def filtered-messages (atom {}))
(def filters (atom {}))
(def id-counter (atom 0))

(defn get-messages [id]
  (@filtered-messages id))

(defn process-message
  [id message]
  (let [filter
        (:query (@filters id))]

    (when (str/includes? (str/lower-case message) filter)
      (swap! filtered-messages update id conj message))))

(defn consume-messages
  [bootstrap-server]
  (let [consumer
        (kc/make-consumer bootstrap-server)

        topics
        (map :topic (vals @filters))]

    (.subscribe consumer topics)

    (future
      (while true
        (let [records (.poll consumer 100)]
          (doseq [record records]
            (doseq [[id filter] @filters]
              (when (= (:topic filter) (.topic record))
                (process-message id (.value record))))))))))

(defn start-consumer
  []
  (let
   [kafka-host
    (kf/get-kafka-host)]
    (consume-messages kafka-host)))

(defn add-filter
  [topic query]
  (let [id (swap! id-counter inc)]
    (swap! filters assoc id {:topic topic :query (str/lower-case query)})
    id))

(defn delete-filter [id]
  (swap! filters dissoc id)
  (swap! filtered-messages dissoc id))

(defn get-filters []
  @filters)

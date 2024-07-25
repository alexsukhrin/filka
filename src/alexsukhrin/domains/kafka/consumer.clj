(ns alexsukhrin.domains.kafka.consumer
  (:require
   [alexsukhrin.domains.kafka.config :as config])
  (:import
   [java.util Properties]
   [org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer]
   [org.apache.kafka.common.serialization StringDeserializer]))

(defn make-consumer
  [bootstrap-server]
  (let [props
        (doto (Properties.)
          (.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG bootstrap-server)
          (.put ConsumerConfig/GROUP_ID_CONFIG "filka-group")
          (.put ConsumerConfig/AUTO_OFFSET_RESET_CONFIG "earliest")
          (.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG (.getName StringDeserializer))
          (.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG (.getName StringDeserializer)))]
    (KafkaConsumer. props)))

(comment

  (let
   [kafka-host
    (config/get-kafka-host)

    consumer
    (make-consumer kafka-host)

    _ (.subscribe consumer ["books" "cars"])

    records
    (.poll consumer (Duration/ofMillis 100))]

    (doseq [record records]
      (prn record)))

  :end)

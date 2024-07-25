(ns alexsukhrin.domains.kafka.producer
  (:require
   [alexsukhrin.domains.kafka.config :as config])
  (:import
   [java.util Properties]
   [org.apache.kafka.clients.producer ProducerConfig KafkaProducer ProducerRecord]
   [org.apache.kafka.common.serialization StringSerializer]))

(defn make-producer
  [bootstrap-server]
  (let [props
        (doto (Properties.)
          (.put ProducerConfig/BOOTSTRAP_SERVERS_CONFIG bootstrap-server)
          (.put ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG (.getName StringSerializer))
          (.put ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG (.getName StringSerializer)))]
    (KafkaProducer. props)))

(comment

  (let
   [kafka-host
    (config/get-kafka-host)

    producer
    (make-producer kafka-host)

    topic
    "books"

    msg
    "python 3"]

    (.send producer (ProducerRecord. "books" "python")))

  :end)

(ns alexsukhrin.domains.kafka.config)

(defn get-kafka-host
  []
  (or (System/getenv "KAFKA_HOST")
      "localhost:9092"))

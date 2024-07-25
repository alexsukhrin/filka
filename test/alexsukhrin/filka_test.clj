(ns alexsukhrin.filka-test
  (:require [clojure.test :refer [deftest testing is]]
            [alexsukhrin.domains.messages :as msg]
            [alexsukhrin.domains.kafka.producer :refer [make-producer]]
            [alexsukhrin.domains.messages :refer [add-filter]])
  (:import (org.testcontainers.containers KafkaContainer)
           (org.apache.kafka.clients.producer ProducerRecord)
           (org.testcontainers.utility DockerImageName)))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest kafka-integration-test
  (testing "Fire up test containers Kafka and then send and consume message"
    (let [kafka-container
          (KafkaContainer. (DockerImageName/parse "confluentinc/cp-kafka:5.5.3"))

          _ (.start kafka-container)

          bootstrap-server
          (.getBootstrapServers kafka-container)

          test-producer
          (make-producer bootstrap-server)

          topic
          "example-consumer-topic"

          input-data
          "hello"

          sent-result
          (.get (.send test-producer (ProducerRecord. topic input-data)))

          query
          "ello"

          new-filter
          (add-filter topic query)]

      (future (msg/consume-messages bootstrap-server))
      (Thread/sleep 1000)
      (is (= topic (.topic sent-result)))
      (is (= 1 new-filter))
      (is (= input-data (first (@msg/filtered-messages new-filter)))))))

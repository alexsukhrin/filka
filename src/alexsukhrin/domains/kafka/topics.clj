(ns alexsukhrin.domains.kafka.topics
  (:import
   [org.apache.kafka.clients.admin AdminClientConfig NewTopic KafkaAdminClient]))

(defn create-topics!
  "Create the topic."
  [bootstrap-server topics ^Integer partitions ^Short replication]
  (let [adminClient
        (KafkaAdminClient/create {AdminClientConfig/BOOTSTRAP_SERVERS_CONFIG bootstrap-server})

        new-topics
        (map (fn [^String topic-name] (NewTopic. topic-name partitions replication)) topics)]

    (.createTopics adminClient new-topics)))

(comment

  (create-topics! kafka-host ["cars"] 1 1)

  :end)

#!/usr/bin/env bash

# arguments are Kafka home

# start Zookeeper
$kafka-home/bin/zookeeper-server-start.sh $kafka-home/config/zookeeper.properties

# start Kafka
$kafka-home/binkafka-server-start.sh $kafka-home/config/server.properties

# configure the topics
$kafka-home/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TEST
$kafka-home/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic LIMITED_OVERS
$kafka-home/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TWENTY_TWENTY

# load the topics
java -cp com.bfm.acs.crazycricket.SampleDataProducer --kafka-broker localhost:9092

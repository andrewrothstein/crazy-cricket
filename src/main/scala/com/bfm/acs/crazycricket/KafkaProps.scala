package com.bfm.acs.crazycricket

import java.util.Properties
/**
  * Created by Oscar on 5/19/16.
  */
case class KafkaProps(brokerAddr: String) {
  import KafkaConstants._
  private val props = new Properties()
  props.put("bootstrap.servers", brokerAddr)
  props.put("acks", "all")
  props.put("retries", 0.toString)
  props.put("batch.size", 16384.toString)
  props.put("linger.ms", 1.toString)
  props.put("buffer.memory", 33554432.toString)

  lazy val producerProps = {
    props.put("key.serializer", KEY_SERIALIZER)
    props.put("value.serializer", VAL_SERIALIZER)
    props
  }
  lazy val consumerProps = {
    props.put("group.id", "test")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", KEY_DESERIALIZER)
    props.put("value.deserializer", VAL_DESERIALIZER)
    props
  }
}

object KafkaConstants {
  val KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer"
  val VAL_SERIALIZER = "org.apache.kafka.common.serialization.ByteArraySerializer"
  val KEY_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer"
  val VAL_DESERIALIZER = "org.apache.kafka.common.serialization.ByteArrayDeserializer"
}

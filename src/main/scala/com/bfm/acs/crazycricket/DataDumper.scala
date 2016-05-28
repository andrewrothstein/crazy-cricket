package com.bfm.acs.crazycricket

import org.apache.kafka.clients.consumer.KafkaConsumer
import scopt.OptionParser
import collection.JavaConverters._

/**
  * Created by Oscar on 5/19/16.
  */
object DataDumper {

  val parser = DataDumperOptParser

  val appName = this.getClass.getName

  def run(parser: OptionParser[DataDumperOptParserConfig], args: Array[String]): Boolean = {
    parser.parse(args.toSeq,DataDumperOptParserConfig()) match {
      case Some(config) => {
        val props = KafkaProps(config.kafkaBroker).consumerProps
        val consumer = new KafkaConsumer[String,Array[Byte]](props)
        consumer.subscribe(config.topics.asJava)
        println(s"Reading up to ${config.messageLimit} messages from topics ${config.topics.reduce(_ + " " + _)}...")
        while(true){
          val records = consumer.poll(1000)
          println(s"Found ${records.asScala.size} records...")
          records.asScala.foreach{
            r => {
              println(
                s"""
                   |Offset: ${r.offset}
                   |key: ${r.key}
                   |val: ${CrazyCricketProtos.Game.parseFrom(r.value).toString}
              """.stripMargin)
            }
          }
        }
        consumer.close
        true
      }
      case None => false
    }
  }

  def main(args: Array[String]) = {
    if(run(parser.getParser(appName),args)) {
      println("All done...")
    } else {
      println("Something went wrong reading the topics...")
    }
  }
}

case class DataDumperOptParserConfig(kafkaBroker: String = "", topics: Seq[String] = Seq(),replayMode: Boolean = false, messageLimit: Int = 20)

object DataDumperOptParser {
  def getParser(appName: String) = {
    new OptionParser[DataDumperOptParserConfig](appName) {
      head(appName)
      opt[String]("kafka-broker") required() valueName "<host:port>" action { (x, config) =>
        config.copy(kafkaBroker = x)
      } text "The Kafka broker to connect to"
      opt[Unit]("replay-mode") action { (_, config) =>
        config.copy(replayMode = true)
      } text "Toggles whether to replay the earliest messages or listen only to new ones"
      opt[String]("topics") valueName "<topic_1,...,topic_N>" action { (x, config) =>
        config.copy(topics = x.split(','))
      } text "Comma seperated list of topics to dump"
      opt[Int]("message-limit") valueName "<int>" action { (x, config) =>
        config.copy(messageLimit = x)
      } text "Limit on the number of messages to be dumped per topic"
    }
  }
}
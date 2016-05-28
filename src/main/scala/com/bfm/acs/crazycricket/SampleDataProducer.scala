package com.bfm.acs.crazycricket

import java.io.File
import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}
import scopt.OptionParser
import com.bfm.acs.crazycricket.CrazyCricketProtos._
import scala.io.Source
import KafkaConstants._

/**
  * Created by Oscar on 5/17/16.
  */
object SampleDataProducer {

  val DEFAULT_TEST_DATA = "/sample-games"

  val TOPICS_TO_GAME =
    Map[String,Game.GameType](
      "TEST" -> Game.GameType.TEST,
      "LIMITED_OVERS" -> Game.GameType.LIMITED_OVERS,
      "TWENTY_TWENTY" -> Game.GameType.TWENTY_TWENTY,
      "OTHER" -> Game.GameType.OTHER
    )

  val parser = SampleDataProducerOptParser

  val appName = this.getClass.getName

  def parseGameDef(s: String): Option[(String,Game)] = s.split(',') match {
    case Array(winnerId, winnerCountry, loserId,loserCountry,gameType) => {
      val winner =
        Player.newBuilder
          .setUserId(winnerId)
          .setCountry(winnerCountry)
          .build
      val loser =
        Player.newBuilder
          .setUserId(loserId)
          .setCountry(loserCountry)
          .build

      Some(
        (gameType,
          Game.newBuilder
          .setWinner(winner)
          .setLoser(loser)
          .setType(TOPICS_TO_GAME(gameType))
          .build
          )
      )

    }
    case _  => None
  }

  def parseGameDefs(defs: Seq[String]) = {
    defs
      .toSeq
      .flatMap(parseGameDef)
      .groupBy(_._1)
  }

  def pushToKafka(topic: String,data: Seq[Game],producer: KafkaProducer[String,Array[Byte]]) = {
     data
      .zipWithIndex
      .foreach{
        case (m,i) => {
          producer.send(new ProducerRecord[String,Array[Byte]](topic,i.toString,m.toByteArray))
        }
      }
  }

  def run(parser: OptionParser[SampleDataProducerOptParserConfig], args: Array[String]) = {
    parser.parse(args.toSeq,SampleDataProducerOptParserConfig()) match {
      case Some(config) => {
        // Read the sample data and convert to Protobuf
        val sampleData = {
          if(config.customSampleData == null) {
            println(s"Reading sample data from ${getClass.getResource(DEFAULT_TEST_DATA)}")
            Source
              .fromInputStream(getClass.getResourceAsStream(DEFAULT_TEST_DATA))
              .getLines
          } else {
            println(s"Reading sample data from ${config.customSampleData}")
            Source
              .fromFile(config.customSampleData)
              .getLines
          }
        }
        val messages = parseGameDefs(sampleData.toSeq)
        println(s"Read in ${messages.size} sample games")

        // Setup Kafka
        val props = KafkaProps(config.kafkaBroker).producerProps
        val producer = new KafkaProducer[String,Array[Byte]](props)
        messages.foreach{
          case (topic,games) => pushToKafka(topic,games.map(_._2),producer)
        }
        producer.close()

        true
      }
      case None => false
    }
  }

  def main(args: Array[String]) = {
    if(run(parser.getParser(appName),args)) {
      println("Successfully loaded sample data into Kafka")
    } else {
      println("Something went wrong...")
    }
  }

}

case class SampleDataProducerOptParserConfig(kafkaBroker: String = "", customSampleData: File = null)

object SampleDataProducerOptParser {
  def getParser(appName: String) = {
    new OptionParser[SampleDataProducerOptParserConfig](appName) {
      head(appName)
      opt[String]("kafka-broker") required() valueName "<host:port>" action { (x, config) =>
        config.copy(kafkaBroker = x)
      } text "The Kafka broker to connect to"
      opt[String]("custom-sample-games") valueName "<path to custom data file>" action { (x, config) =>
        config.copy(customSampleData = new File(x))
      } text "A custom data file to load into the Kafka broker"
    }
  }
}



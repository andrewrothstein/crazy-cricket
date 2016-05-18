# Crazy Cricket
Welcome to crazy cricket, the greatest Cricket Video game on earth! You have been recruited to help our data team make the game more engage for users, driving growth in the user base!

## The Problem
So far uptake in the game has been great, users are really enjoying playing each other. However, users would like to know how they stack up against other users. We'd also like to implement a "national leaderboard", so users can see how their country compares to the others. This last feature was especially requested by users in India who want to make sure they are beating the English!

## The Solution
There are two requirements in the implementation:  
1. The solution must read Thrift messages off of a Kafka instance, and store the results in a persistent data store  
2. The end results must be served via HTTP get requests, and return results as JSON  
There are **no** other requirements! You can use whatever technology/language you like! At a high level, you will need three pieces:  
1. A processor for reading the Thrift messages off the Kafka queue and persist them  
2. A datastore of some kind that will hold the the data you have read  
3. A server that will respond to REST requests as follows:  
  * Current leaderboad: GET localhost:8080/crazycricket/api/leaderboard
  * Date range leaderboard: GET localhost:8080/crazycricket/api/leaderboard?start=yyyyMMdd&end=yyyyMMdd
  * Current country leaderboard: GET localhost:8080/crazycricket/api/national_leaderboard
  * Date range country leaderboard: GET localhost:8080/crazycricket/api/national_leaderboard?start=yyyyMMdd&end=yyyyMMdd

My shell script, bin/setup.sh, will configure a local Kafka cluster with some preloaded messages that you can use to test your query. Obviously your submissions willm be subject to some more rigorous testing!

## Details
The team responsible for the app itself have been kind enough to write the game resutls into a Kafka queue as Thrift messages. Check out the documentation if you're not familiar with [Kafka](http://kafka.apache.org/documentation.html), or [Thrift](https://thrift.apache.org/). You don't need to know much about Kafka, other than figuring out how to read a message off a topic queue. Similarly for Thrift, all you need to do is figure out how to deserialize one of the messages. The schema for these messages are included in the code you download!

## Grading Rubric
1. Unit tests: we expect to see thoughtful unit tests
2. Coding style: our team is pretty into code being clear and concise!
3. Design decisions: this essentially a design test, in the sense that it is really asking you to design a system for capturing and presenting data via a pre-defined interface. We are most interested not in which technologies you chose, but in **why** you chose them, and if the way that you used them demonstrated a clear understanding of that technology.



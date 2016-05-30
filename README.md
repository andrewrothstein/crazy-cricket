# Crazy Cricket
Welcome to crazy cricket, the greatest Cricket Video game on earth! You have been recruited to help our Anaytics Team team make the game more engaging for users, driving growth in the user base! In conjuction with the product manager for the game we have decided that an REST API that allows us to publish league tables will be the catalyst for an explosion of interest in the game!

## High Level
The goal here is to create a REST API that serves aggregated metrics created from a data source. In this case the data source will be a Kafka instance where the topics are protocol buffer objects that represent game results. Your task is to provide a REST API that returns correct aggregated tables of the data that comes of the Kafka topics. If you don't know Kafka, or protocol buffers, that shouldn't be a problem as you really don't need to know much about either to execute the project, though it will necessitate a few additional hours of reading to get familiar with those tools.

## Setup and submission requirements  
1. Linux (or Windows with Cygwin): you will need to be able to execute .sh scripts to run Kafka and the project locally. I recommend doing this on a VM using Virtual Box, though as long as you can run Kafka you are all set!
2. a distribution of Kafka: download the distribution [here](http://kafka.apache.org/downloads.html), and unpack it
3. sbt, or Simple Build Tool: in order to run the tests and get access to the Java protocol buffer defintions, you need to download and compile the project. Typesafe provide binary distributions of sbt [here](http://www.scala-sbt.org/0.13/docs/Setup.html), and it is very easy to install.
4. Clone this repo: `git clone... <your dir>; cd <your dir>; sbt`, which should clone the project into `<your dir>`, and fire up sbt, which will compile and build the project.
5. Create a project of your own with a solution: there is only one requirement, that the project contians a directory called bin, and a shell script `bin/run.sh <kafka host:port>` which starts your REST API and any other components (databases, Kafka consumers, etc), and connects to the Kafka broker specified as an argument.

## Task
There are two requirements in the implementation:  
   1. The solution must read Protobuf messages off of a Kafka instance, and store the results in a persistent data store.  
   2. The end results must be served via HTTP get requests, and return results as JSON. The format of the required JSON is specified below.  

There are **no** other requirements! You can use whatever technology/language you like! At a high level, you will need three pieces:  
   1. A processor for reading the Protobuf messages off the Kafka queue and persisting them in some form, which is most certainly an important design decision  
   2. A datastore of some kind that will hold the the data you have read  
   3. A server that will respond to REST requests as follows:  
    * Current leaderboad:  
    `GET <server address>/api/leaderboard`  
    * Date range leaderboard:  
    `GET <server address>/api/leaderboard?start=yyyyMMdd&end=yyyyMMdd`  
    * Current country leaderboard:  
    `GET <server address>/api/national_leaderboard`  
    * Date range country leaderboard:  
    `GET <server address>/api/national_leaderboard?start=yyyyMMdd&end=yyyyMMdd`  

Also JSON should be formatted as `{[name_1,value_1],...,[name_k,value_k]}`

My shell script, `bin/setup.sh <kafka home>`, will configure a local Kafka cluster with some preloaded messages that you can use to test your code, provided have the Kafka distribution properly unpacked in whatever directory you pass in as an argument.

## Grading Rubric
First and foremost, this is a time consuming test, and successfully executing it will put a candidate in a superb position to land a job, with the important caveat that the in person interview must convince us the candidate did the work independently (it's okay to use Google, in fact that's part of the test!). More specifically we will be looking at:
1. Unit tests: we would like to see unit tests  
2. Coding style: clear and concise code is always preferred  
3. Design decisions: this essentially a design test, in the sense that it is really asking you to design a system for   capturing and presenting data via a specified interface. We are most interested not in which technologies you chose, but in **why** you chose them, and if the way that you used them demonstrated a clear understanding of that technology.



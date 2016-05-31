# Background
The \<team\> is building out our engineering presence in Gurgoan, and we are seeking exceptional candidates to fill several rolls on our team. We believe that the successful candidate will have a high degree of technical aptitude, a *creative* problem solver, and most of all engaged. We have created this test in order to let candidate show us how excited they are about the kinds of technical challenges we face, and also to give them the opporunity to show their technical skill and creative problem solving.

A few things to bear in mind if you take on this test:
   1. It is intentionally a lot of work, and we expect that anyone who completes has put themselves in an excellent position to recieve an offer, provided we are convinced they have done the work. The in person technical interview will mainly explore questions about design and algorithms and come out of your solution.   
   2. We absolutely have *no expectation* that you are familiar with all of the tools and technologies used here (Java, Kafka, Protocol Buffers, and then whatever you choose to use in your solution). Indeed part of the test is to see how candidates handle an unfamiliar problem with an unfamiliar toolchain. Expect to use Google a lot, you **will not** find the answers to these problems in textbooks, and it will certainly require a combination of technical know-how, reading/**understanding** documentation,  creativitiy, and determination to complete. That's exactly the point, those are the attributes we are looking for in a successful candidate.  
   3. We are very much interested in the decisions *you* make. We are no interested in telling people what to do, we want to hire people that can synthesize business problems into slick technical solutions that create business value without the need to micro-manage and dictate projects. We want to know the kind of decisions you make, and the kind of work you do, when given an open ended problem with intentionally few requirements.
   4. The few requirements, read Protocol Buffers from Kafka and server data over HTTP as JSON via a REST API, are hard requirements. Indeed, doing those things successfully exactly as specified are the only requirements.

Enough preamble, let's jump into the detail...

# Crazy Cricket
Welcome to crazy cricket, the greatest Cricket Video game on earth! You have been recruited to help our Analytics Team team make the game more engaging for users, driving growth in the user base! In conjuction with the product manager for the game we have decided that an REST API that allows us to publish league tables will be the catalyst for an explosion of interest in the game!

## High Level
The goal here is to create a REST API that serves aggregated metrics created from a data source. In this case the data source will be a Kafka instance where the topics are protocol buffer objects that represent game results. Your task is to provide a REST API that returns correct aggregated tables of the data that comes off of the Kafka topics. If you don't know Kafka, or protocol buffers, that shouldn't be a problem as you really don't need to know much about either to execute the project, though it will necessitate a few additional hours of reading to get familiar with those tools.

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
   1. A processor for reading the Protobuf messages off of the Kafka topics and persisting them in some form, which is itself an interesting design decision you will have to make  
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

My shell script, `bin/setup.sh <kafka home>`, will configure a local Kafka cluster with some preloaded messages that you can use to test your code, provided you have the Kafka distribution properly unpacked in whatever directory you pass in as an argument.

## Grading Rubric
First and foremost, this is a design test. Given a set of constraints on input and output we are interested in the details of your implementation when you make all the decisions yourself. More specifically we will be looking at:  
   1. Unit tests: we would like to see unit tests  
   2. Coding style: clear and concise code is always preferred  
   3. Design decisions: this essentially a design test, in the sense that it is really asking you to design a system for   capturing and presenting data via a specified interface. We are most interested not in which technologies you chose, but in **why** you chose them, and if the way that you used them demonstrated a clear understanding of that technology.

## Final Words
Good luck, and please feel free to post questions in the "Issues" section!

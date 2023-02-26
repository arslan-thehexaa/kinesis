# AWS Kinesis with LocalStack

* This repo contains sample code for kinesis producer and consumer with localstack.

* There is a worker thread which consumes kinesis events whenever data is pushed to kinesis stream from last checkpoint.

* This code is written in Java 11 and Spring Boot framework.

# TODO before running the code
* Make sure docker & docker-compose is up & running on your system.
* run docker-compose.yml file in the repo which will start a localstack container. Use below command for this purpose:
  * docker-compose up -d
* create a sample stream in localstack using below command:
  * aws kinesis create-stream <stream-name> --shard-count <shard-count> --endpoint-url <your-localstack-container-url>
    * shard-count: for testing purpose 1 would suffice
    * endpoint-url: usually it is http://localhost:4566, can be different depending upon your localstack container config
* update application.yml file and add aws credentials, any dummy credentials will work with localstack but make sure they match AWS configuration defined for localstack.
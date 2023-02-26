# AWS Kinesis with LocalStack

This repo contains sample code for kinesis producer and consumer with localstack.

There is a worker thread which consumes kinesis events whenever data is pushed to kinesis stream from last checkpoint.

This code is written in Java 11 and Spring Boot framework.

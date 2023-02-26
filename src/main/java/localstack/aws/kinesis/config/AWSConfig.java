package localstack.aws.kinesis.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AWSConfig {

  @Value("${cloud.aws.region}")
  private Regions region;
  @Value("${cloud.aws.credentials.access-key}")
  private String accessKeyId;
  @Value("${cloud.aws.credentials.secret-key}")
  private String secretAccessKey;
  @Value("${cloud.aws.endpoint-url}")
  private String endpointUrl;

  @Bean
  public AmazonKinesis buildAmazonKinesis() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
    return AmazonKinesisClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withEndpointConfiguration(new EndpointConfiguration(endpointUrl, region.getName()))
        .build();
  }

  @Bean
  public AmazonDynamoDB amazonDynamoDBClient() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withEndpointConfiguration(new EndpointConfiguration(endpointUrl, region.getName()))
        .build();
  }

  @Bean
  public AmazonCloudWatch amazonCloudWatch() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
    return AmazonCloudWatchClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withEndpointConfiguration(new EndpointConfiguration(endpointUrl, region.getName()))
        .build();
  }
}

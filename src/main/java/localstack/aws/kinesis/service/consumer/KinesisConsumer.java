package localstack.aws.kinesis.service.consumer;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import java.util.concurrent.CompletableFuture;
import localstack.aws.kinesis.config.AWSConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KinesisConsumer implements ApplicationRunner {

  private final AmazonCloudWatch amazonCloudWatch;
  private final AWSConfig config;
  @Value("${cloud.aws.kinesis.stream-name}")
  private String kinesisStreamName;

  @SneakyThrows
  @Override
  public void run(ApplicationArguments args) {

    KinesisClientLibConfiguration kinesisConsumerConfiguration = new KinesisClientLibConfiguration(
        "Kinesis", kinesisStreamName,
        new AWSStaticCredentialsProvider(new BasicAWSCredentials(config.getAccessKeyId(),
            config.getSecretAccessKey())),
        "WORKER_ID")
        .withRegionName(config.getRegion().getName())
        .withKinesisEndpoint(config.getEndpointUrl())
        .withDynamoDBEndpoint(config.getEndpointUrl())
        .withCloudWatchClientConfig(new ClientConfiguration());

    final Worker worker = new Worker.Builder()
        .recordProcessorFactory(new KinesisRecordProcessorFactory())
        .config(kinesisConsumerConfiguration)
        .cloudWatchClient(amazonCloudWatch)
        .build();
    CompletableFuture.runAsync(worker);
  }
}

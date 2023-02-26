package localstack.aws.kinesis.service.consumer;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import java.util.concurrent.CompletableFuture;
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

  @Value("${cloud.aws.credentials.access-key}")
  private String accessKeyId;

  @Value("${cloud.aws.credentials.secret-key}")
  private String secretAccessKey;

  @Value("${cloud.aws.endpoint-url}")
  private String endpointUrl;

  @Value("${cloud.aws.kinesis.stream-name}")
  private String kinesisStreamName;

  @SneakyThrows
  @Override
  public void run(ApplicationArguments args) {

    KinesisClientLibConfiguration kinesisConsumerConfiguration = new KinesisClientLibConfiguration(
        "Kinesis", kinesisStreamName,
        new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)),
        "WORKER_ID")
        .withRegionName(Regions.EU_CENTRAL_1.getName())
        .withKinesisEndpoint(endpointUrl)
        .withDynamoDBEndpoint(endpointUrl)
        .withCloudWatchClientConfig(new ClientConfiguration());

    final Worker worker = new Worker.Builder()
        .recordProcessorFactory(new KinesisRecordProcessorFactory())
        .config(kinesisConsumerConfiguration)
        .cloudWatchClient(amazonCloudWatch)
        .build();
    CompletableFuture.runAsync(worker);
  }
}

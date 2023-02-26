package localstack.aws.kinesis.service.publisher;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.ByteBuffer;
import java.util.UUID;
import localstack.aws.kinesis.dto.KinesisPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class Publisher {
  private final ObjectMapper objectMapper;
  private final AmazonKinesis amazonKinesis;

  @Value("${cloud.aws.kinesis.stream-name}")
  private String kinesisStreamName;

  public PutRecordResult sendToKinesis(KinesisPayload payload) {

    PutRecordResult putRecordResult;
    try {
      PutRecordRequest request = new PutRecordRequest();
      request.setData(ByteBuffer.wrap(objectMapper.writeValueAsString(payload).getBytes()));
      request.setPartitionKey(UUID.randomUUID().toString());
      request.setStreamName(kinesisStreamName);
      putRecordResult = amazonKinesis.putRecord(request);
      log.info("Data published to kinesis: {}", request);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    return putRecordResult;
  }
}

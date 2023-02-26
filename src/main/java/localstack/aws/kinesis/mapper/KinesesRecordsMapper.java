package localstack.aws.kinesis.mapper;

import com.amazonaws.services.kinesis.model.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import localstack.aws.kinesis.dto.KinesisPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class KinesesRecordsMapper {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public KinesisPayload toKinesisPayload(Record kinesisRecord) {
    try {
      return objectMapper.readValue(kinesisRecord.getData().array(), KinesisPayload.class);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

}

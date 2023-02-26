package localstack.aws.kinesis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KinesisPayload {
  private int id;
  private String message;
}

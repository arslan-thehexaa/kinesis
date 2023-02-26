package localstack.aws.kinesis.controller;

import localstack.aws.kinesis.dto.KinesisPayload;
import localstack.aws.kinesis.service.publisher.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class ProducerController {

  @Autowired
  private Publisher publisher;

  @PostMapping("sendDataToKinesis")
  public ResponseEntity<?> sendDataToKinesis(@RequestBody KinesisPayload kinesisPayload) {
    return ResponseEntity.ok(publisher.sendToKinesis(kinesisPayload).getSequenceNumber());
  }

}

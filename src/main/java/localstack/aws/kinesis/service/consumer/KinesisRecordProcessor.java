package localstack.aws.kinesis.service.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import localstack.aws.kinesis.mapper.KinesesRecordsMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class KinesisRecordProcessor implements IRecordProcessor {

  @Override
  public void initialize(InitializationInput initializationInput) {
    log.info("Initializing the Kinesis Record Consumer for Shard: {}......",
        initializationInput.getShardId());
  }

  @Override
  public void processRecords(ProcessRecordsInput processRecordsInput) {
    log.info("Processing Kinesis Records......");
    processRecordsInput.getRecords().stream().map(KinesesRecordsMapper::toKinesisPayload)
        .forEach(log::info);
  }

  @Override
  public void shutdown(ShutdownInput shutdownInput) {
    log.info("Shutting Down Kinesis Consumer for Reason: {}......",
        shutdownInput.getShutdownReason());
  }
}

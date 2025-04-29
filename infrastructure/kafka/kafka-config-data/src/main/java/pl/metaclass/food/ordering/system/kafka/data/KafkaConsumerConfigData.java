package pl.metaclass.food.ordering.system.kafka.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
class KafkaConsumerConfigData {
	private String keyDeserializer;
	private String valueDeserializer;
	private String autoOffsetReset;
	private String specificAvroReaderKey;
	private String specificAvroReaderValue;
	private Boolean batchListener;
	private Boolean autoStartup;
	private Integer concurrencyLevel;
	private Integer sessionTimeoutMs;
	private Integer heartbeatIntervalMs;
	private Integer maxPollIntervalMs;
	private Long pollTimeoutMs;
	private Integer maxPollRecords;
	private Integer maxPartitionFetchBytesDefault;
	private Integer maxPartitionFetchBytesBoostFactor;


}

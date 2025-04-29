package pl.metaclass.food.ordering.system.kafka.producer.service.impl;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import pl.metaclass.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import pl.metaclass.food.ordering.system.kafka.producer.service.KafkaProducer;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

	private final KafkaTemplate<K, V> kafkaTemplate;

	public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void send(String topicName, K key, V message, CompletableFuture<SendResult<K, V>> callback) {
		log.info("Sending data {} to topic {}", message, topicName);
		try {
			CompletableFuture<SendResult<K, V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
			kafkaResultFuture.thenAccept(callback::complete);
		} catch (KafkaException e) {
			log.error("Kafka exception while sending data {} to topic {}", message, topicName, e);
			throw new KafkaProducerException("Error on kafka producer while sending data to topic " + topicName +
					" key " + key + " and message " + message);
		}
	}

	@PreDestroy
	public void close() {
		if (kafkaTemplate != null) {
			log.info("Closing kafka producer");
			kafkaTemplate.destroy();
		}
	}
}

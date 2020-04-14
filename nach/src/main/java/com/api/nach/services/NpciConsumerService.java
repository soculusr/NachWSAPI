package com.api.nach.services;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

public class NpciConsumerService {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NpciConsumerService.class);
	
	
	@KafkaListener(topics = "kafka_demo", groupId="nach")
	public void consume(String message) {
		
		logger.info("Consumed message :" + message);
	}
	

}

package com.api.nach.services;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


public class KafkaConsumerService {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaConsumerService.class);
	
	
	@KafkaListener(topics = "kafka_demo", groupId="nach")
	public void consume(String message) {
		
		logger.info("Consumed message :" + message);
	}
	

}

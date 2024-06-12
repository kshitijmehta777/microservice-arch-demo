package in.kshitij.users_command_service.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageProducer {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	public <T> void sendMessage(String topic, T messageObject) throws JsonProcessingException {
		kafkaTemplate.send(topic, objectMapper.writeValueAsString(messageObject));
	}
	public <T> void sendMessage(String topic, String message) throws JsonProcessingException {
		kafkaTemplate.send(topic, message);
	}
}

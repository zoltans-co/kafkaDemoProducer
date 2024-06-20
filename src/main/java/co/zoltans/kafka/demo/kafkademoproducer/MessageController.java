package co.zoltans.kafka.demo.kafkademo;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "api/v1/messages")
@Tag(name = "Messages", description = "The messages API")
public class MessageController {

	private KafkaTemplate<String, KafkaMessage> kafkaTemplate;
	
	public MessageController(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	@PostMapping
	public void publishMessage(@RequestBody KafkaMessage request) {
		KafkaMessage kafkaMessage = new KafkaMessage(request.message(), LocalDateTime.now());
		kafkaTemplate.send("kafka-demo", kafkaMessage);
	}

}

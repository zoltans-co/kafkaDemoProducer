package co.zoltans.kafka.demo.kafkademoproducer;

import co.zoltans.kafka.demo.kafkademoproducer.message.PublicationMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "api/v1/publications")
@Tag(name = "Publications", description = "The publications API")
public class MessageController {
	
	@Value("${topic.name.publications}")
	private String topicNamePublications;
	
	private final KafkaTemplate<String, PublicationMessage> kafkaTemplate;
	
	public MessageController(KafkaTemplate<String, PublicationMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	@PostMapping
	public void publishMessage(@RequestBody PublicationMessage request) {
		PublicationMessage kafkaMessage = new PublicationMessage(
				request.title(), request.author(), request.content(), request.category(), LocalDateTime.now());
		kafkaTemplate.send(topicNamePublications, kafkaMessage);
	}

}

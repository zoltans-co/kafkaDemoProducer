package co.zoltans.kafka.demo.kafkademoproducer;

import co.zoltans.kafka.demo.kafkademoproducer.message.PublicationMessage;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Random;

@OpenAPIDefinition(
		info =
		@io.swagger.v3.oas.annotations.info.Info(
				title = "Introduction to Kafka demo API",
				version = "1.0",
				description = "Introduction to Kafka demo API Documentation"),
		servers =
		@io.swagger.v3.oas.annotations.servers.Server(description = "Introduction to Kafka Demo API", url = "/"))
@SpringBootApplication
public class KafkaDemoProducerApplication {
	
	@Value("${topic.name.publications}")
	private String topicNamePublications;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoProducerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, PublicationMessage> kafkaTemplate) {
		return args -> {
			for (int i = 1; i < 5; i++) {
				kafkaTemplate.send(topicNamePublications,
						new PublicationMessage(
								"Interesting publication " + i,
								"automatic author",
								generateRandomSentence(),
								"investments",
								LocalDateTime.now()));
			}
		};
	}
	
	private static int randomLength() {
		Random random = new Random();
		return 1 + random.nextInt(10);  // Returns length between 1 and 10
	}
	
	private static String generateRandomSentence() {
		StringBuilder sentence = new StringBuilder();
		int wordsInSentence = 5 + new Random().nextInt(5); // Random number of words in each sentence (between 5 and 10)
		
		for(int i = 0; i < wordsInSentence; i++) {
			sentence.append(i > 0 ? " " : "");  // Space between words, but not before the first one
			String word = generateRandomWords(randomLength());
			
			// Capitalize the first word of the sentence
			if(i == 0) {
				word = word.substring(0, 1).toUpperCase() + word.substring(1);
			}
			
			sentence.append(word);
		}
		sentence.append(".");  // End the sentence with a dot
		return sentence.toString();
	}
	
	private static String generateRandomWords(int length) {
		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < length; i++) {
			bld.append((char) (Math.random() * 26 + 'a'));
		}
		return bld.toString();
	}
	
}

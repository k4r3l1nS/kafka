package io.dtechs.producer;

import io.dtechs.producer.service.MessageGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerApplicationTests {

	@Autowired
	private MessageGeneratorService messageGeneratorService;

	@Test
	void contextLoads() {
	}

	@Test
	public void sendMessages() {
		messageGeneratorService.sendMessages();
	}
}

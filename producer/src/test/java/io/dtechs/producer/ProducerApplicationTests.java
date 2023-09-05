package io.dtechs.producer;

import io.dtechs.producer.service.MyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerApplicationTests {

	@Autowired
	private MyService myService;

	@Test
	void contextLoads() {
	}

	@Test
	public void sendMessages() {
		myService.send10kMessages();
	}
}

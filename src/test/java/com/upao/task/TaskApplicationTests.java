package com.upao.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void testAddTaskAndRetrieveTasks() {

		ObjectMapper mapper = new ObjectMapper();

		Task task1 = new Task("Task 1");
		Task task2 = new Task("Task 2");

		restTemplate.postForEntity("http://localhost:" + port + "/api/tasks", task1, Void.class);
		restTemplate.postForEntity("http://localhost:" + port + "/api/tasks", task2, Void.class);

		JsonNode tasknode = restTemplate.getForObject("http://localhost:" + port + "/api/tasks", JsonNode.class);

		List<Task> tasks = mapper.convertValue(
				tasknode,
				new TypeReference<List<Task>>() {
				});
		assertEquals(2, tasks.size());
		assertEquals("Task 1", tasks.get(0).getName());
		assertEquals("Task 2", tasks.get(1).getName());

	}
}

package com.pinewise.nbdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
//import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
//import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
//import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class NbdemoApplication {

//	@Autowired
//	private ZookeeperServiceRegistry serviceRegistry;
//
//	public void registerThings() {
//
//		Map<String, String> metadata = new HashMap<String, String>();
//		metadata.put("externalPath", "/api/v1/demoapp1");
//		ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
//				.defaultUriSpec()
//				.address("anyUrl")
//				.port(10)
//				.payload(new ZookeeperInstance("testting", "name", metadata ))
//				.name("/a/b/c/d/anotherservice")
//				.build();
//		this.serviceRegistry.register(registration);
//	}


	@Value("${return.error}")
	boolean returnError;

	Logger logger = LoggerFactory.getLogger(NbdemoApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(NbdemoApplication.class, args);
	}


	@GetMapping("/hello")
	public ResponseEntity<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		if (!returnError) {
			logger.warn("------> Hello");
			return new ResponseEntity(String.format("Hello %s!", name), HttpStatus.OK);
		} else {
			logger.warn("------> Returning error");
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

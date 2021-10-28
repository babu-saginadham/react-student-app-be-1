package com.app.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="test")
@CrossOrigin(origins = {"http://localhost:3000"})
public class HelloWorld {
	
	static Logger logger = Logger.getLogger(HelloWorld.class);
	
	@GetMapping(value="/getMessage")
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(HttpStatus.OK).ok("From BackEnd");
	}
	
	@GetMapping(value="/getMessage/{msg}")
	public ResponseEntity<Object> getFromPath(@PathVariable String msg ) {
		return ResponseEntity.ok(HttpStatus.OK).ok("From BackEnd " + msg);
	}
	
	@GetMapping(value="/error")
	public ResponseEntity<Object> getFromPath1(@PathVariable String msg ) {
		throw new RuntimeException("Exception occured:");
	}

}

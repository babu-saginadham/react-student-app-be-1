package com.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.StudentModel;
import com.app.service.StudentService;

@RestController
@RequestMapping(value="student")
@CrossOrigin(origins = {"http://localhost:3000"})
public class StudentController {
	
	static Logger logger = Logger.getLogger(StudentController.class);
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping(value="/all")
	public ResponseEntity<Object> getAll(@RequestParam(value="startsWith", required = false) String nameStartsWith) {
		return ResponseEntity.ok(HttpStatus.OK).ok(studentService.getAllStudents());
	}
	
	@GetMapping(value="/v2/all")
	public ResponseEntity<Object> getAll(
			@RequestParam(value="startsWith", required = false) String nameStartsWith,
			@RequestParam(value="pageNo", required = false) Integer pageNo,
			@RequestParam(value="limit", required = false) Integer limit,
			@RequestParam(value="sortBy", required = false) String sortBy,
			@RequestParam(value="sortOrder", required = false) String sortOrder) {
		return ResponseEntity.ok(HttpStatus.OK).ok(studentService.getAllStudents(nameStartsWith, 
				pageNo, limit, sortBy, sortOrder));
	}
	
	@PostMapping
	public ResponseEntity<Long> create(@Valid @RequestBody StudentModel student) {
		Long sno = studentService.createStudent(student);
		return ResponseEntity.ok(HttpStatus.OK).ok(sno);
	}
	
	@PutMapping(value="/{sno}")
	public ResponseEntity<Void> put(@PathVariable Long sno, @RequestBody StudentModel student) {
		studentService.updateStudent(sno, student);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/{sno}")
	public ResponseEntity<Void> delete(@PathVariable Long sno) {
		studentService.deleteStudent(sno);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping(value="/{sno}")
	public ResponseEntity<Object> getStudent(@PathVariable Long sno) {
		StudentModel stu = studentService.getStudent(sno);
		return ResponseEntity.ok(HttpStatus.OK).ok(stu);
	}
	
	@GetMapping(value="/v2/{id}")
	public ResponseEntity<List<StudentModel>> getStudent2(@PathVariable("id") Integer id, 
			@RequestParam(value="startsWith", required = false) String nameStartsWith) {
		return null;
	}
	
	
	

	
	
	
	

}

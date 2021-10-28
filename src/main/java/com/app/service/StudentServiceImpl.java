package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.controller.StudentController;
import com.app.jdbc.model.Student;
import com.app.model.StudentModel;
import com.app.repository.StudentRepository;
import com.exception.ResourceNotFoundException;
import com.exception.ValidationFailureException;

@Service
public class StudentServiceImpl implements StudentService{

	static Logger logger = Logger.getLogger(StudentController.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Long createStudent(StudentModel studentModel) {
		
		
		//Validations
		/**
		 * 1. sname can't be empty / mandatory
		 * 2. sname should be min of 3 characters
		 * 3. sname can't be more than 100 characters
		 * 
		 */
		String sname = studentModel.getSname();
		if(sname == null || "".equals(sname)) {
			logger.error("Student Can't be empty");
			throw new ValidationFailureException("Invalid Request. PLs give proper student name:");
		}
		
		//length
		if(sname.length() <3 || sname.length() >100) {
			logger.error("not flling under the range");
			throw new ValidationFailureException("Invalid Request. student name can't be less than 3 & not more than 100 character");
		}
		
		
		Student studentToSave = new Student();
		studentToSave.setSname(studentModel.getSname());
		
		Student savedData = studentRepository.save(studentToSave);
		
		return savedData.getSno();
	}

	@Override
	public void updateStudent(Long stud_id, StudentModel studentModel) {
		
		/**
		 * Validation
		 * 1. Static valdiation (Payload)
		 * 		id is available in request or not
		 * 		it shouldn't be 0
		 * 2. DB Validation
		 * 		student is exists or not
		 * 	HTTP status : 404
		 */
		Optional<Student> studFromDB = studentRepository.findById(stud_id);
		if(!studFromDB.isPresent()) {
			logger.error("For the given student Id, no record found. pls give valid input");
			throw new ResourceNotFoundException("For the given student Id, no record found. pls give valid input");
		}
		
		Student fromDb = studFromDB.get();
		fromDb.setSname(studentModel.getSname());
		
		studentRepository.save(fromDb);
		
		
	}

	@Override
	public void deleteStudent(Long sno) {
		studentRepository.deleteById(sno);
		
	}

	@Override
	public List<StudentModel> getAllStudents() {
		List<Student> studentsFromDB = studentRepository.findAll();
	
		List<StudentModel> studentModels = new ArrayList<>();
		for(Student stud:studentsFromDB) {
			StudentModel studentModel = new StudentModel();
			studentModel.setSno(stud.getSno());
			studentModel.setSname(stud.getSname());
			
			studentModels.add(studentModel);
			
		}
		
		//TODO: BeanMapper
		
		return studentModels;
	}

	@Override
	public StudentModel getStudent(Long sno) {
		Optional<Student> studDB = studentRepository.findById(sno);
		
		if(studDB.isPresent()) {
			Student fromDB = studDB.get();
			
			StudentModel stud = new StudentModel();
			stud.setSname(fromDB.getSname());
			stud.setSno(fromDB.getSno());
			return stud;
			
		}else {
			//Throw exception 
			return new StudentModel();
		}
		
		
	}
	
	@Override
	public Page<Student> getAllStudents(String nameStartsWith, 
			Integer pageNo, Integer limit,
			String sortBy, String sortOrder) {
		
		//Way1 - without sorting
		//Pageable pageable = PageRequest.of(pageNo, limit);
		
		
		Sort sort = Sort.by(sortBy);
		if(sortOrder.equals("asc")) {
			sort = sort.ascending();
		}else {
			sort = sort.descending();
		}
		
		Pageable pageable = PageRequest.of(pageNo, limit, sort);
		
		Page<Student> dbData = studentRepository.getPaginatedStudents(pageable);
		
		
		return dbData;
	}

}

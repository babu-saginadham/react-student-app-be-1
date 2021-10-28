package com.app.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StudentModel {

	@Min(1)
	@Max(1000000)
	Long sno;
	
	@NotNull
	@Size(max = 100, min = 3, message = "Student No can't be more than 100 characters")
	String sname;
	
	/*
	@Pattern(regexp = "[A-Za-z]{1,100}")
	private String address;
	
	@Email(message = "Email Address is invalid")
	private String email;
	*/
	
	public Long getSno() {
		return sno;
	}
	public void setSno(Long sno) {
		this.sno = sno;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	
}

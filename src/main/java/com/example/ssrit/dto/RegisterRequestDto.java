package com.example.ssrit.dto;

import org.springframework.web.multipart.MultipartFile;

public class RegisterRequestDto {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String mobile;
    private String highestQualification;
    private String workStatus;
    private Integer yearOfExperience;
    private MultipartFile resume;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public Integer getYearOfExperience() {
		return yearOfExperience;
	}
	public void setYearOfExperience(Integer yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}
	public MultipartFile getResume() {
		return resume;
	}
	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}
	@Override
	public String toString() {
		return "RegisterRequestDto [name=" + name + ", email=" + email + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", mobile=" + mobile + ", highestQualification=" + highestQualification
				+ ", workStatus=" + workStatus + ", yearOfExperience=" + yearOfExperience + ", resume=" + resume + "]";
	}

    
}

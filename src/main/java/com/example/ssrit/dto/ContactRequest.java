package com.example.ssrit.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 10, max = 1500, message = "Phone number must be between 10–1500 digits")
    private String phoneNumber;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 2000, message = "Message cannot exceed 2000 characters")
    private String message;

    // Default constructor
    public ContactRequest() {}

    public ContactRequest(String name, String email, String phoneNumber, String message) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    // Getters & Setters
    public String getName() { return name; }
    @Override
	public String toString() {
		return "ContactRequest [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", message="
				+ message + "]";
	}

	public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

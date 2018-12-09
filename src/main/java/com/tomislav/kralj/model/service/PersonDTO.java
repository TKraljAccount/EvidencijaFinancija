package com.tomislav.kralj.model.service;

import lombok.Data;

@Data
public class PersonDTO implements ServiceObject {

	private Integer personId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;

}

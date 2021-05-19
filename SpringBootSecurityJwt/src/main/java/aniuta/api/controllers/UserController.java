package com.huyhs.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huyhs.models.User;
import com.huyhs.services.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getAllUser(){
		return new ResponseEntity<>(userServiceImpl.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userServiceImpl.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userServiceImpl.findById(id);
        return userOptional.map(category -> {
        	userServiceImpl.remove(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
}
	
	@PutMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
		userServiceImpl.update(id, user);
        return new ResponseEntity<>(userServiceImpl.findById(id), HttpStatus.OK);
}
	
}

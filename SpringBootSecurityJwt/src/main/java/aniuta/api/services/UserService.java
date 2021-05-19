package com.huyhs.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.huyhs.models.User;
import com.huyhs.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void remove(Long id) {
		userRepository.deleteById(id);

	}

	@Override
	public void update(Long id, User user) {
		User existingUser = userRepository.findById(id).orElse(null);
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(encoder.encode(user.getPassword()));	
		existingUser.setRoles(user.getRoles());
        userRepository.save(existingUser);
	}

}

package br.com.navita.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.navita.exception.BusinessException;
import br.com.navita.model.User;
import br.com.navita.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
	return userRepository.findAll();
    }

    public Optional<User> findById(Long userId) {
	return userRepository.findById(userId);
    }

    public User save(User user) {
	User userFoundByEmail = userRepository.findByEmail(user.getEmail());

	if (userFoundByEmail != null && !userFoundByEmail.equals(user)) {
	    throw new BusinessException("There is already a registered user with this email!");
	}

	return userRepository.save(user);
    }

    public User update(User user) {
	return userRepository.save(user);
    }

    public void deleteById(Long userId) {
	userRepository.deleteById(userId);
    }

}
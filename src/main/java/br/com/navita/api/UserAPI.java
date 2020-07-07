package br.com.navita.api;

import java.util.List;

import javax.json.Json;
import javax.json.JsonString;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.navita.exception.BusinessException;
import br.com.navita.model.User;
import br.com.navita.service.UserService;
import br.com.navita.utils.Encrypt;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    @Autowired
    private UserService userService;

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<User>> findAll() {
	return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
	
	user.setPassword(Encrypt.encryptPassword(user.getPassword()));

	try {
	    User createdUser = userService.save(user);
	    return new ResponseEntity<Object>(createdUser, HttpStatus.CREATED);
	} catch (BusinessException e) {
	    JsonString jsonNotFoundMessage = Json.createValue(e.getMessage());
	    
	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.CONFLICT);
	}
    }

    @DeleteMapping("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<JsonString> delete(@PathVariable Long userId) {
	
	if (!userService.findById(userId).isPresent()) {
	    JsonString jsonNotFoundMessage = Json.createValue("User not found with id " + userId);

	    return new ResponseEntity<JsonString>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	userService.deleteById(userId);

	return ResponseEntity.ok().build();
    }

}
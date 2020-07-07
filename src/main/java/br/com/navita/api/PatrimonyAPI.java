package br.com.navita.api;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.navita.exception.BusinessException;
import br.com.navita.model.Patrimony;
import br.com.navita.service.PatrimonyService;

@RestController
@RequestMapping("/api/patrimonies")
public class PatrimonyAPI {

    @Autowired
    private PatrimonyService patrimonyService;

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Patrimony>> findAll() {
	return ResponseEntity.ok(patrimonyService.findAll());
    }

    @GetMapping("/{patrimonyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> findById(@PathVariable Long patrimonyId) {
	
	Optional<Patrimony> patrimony = patrimonyService.findById(patrimonyId);
	
	if (!patrimony.isPresent()) {
	    JsonString jsonNotFoundMessage = Json.createValue("Patrimony not found with id " + patrimonyId);

	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	return new ResponseEntity<Object>(patrimony.get(), HttpStatus.OK);
    }

    @PostMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> create(@Valid @RequestBody Patrimony patrimony) {
	try {
	    Patrimony createdPatrimony = patrimonyService.save(patrimony);
	    
	    return new ResponseEntity<Object>(createdPatrimony, HttpStatus.CREATED);
	} catch (BusinessException e) {
	    JsonString jsonNotFoundMessage = Json.createValue(e.getMessage());
	    
	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}
    }

    @PutMapping("/{patrimonyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> update(@Valid @PathVariable Long patrimonyId, @RequestBody Patrimony patrimony) {
	try {
	    if (!patrimonyService.findById(patrimonyId).isPresent()) {
		JsonString jsonNotFoundMessage = Json.createValue("Patrimony not found with id " + patrimonyId);
		
		return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	    }
	    
	    patrimony.setId(patrimonyId);
	    patrimony = patrimonyService.save(patrimony);
	    
	    return new ResponseEntity<Object>(patrimony, HttpStatus.OK);
	} catch (BusinessException e) {
	    JsonString jsonNotFoundMessage = Json.createValue(e.getMessage());
	    
	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.CONFLICT);
	}
    }

    @DeleteMapping("/{patrimonyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<JsonString> delete(@PathVariable Long patrimonyId) {
	
	if (!patrimonyService.findById(patrimonyId).isPresent()) {
	    JsonString jsonNotFoundMessage = Json.createValue("Patrimony not found with id " + patrimonyId);

	    return new ResponseEntity<JsonString>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	patrimonyService.deleteById(patrimonyId);

	return ResponseEntity.ok().build();
    }

}
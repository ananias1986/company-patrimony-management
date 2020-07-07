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
import br.com.navita.model.Brand;
import br.com.navita.model.Patrimony;
import br.com.navita.service.BrandService;
import br.com.navita.service.PatrimonyService;

@RestController
@RequestMapping("/api/brands")
public class BrandAPI {

    @Autowired
    private BrandService brandService;

    @Autowired
    private PatrimonyService patrimonyService;

    @GetMapping
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Brand>> findAll() {
	return ResponseEntity.ok(brandService.findAll());
    }

    @GetMapping("/{brandId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> findById(@PathVariable Long brandId) {
	
	Optional<Brand> brand = brandService.findById(brandId);
	
	if (!brand.isPresent()) {
	    JsonString jsonNotFoundMessage = Json.createValue("Brand not found with id " + brandId);
	    
	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	return new ResponseEntity<Object>(brand.get(), HttpStatus.OK);
    }

    @GetMapping("/{brandId}/patrimonies")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> findAllByBrand(@PathVariable Long brandId) {
	
	List<Patrimony> patrimonies = patrimonyService.findByBrandId(brandId);
	
	if (patrimonies.isEmpty()) {
	    JsonString jsonNotFoundMessage = Json.createValue("Brand not found with id " + brandId);
	    
	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	return new ResponseEntity<Object>(patrimonies, HttpStatus.OK);
    }

    @PostMapping
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> create(@Valid @RequestBody Brand brand) {
	try {
	    Brand createdBrand = brandService.save(brand);
	    
	    return new ResponseEntity<Object>(createdBrand, HttpStatus.CREATED);
	} catch (BusinessException e) {
	    JsonString jsonNotFoundMessage = Json.createValue(e.getMessage());
	    
	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.CONFLICT);
	}
    }

    @PutMapping("/{brandId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> update(@Valid @PathVariable Long brandId, @RequestBody Brand brand) {
	
	if (!brandService.findById(brandId).isPresent()) {
	    JsonString jsonNotFoundMessage = Json.createValue("Brand not found with id " + brandId);

	    return new ResponseEntity<Object>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	brand.setId(brandId);
	brand = brandService.save(brand);

	return new ResponseEntity<Object>(brand, HttpStatus.OK);
    }

    @DeleteMapping("/{brandId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<JsonString> delete(@PathVariable Long brandId) {
	
	if (!brandService.findById(brandId).isPresent()) {
	    JsonString jsonNotFoundMessage = Json.createValue("Brand not found with id " + brandId);

	    return new ResponseEntity<JsonString>(jsonNotFoundMessage, HttpStatus.NOT_FOUND);
	}

	brandService.deleteById(brandId);

	return ResponseEntity.ok().build();
    }

}
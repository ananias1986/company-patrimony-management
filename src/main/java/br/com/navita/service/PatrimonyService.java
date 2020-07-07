package br.com.navita.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.navita.exception.BusinessException;
import br.com.navita.model.Brand;
import br.com.navita.model.Patrimony;
import br.com.navita.repository.PatrimonyRepository;

@Service
public class PatrimonyService {

    @Autowired
    private PatrimonyRepository patrimonyRepository;

    @Autowired
    private BrandService brandService;
    
    public List<Patrimony> findAll() {
	return patrimonyRepository.findAll();
    }

    public Optional<Patrimony> findById(Long patrimonyId) {
	return patrimonyRepository.findById(patrimonyId);
    }

    public List<Patrimony> findByBrandId(Long brandId) {
	return patrimonyRepository.findByBrandId(brandId);
    }

    public Patrimony save(Patrimony patrimony) {
	
	Optional<Brand> brand = brandService.findById(patrimony.getBrand().getId());
	
	if (!brand.isPresent()) {
	    throw new BusinessException("Brand not found with id " + patrimony.getBrand().getId());
	}
	
	return patrimonyRepository.save(patrimony);
    }

    public Patrimony update(Patrimony patrimony) {
	
	Optional<Brand> brand = brandService.findById(patrimony.getBrand().getId());
	
	if (!brand.isPresent()) {
	    throw new BusinessException("Brand not found with id " + patrimony.getBrand().getId());
	}
	
	return patrimonyRepository.save(patrimony);
    }

    public void deleteById(Long patrimonyId) {
	patrimonyRepository.deleteById(patrimonyId);
    }

}
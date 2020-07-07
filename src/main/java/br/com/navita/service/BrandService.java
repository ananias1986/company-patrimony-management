package br.com.navita.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.navita.exception.BusinessException;
import br.com.navita.model.Brand;
import br.com.navita.repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() {
	return brandRepository.findAll();
    }

    public Optional<Brand> findById(Long brandId) {
	return brandRepository.findById(brandId);
    }

    public Brand save(Brand brand) {
	Brand brandFoundByName = brandRepository.findByName(brand.getName());

	if (brandFoundByName != null && !brandFoundByName.equals(brand)) {
	    throw new BusinessException("There is already a registered brand with this name!");
	}

	return brandRepository.save(brand);
    }

    public Brand update(Brand brand) {
	return brandRepository.save(brand);
    }

    public void deleteById(Long brandId) {
	brandRepository.deleteById(brandId);
    }

}
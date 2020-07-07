package br.com.navita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.navita.model.Patrimony;

@Repository
public interface PatrimonyRepository extends JpaRepository<Patrimony, Long> {

    List<Patrimony> findByBrandId(Long brandId);

}

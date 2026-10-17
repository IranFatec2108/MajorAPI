package br.com.iranfatec.majorapi.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, Long> {


    boolean existsByNameIgnoreCaseAndActiveTrue(String nome);

    boolean existsByNameIgnoreCaseAndActiveTrueAndIdNot(String nome, Long id);
}

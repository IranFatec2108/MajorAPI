package br.com.iranfatec.majorapi.service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, Long> {
    boolean existsByNameIgnoreCaseAndActiveTrue(String nome);

    boolean existsByNameIgnoreCaseAndActiveTrueAndIdNot(String nome, Long id);
    // Busca por ID somente se o serviço estiver ativo
    Optional<ProvidedService> findByIdAndActiveTrue(Long id);
}

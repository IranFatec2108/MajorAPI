package br.com.iranfatec.majorapi.service;

import org.springframework.stereotype.Service; // Annotation do Spring (Sem conflito agora!)
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Spring Service
public class ServiceManager {

    private final ProvidedServiceRepository providedServiceRepository;

    public ServiceManager(ProvidedServiceRepository providedServiceRepository) {
        this.providedServiceRepository = providedServiceRepository;
    }

    public ServiceResponseDto registerService(ServiceRequestDto dto) {
        if (providedServiceRepository.existsByNameIgnoreCaseAndActiveTrue(dto.name())) {
            throw new ActiveNameAlreadyExistsException("O nome a ser cadastrado já existe em um serviço ativo");
        }

        ProvidedService providedService = toEntity(dto);
        ProvidedService savedService = providedServiceRepository.save(providedService);

        return toResponseDto(savedService);
    }

    public List<ServiceResponseDto> listServices() {
        List<ProvidedService> services = providedServiceRepository.findAll();
        return services.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ServiceResponseDto listById(Long id) {
        Optional<ProvidedService> foundServiceById = providedServiceRepository.findById(id);

        if (foundServiceById.isEmpty()) {
            throw new ServiceNotFoundException("Esse serviço não existe, por favor digite um id válido");
        }

        return toResponseDto(foundServiceById.get());
    }

    public ServiceResponseDto updateById(Long id, ServiceRequestDto dto) {
        Optional<ProvidedService> serviceById = providedServiceRepository.findById(id);

        if (serviceById.isEmpty()) {
            throw new ServiceNotFoundException("Serviço não encontrado, por favor digite um id válido para alterar um serviço");
        }

        if (providedServiceRepository.existsByNameIgnoreCaseAndActiveTrueAndIdNot(dto.name(), id)) {
            throw new ActiveNameAlreadyExistsException("O nome já existe em um serviço ativo, por favor escrever nome válido");
        }

        ProvidedService validatedService = serviceById.get();
        validatedService.setName(dto.name());
        validatedService.setSuggestedPrice(dto.suggestedPrice());

        ProvidedService savedService = providedServiceRepository.save(validatedService);
        return toResponseDto(savedService);
    }

    public ServiceResponseDto deactivateById(Long id) {
        Optional<ProvidedService> serviceById = providedServiceRepository.findById(id);

        if (serviceById.isEmpty()) {
            throw new ServiceNotFoundException("Serviço não existente, digite um ID válido");
        }

        ProvidedService service = serviceById.get();

        if (!service.isActive()) {
            return toResponseDto(service);
        } else {
            service.setActive(false);
            ProvidedService updatedService = providedServiceRepository.save(service);
            return toResponseDto(updatedService);
        }
    }

    private ProvidedService toEntity(ServiceRequestDto dto) {
        ProvidedService service = new ProvidedService();
        service.setName(dto.name());
        service.setSuggestedPrice(dto.suggestedPrice());
        service.setActive(true);
        return service;
    }

    private ServiceResponseDto toResponseDto(ProvidedService service) {
        return new ServiceResponseDto(
                service.getId(),
                service.getName(),
                service.getSuggestedPrice(),
                service.isActive()
        );
    }
}

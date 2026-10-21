package br.com.iranfatec.majorapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Cobertura de todas as operações públicas do {@link ServiceManager}.
 *
 * Cada método segue o padrão AAA (Arrange‑Act‑Assert) e usa Mockito
 * para isolar a camada de persistência (ProvidedServiceRepository).
 */
@ExtendWith(MockitoExtension.class)
class ServiceManagerTest {

    /*
       Mocks e objeto em teste
       */
    @Mock
    private ProvidedServiceRepository repository;

    @InjectMocks
    private ServiceManager serviceManager;

    /*   Helper – cria rapidamente uma entidade de teste   */
    private ProvidedService fakeService(Long id, String name, BigDecimal price, boolean active) {
        ProvidedService s = new ProvidedService();
        s.setId(id);
        s.setName(name);
        s.setSuggestedPrice(price);
        s.setActive(active);
        return s;
    }

    /*   registerService  */

    @Test
    void registerService_createsWhenNameIsFree() {
        // Arrange
        ServiceRequestDto request = new ServiceRequestDto("Barba", BigDecimal.valueOf(25));
        when(repository.existsByNameIgnoreCaseAndActiveTrue("Barba")).thenReturn(false);

        // Simula o retorno do save (id = 1L)
        ProvidedService saved = fakeService(1L, "Barba", BigDecimal.valueOf(25), true);
        when(repository.save(any(ProvidedService.class))).thenReturn(saved);

        // Act
        ServiceResponseDto result = serviceManager.registerService(request);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Barba", result.name());
        assertEquals(BigDecimal.valueOf(25), result.suggestedPrice());
        assertTrue(result.active());

        // verifica que o repository.save foi realmente chamado
        verify(repository).save(any(ProvidedService.class));
    }

    @Test
    void registerService_throwsWhenNameAlreadyActive() {
        // Arrange
        ServiceRequestDto request = new ServiceRequestDto("Corte", BigDecimal.valueOf(30));

        // O repositório indica que já existe um serviço ativo com esse nome
        when(repository.existsByNameIgnoreCaseAndActiveTrue("Corte")).thenReturn(true);

        // Act / Assert
        assertThrows(ActiveNameAlreadyExistsException.class,
                () -> serviceManager.registerService(request));
    }


    /*   listServices   */

    @Test
    void listServices_returnsAllConvertedToDto() {
        // Arrange
        ProvidedService s1 = fakeService(1L, "Corte", BigDecimal.valueOf(30), true);
        ProvidedService s2 = fakeService(2L, "Barba", BigDecimal.valueOf(20), true);
        when(repository.findAll()).thenReturn(List.of(s1, s2));

        // Act
        List<ServiceResponseDto> result = serviceManager.listServices();

        // Assert
        assertEquals(2, result.size());

        ServiceResponseDto dto1 = result.get(0);
        assertEquals(1L, dto1.id());
        assertEquals("Corte", dto1.name());

        ServiceResponseDto dto2 = result.get(1);
        assertEquals(2L, dto2.id());
        assertEquals("Barba", dto2.name());

        verify(repository).findAll();
    }

    /*   listById   */

    @Test
    void listById_returnsDtoWhenFound() {
        // Arrange
        ProvidedService fake = fakeService(1L, "Corte", BigDecimal.valueOf(30), true);
        when(repository.findById(1L)).thenReturn(Optional.of(fake));

        // Act
        ServiceResponseDto result = serviceManager.listById(1L);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Corte", result.name());
        assertEquals(BigDecimal.valueOf(30), result.suggestedPrice());
        assertTrue(result.active());
    }

    @Test
    void listById_throwsWhenNotFound() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act / Assert
        assertThrows(ServiceNotFoundException.class,
                () -> serviceManager.listById(99L));
    }

    /*   updateById  */

    @Test
    void updateById_updatesWhenIdExistsAndNameIsFree() {
        // Arrange
        ServiceRequestDto request = new ServiceRequestDto("Corte Premium", BigDecimal.valueOf(35));
        ProvidedService stored = fakeService(1L, "Corte", BigDecimal.valueOf(30), true);
        when(repository.findById(1L)).thenReturn(Optional.of(stored));
        when(repository.existsByNameIgnoreCaseAndActiveTrueAndIdNot("Corte Premium", 1L))
                .thenReturn(false);

        // Simula save → devolve o mesmo objeto já modificado
        when(repository.save(stored)).thenReturn(stored);

        // Act
        ServiceResponseDto result = serviceManager.updateById(1L, request);

        // Assert
        assertEquals("Corte Premium", result.name());
        assertEquals(BigDecimal.valueOf(35), result.suggestedPrice());
        verify(repository).save(stored);
    }

    @Test
    void updateById_throwsWhenIdNotFound() {
        // Arrange
        ServiceRequestDto request = new ServiceRequestDto("Qualquer", BigDecimal.valueOf(10));
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act / Assert
        assertThrows(ServiceNotFoundException.class,
                () -> serviceManager.updateById(99L, request));
    }

    @Test
    void updateById_throwsWhenNameAlreadyExists() {
        // Arrange
        ServiceRequestDto request = new ServiceRequestDto("Barba", BigDecimal.valueOf(25));
        ProvidedService stored = fakeService(2L, "Corte", BigDecimal.valueOf(30), true);
        ProvidedService conflict = fakeService(1L, "Barba", BigDecimal.valueOf(20), true);

        when(repository.findById(2L)).thenReturn(Optional.of(stored));
        when(repository.existsByNameIgnoreCaseAndActiveTrueAndIdNot("Barba", 2L))
                .thenReturn(true);
        // (o mock do findByName não é necessário aqui, só para deixar claro que há conflito)

        // Act / Assert
        assertThrows(ActiveNameAlreadyExistsException.class,
                () -> serviceManager.updateById(2L, request));
    }

    /* deactivateById   */

    @Test
    void deactivateById_deactivatesWhenActive() {
        // Arrange
        ProvidedService stored = fakeService(1L, "Barba", BigDecimal.valueOf(20), true);
        when(repository.findById(1L)).thenReturn(Optional.of(stored));
        when(repository.save(stored)).thenReturn(stored); // salva com active = false

        // Act
        ServiceResponseDto result = serviceManager.deactivateById(1L);

        // Assert
        assertFalse(result.active());
        verify(repository).save(stored);
    }

    @Test
    void deactivateById_returnsSameDtoWhenAlreadyInactive() {
        // Arrange
        ProvidedService stored = fakeService(1L, "Corte", BigDecimal.valueOf(30), false);
        when(repository.findById(1L)).thenReturn(Optional.of(stored));

        // Act
        ServiceResponseDto result = serviceManager.deactivateById(1L);

        // Assert
        assertFalse(result.active());
        // não deve chamar save, pois já está inativo
        verify(repository, never()).save(any());
    }

    @Test
    void deactivateById_throwsWhenIdNotFound() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act / Assert
        assertThrows(ServiceNotFoundException.class,
                () -> serviceManager.deactivateById(99L));
    }
}
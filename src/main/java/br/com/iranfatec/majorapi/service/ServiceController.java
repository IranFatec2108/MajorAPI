package br.com.iranfatec.majorapi.service;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/servicos")
public class ServiceController {

    private final ServiceManager serviceManager;

    public ServiceController(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> cadastrar (@Valid @RequestBody ServiceRequestDto dto)  {
            ServiceResponseDto responseCadastro = serviceManager.registerService(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastro);
        }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> listar(){
        List<ServiceResponseDto> servicosList = serviceManager.listServices();
        return ResponseEntity.ok(servicosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> listarPorId(@PathVariable Long id){
        ServiceResponseDto responseListarPorId = serviceManager.listById(id);
        return ResponseEntity.ok().body(responseListarPorId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> alterarPorId(@PathVariable Long id , @Valid @RequestBody ServiceRequestDto dto){
        ServiceResponseDto responseAlterarPorId = serviceManager.updateById(id, dto);
        return ResponseEntity.ok().body(responseAlterarPorId);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<ServiceResponseDto> inativarPorId (@PathVariable Long id){
        ServiceResponseDto responseInativarPorId = serviceManager.deactivateById(id);
        return  ResponseEntity.ok().body(responseInativarPorId);
    }

}




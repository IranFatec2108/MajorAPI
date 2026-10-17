package br.com.iranfatec.majorapi.service;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceManager serviceManager;

    public ServiceController(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> registerService (@Valid @RequestBody ServiceRequestDto dto)  {
            ServiceResponseDto responseRegisterService = serviceManager.registerService(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseRegisterService);
        }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> listAllServices(){
        List<ServiceResponseDto> responseServicesList = serviceManager.listServices();
        return ResponseEntity.ok(responseServicesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> listServiceById(@PathVariable Long id){
        ServiceResponseDto responseListServiceById = serviceManager.listById(id);
        return ResponseEntity.ok().body(responseListServiceById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateServiceById(@PathVariable Long id , @Valid @RequestBody ServiceRequestDto dto){
        ServiceResponseDto responseUpdateServiceById = serviceManager.updateById(id, dto);
        return ResponseEntity.ok().body(responseUpdateServiceById);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ServiceResponseDto> deactivateServiceById (@PathVariable Long id){
        ServiceResponseDto responseDeactivateServiceById = serviceManager.deactivateById(id);
        return  ResponseEntity.ok().body(responseDeactivateServiceById);
    }

}




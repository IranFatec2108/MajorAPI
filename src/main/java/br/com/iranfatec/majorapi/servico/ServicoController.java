package br.com.iranfatec.majorapi.servico;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDto> cadastrar (@Valid @RequestBody ServicoRequestDto dto)  {
            ServicoResponseDto response = servicoService.cadastrarServico(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDto>> listar(){
        List<ServicoResponseDto> servicos = servicoService.listarServicos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDto> listarPorId(@PathVariable Long id){
        ServicoResponseDto responsePorId = servicoService.listarPorId(id);
        return ResponseEntity.ok().body(responsePorId);
    }
}




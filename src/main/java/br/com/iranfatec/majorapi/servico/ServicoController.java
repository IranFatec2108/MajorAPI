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
            ServicoResponseDto responseCadastro = servicoService.cadastrarServico(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCadastro);
        }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDto>> listar(){
        List<ServicoResponseDto> servicosList = servicoService.listarServicos();
        return ResponseEntity.ok(servicosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDto> listarPorId(@PathVariable Long id){
        ServicoResponseDto responseListarPorId = servicoService.listarPorId(id);
        return ResponseEntity.ok().body(responseListarPorId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDto> alterarPorId(@PathVariable Long id , @Valid @RequestBody ServicoRequestDto dto){
        ServicoResponseDto responseAlterarPorId = servicoService.alterarPorId(id, dto);
        return ResponseEntity.ok().body(responseAlterarPorId);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<ServicoResponseDto> inativarPorId (@PathVariable Long id){
        ServicoResponseDto responseInativarPorId = servicoService.inativarPorId(id);
        return  ResponseEntity.ok().body(responseInativarPorId);
    }

}




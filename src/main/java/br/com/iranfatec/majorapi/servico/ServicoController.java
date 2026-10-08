package br.com.iranfatec.majorapi.servico;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    }



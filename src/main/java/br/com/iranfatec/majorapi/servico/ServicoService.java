package br.com.iranfatec.majorapi.servico;

import org.springframework.stereotype.Service;

@Service
public class ServicoService {
     private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public ServicoResponseDto cadastrarServico(ServicoRequestDto dto) {


        if (servicoRepository.existsByNomeIgnoreCaseAndAtivoTrue(dto.nome())) {
            throw new NomeAtivoJaExistenteException("O nome a ser cadastrado já existe em um serviço ativo");
        }

        Servico servico = toEntity(dto);

        Servico servicoSalvo = servicoRepository.save(servico);

        return toResponseDto(servicoSalvo);

     }

    private Servico toEntity(ServicoRequestDto dto){

        Servico servico = new Servico();
        servico.setNome(dto.nome());
        servico.setPrecoSugerido(dto.precoSugerido());
        servico.setAtivo(true);
        return servico;
    }

    private ServicoResponseDto toResponseDto(Servico servico){

        return new ServicoResponseDto(
                servico.getId(),
                servico.getNome(),
                servico.getPrecoSugerido(),
                servico.isAtivo()
        );
    }
}







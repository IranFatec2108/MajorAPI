package br.com.iranfatec.majorapi.servico;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

     public List<ServicoResponseDto> listarServicos(){
        List<Servico> servicos = servicoRepository.findAll();
        return servicos.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
     }

     public ServicoResponseDto listarPorId(Long id){
        Optional<Servico> servicoAchado = servicoRepository.findById(id);

        if(servicoAchado.isEmpty()){
           throw new ServicoNaoEncontradoException ("Esse serviço não existe, por favor digite um id válido");
        }else{
           return toResponseDto(servicoAchado.get());
        }
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







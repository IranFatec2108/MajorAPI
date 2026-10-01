package br.com.iranfatec.majorapi.servico;

import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    final private ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico cadastrarServico(Servico servico) throws ServicoVazioException, NomeInvalidoException, NomeAtivoJaExistenteException {
        if (servico == null) {
            throw new ServicoVazioException("Os campos de cadastros são inválidos, por favor preencher todos os campos");
        }
        if (servico.getNome() == null || servico.getNome().isBlank()) {
            throw new NomeInvalidoException("O campo de nome não pode ser vazio");
        }

        if (servicoRepository.existsByNomeIgnoreCaseAndAtivoTrue(servico.getNome())) {
            throw new NomeAtivoJaExistenteException("O nome a ser cadastrado já existe em um serviço ativo");
        }

        servico.setAtivo(true);

        Servico servicoSalvo = servicoRepository.save(servico);

        return servicoSalvo;
    }
}







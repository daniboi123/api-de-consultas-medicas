package med.voll.api.domain.agendamento;

import med.voll.api.domain.Paciente.PacientesRepository;
import med.voll.api.domain.ValidacaoExcepetion;
import med.voll.api.domain.agendamento.validacaoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendeDeConsultaService  {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacientesRepository pacientesRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if (!pacientesRepository.existsById(dados.idPaciente())){
            throw new ValidacaoExcepetion("id do paciente informado não existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoExcepetion("id do medico informado não existe");
        }

        validadores.forEach(v -> v.validar((dados)));

        var paciente = pacientesRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null){
            throw new ValidacaoExcepetion("não existe medico disponivel nessa data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data()) ;
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoExcepetion("especialidade e obrigatoria quando o medico não for escolhido!");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}

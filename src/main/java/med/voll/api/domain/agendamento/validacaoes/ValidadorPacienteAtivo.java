package med.voll.api.domain.agendamento.validacaoes;

import med.voll.api.domain.Paciente.PacientesRepository;
import med.voll.api.domain.ValidacaoExcepetion;
import med.voll.api.domain.agendamento.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private PacientesRepository repository;

    public void validar(DadosAgendamentoConsulta dados){

        var medicoEstaAtivo = repository.findAtivoById(dados.idPaciente());
        if (!medicoEstaAtivo){
            throw new ValidacaoExcepetion("Consulta não pode ser agendada com paciente excluido");

        }    }
}

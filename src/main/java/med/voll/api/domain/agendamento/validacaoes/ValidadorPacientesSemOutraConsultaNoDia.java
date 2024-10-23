package med.voll.api.domain.agendamento.validacaoes;

import med.voll.api.domain.ValidacaoExcepetion;
import med.voll.api.domain.agendamento.ConsultaRepository;
import med.voll.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacientesSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientesPossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientesPossuiOutraConsultaNoDia){
            throw new ValidacaoExcepetion("Paciente ja possui uma consulta agendada nesse dia");

        }    }
}

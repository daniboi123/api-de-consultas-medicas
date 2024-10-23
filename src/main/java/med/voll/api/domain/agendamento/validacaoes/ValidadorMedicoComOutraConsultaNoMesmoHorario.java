package med.voll.api.domain.agendamento.validacaoes;

import med.voll.api.domain.ValidacaoExcepetion;
import med.voll.api.domain.agendamento.Consulta;
import med.voll.api.domain.agendamento.ConsultaRepository;
import med.voll.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoExcepetion("Medico ja possui outra consulta agendada nesse mesmo horario");
        }
    }
}

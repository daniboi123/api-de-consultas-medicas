package med.voll.api.controller;

import med.voll.api.domain.agendamento.AgendeDeConsultaService;
import med.voll.api.domain.agendamento.DadosAgendamentoConsulta;
import med.voll.api.domain.agendamento.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class consultaControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta>  dadosAgendamentoConsultaJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta>  detalhamentoConsultaJson;
    @MockBean
    private AgendeDeConsultaService agendeDeConsultaService;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando as informaçãoes estão invalidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
       var responce = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

       assertThat(responce.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando as informaçãoes estão validas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var dadosDetalhamento =   new DadosDetalhamentoConsulta(null,2l,5l,data);
        var especialidade = Especialidade.CARDIOLOGIA;


        when(agendeDeConsultaService.agendar(any())).thenReturn(dadosDetalhamento);

        var responce = mvc.perform(post("/consultas")


                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(2l, 5l,data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();


        assertThat(responce.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = detalhamentoConsultaJson.write(
                dadosDetalhamento
        ).getJson();
        assertThat(responce.getContentAsString()).isEqualTo(jsonEsperado);
    }

}
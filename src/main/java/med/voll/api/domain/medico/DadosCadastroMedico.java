package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        //not blank verifica se o campo está vazio e tbm se está nulo, OBS NOTBLANK E SÓ PARA STRING
                                  @NotBlank
                                  String nome,
                                  @NotBlank
                                  @Email
                                  String email,
                                  @NotBlank
                                  String telefone,
                                  @NotBlank
                                  //expressão regular de quatro a seis digitos, somente para numeros a espressão pattern
                                  @Pattern(regexp = "\\d{4,6}")
                                  String crm,
                                  //para validar um enum
                                  @NotNull
                                  Especialidade especialidade,
                                  @NotNull
                                  @Valid
                                  // valid e para validar um dto
                                  DadosEndereco endereco) {
}

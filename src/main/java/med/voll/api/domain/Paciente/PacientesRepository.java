package med.voll.api.domain.Paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacientesRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
    @Query("""
            select m.ativo
            from Paciente m 
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);


}
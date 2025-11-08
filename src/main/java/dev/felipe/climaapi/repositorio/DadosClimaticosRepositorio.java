package dev.felipe.climaapi.repositorio;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;


public interface DadosClimaticosRepositorio extends JpaRepository<DadosClimaticos, Long>
{
    List<DadosClimaticos> findByLocalIgnoreCase(String local);

    /**
     * @param local O nome do local, ignorando case (maiúsculas/minúsculas).
     * @param dia   Os registros pesquisados devem conter essa data.
     * @return Um Optional contendo o registro, se encontrado.
     */
    Optional<DadosClimaticos> findByLocalIgnoreCaseAndDia(String local, LocalDate dia);
}

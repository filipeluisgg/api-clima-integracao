package dev.felipe.climaapi.repositorio;

import dev.felipe.climaapi.entidade.DadosClimaticos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DadosClimaticosRepositorio extends JpaRepository<DadosClimaticos, Long>
{
    List<DadosClimaticos> findByLocalIgnoreCase(String local);
}

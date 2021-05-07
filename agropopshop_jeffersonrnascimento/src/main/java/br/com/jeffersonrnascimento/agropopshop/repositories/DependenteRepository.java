package br.com.jeffersonrnascimento.agropopshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import br.com.jeffersonrnascimento.agropopshop.model.Dependente;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {
	List<Dependente> findByIdPrincipal (String id);
}
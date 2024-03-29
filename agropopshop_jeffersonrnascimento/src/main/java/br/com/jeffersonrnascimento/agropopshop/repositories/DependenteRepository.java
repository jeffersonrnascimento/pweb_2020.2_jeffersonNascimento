package br.com.jeffersonrnascimento.agropopshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jeffersonrnascimento.agropopshop.model.Cliente;
import br.com.jeffersonrnascimento.agropopshop.model.Dependente;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {
	List<Dependente> findByCliente(Cliente cliente);


}
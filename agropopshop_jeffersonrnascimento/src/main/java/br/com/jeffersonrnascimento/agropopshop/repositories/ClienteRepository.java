package br.com.jeffersonrnascimento.agropopshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jeffersonrnascimento.agropopshop.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
}

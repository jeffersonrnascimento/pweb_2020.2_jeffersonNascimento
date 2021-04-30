package br.com.jeffersonrnascimento.agropopshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jeffersonrnascimento.agropopshop.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
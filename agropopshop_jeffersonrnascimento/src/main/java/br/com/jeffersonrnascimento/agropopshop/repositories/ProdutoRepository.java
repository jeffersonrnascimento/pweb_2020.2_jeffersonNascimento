package br.com.jeffersonrnascimento.agropopshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.jeffersonrnascimento.agropopshop.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query("select p from Produto p where p.nomeProduto like %?1%")
	List<Produto> buscarProdutoPorNome(String nomeProduto);

}
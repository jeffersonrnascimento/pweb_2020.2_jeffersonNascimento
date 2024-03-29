package br.com.jeffersonrnascimento.agropopshop.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.jeffersonrnascimento.agropopshop.model.Produto;
import br.com.jeffersonrnascimento.agropopshop.repositories.ProdutoRepository;

@Controller
@RequestMapping("/")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepo;
	
	private ModelAndView mva;


	ProdutoController(ProdutoRepository produtoRepo) {
		this.produtoRepo = produtoRepo;
	}
	
	@Autowired
	public ProdutoRepository getProdutoRepo() {
		return produtoRepo;
	}

	@GetMapping("/{listarProdutos}")
	public ModelAndView listarProdutos() {
		List<Produto> lista = produtoRepo.findAll();
		ModelAndView ModelAndView = new ModelAndView("/listarProdutos");
		ModelAndView.addObject("produtos", lista);
		return ModelAndView;
	}

	@GetMapping("/adicionarProduto")
	public ModelAndView adicionarProduto() {
		ModelAndView mav = new ModelAndView("/adicionarProduto");
		mav.addObject(new Produto());
		return mav;
	}

	@PostMapping("/adicionarProduto")
	public String adicionarProduto(Produto produto, @RequestParam("fileProduto") MultipartFile file) {
		
		/*try {
			produto.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		this.produtoRepo.save(produto);
		return "redirect:/listarProdutos";
	}

	@GetMapping("/removerProduto/{id}")
	public ModelAndView removerCliente(@PathVariable("id") long id) {
		Produto aRemover = produtoRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

		produtoRepo.delete(aRemover);
		return new ModelAndView("redirect:/listarProdutos");
	}

	@GetMapping("/editarProduto/{id}")
	public ModelAndView editarProduto(@PathVariable("id") long id) {
		Produto produto = produtoRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

		ModelAndView ModelAndView = new ModelAndView("/editarProduto");
		ModelAndView.addObject(produto);
		return ModelAndView;
	}

	@PostMapping("/editarProduto/{id}")
	public ModelAndView editarProduto(@PathVariable("id") long id, Produto produto, @RequestParam("fileProduto") MultipartFile file) {
		
		try {
			produto.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.produtoRepo.save(produto);
		return new ModelAndView("redirect:/listarProdutos");
	}
	
	@GetMapping("/imagem/{id}")
	@ResponseBody
	//No video foi usado Integer
	public byte[] exibirImagem(@PathVariable("id") Long id ) {
		Produto produto = this.produtoRepo.getOne(id);
		return produto.getImagem();
	}
	
	@PostMapping("**/pesquisarProduto")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {
		mva = new ModelAndView("/listarProdutos");
		mva.addObject("produtos", produtoRepo.buscarProdutoPorNome(nomePesquisa));
		return mva;
	}
}
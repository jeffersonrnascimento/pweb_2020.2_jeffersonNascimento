package br.com.jeffersonrnascimento.agropopshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.jeffersonrnascimento.agropopshop.model.Cliente;
import br.com.jeffersonrnascimento.agropopshop.model.Dependente;
import br.com.jeffersonrnascimento.agropopshop.repositories.ClienteRepository;
import br.com.jeffersonrnascimento.agropopshop.repositories.DependenteRepository;

@Controller
@RequestMapping("/")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private DependenteRepository dependenteRepo;

	ClienteController(ClienteRepository clienteRepo) {
		this.clienteRepo = clienteRepo;
	}

	@GetMapping("/listarClientes")
	public ModelAndView listarClientes() {
		List<Cliente> lista = clienteRepo.findAll();
		ModelAndView mav = new ModelAndView("listarClientes");
		mav.addObject("clientes", lista);
		return mav;
	}

	@GetMapping("/adicionarCliente")
	public ModelAndView formAdicionarCliente() {
		ModelAndView modelAndView = new ModelAndView("adicionarCliente");
		modelAndView.addObject(new Cliente());
		return modelAndView;
	}

	@PostMapping("/adicionarCliente")
	public String adicionarCliente(Cliente aSalvar) {
		this.clienteRepo.save(aSalvar);
		return "redirect:/listarClientes";
	}

	@GetMapping("/editar/{id}")
	public ModelAndView formEditarCliente(@PathVariable("id") long id) {
		Cliente aEditar = clienteRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

		ModelAndView modelAndView = new ModelAndView("editarCliente");
		modelAndView.addObject(aEditar);
		return modelAndView;
	}

	@PostMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") long id, Cliente cliente) {
		this.clienteRepo.save(cliente);
		return new ModelAndView("redirect:/listarClientes");
	}

	@GetMapping("/remover/{id}")
	public ModelAndView removerCliente(@PathVariable("id") long id) {
		Cliente aRemover = clienteRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

		clienteRepo.delete(aRemover);
		return new ModelAndView("redirect:/listarClientes");
	}

	public DependenteRepository getDependenteRepo() {
		return dependenteRepo;
	}

	public void setDependenteRepo(DependenteRepository dependenteRepo) {
		this.dependenteRepo = dependenteRepo;
	}

	@GetMapping("/adicionarDependente/{id}")
	public ModelAndView inserirDependente(@PathVariable("id") long id) {
		Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
		ModelAndView mav = new ModelAndView("/adicionarDependente");
		mav.addObject(new Dependente());
		mav.addObject(cliente);
		return mav;
	}

}

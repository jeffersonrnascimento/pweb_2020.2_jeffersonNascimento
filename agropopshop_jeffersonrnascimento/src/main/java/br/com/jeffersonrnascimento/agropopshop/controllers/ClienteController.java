package br.com.jeffersonrnascimento.agropopshop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	public String index() {
		return "index.html";
	}

	@GetMapping("/listarClientes")
	public ModelAndView listarclientes() {
		List<Cliente> lista = clienteRepo.findAll();
		ModelAndView ModelAndView = new ModelAndView("listarClientes");
		ModelAndView.addObject("clientes", lista);
		return ModelAndView;
	}

	@GetMapping("/adicionarCliente")
	public ModelAndView formAdicionarCliente() {
		ModelAndView mav = new ModelAndView("adicionarCliente");
		mav.addObject(new Cliente());
		return mav;
	}

	@PostMapping("/adicionarCliente")
	public String adicionarCliente(Cliente c) {
		this.clienteRepo.save(c);
		return "redirect:/listarClientes";
	}

	@GetMapping("/remover/{id}")
	public ModelAndView removerCliente(@PathVariable("id") long id) {
		Cliente aRemover = clienteRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

		clienteRepo.delete(aRemover);
		return new ModelAndView("redirect:/listarClientes");
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") long id) {
		Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));

		ModelAndView ModelAndView = new ModelAndView("editarCliente");
		ModelAndView.addObject(cliente);
		return ModelAndView;
	}

	@PostMapping("/editar/{id}")
	public ModelAndView editarCliente(@PathVariable("id") long id, Cliente cliente) {
		this.clienteRepo.save(cliente);
		return new ModelAndView("redirect:detalheCliente/{id}");
	}
	/*
	@GetMapping("/detalheCliente/{id}")
	public ModelAndView detalheCliente(@PathVariable("id") long id) {
		Optional<Dependente> listaDependentes = dependenteRepo.findById(id);
		Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
		ModelAndView mav = new ModelAndView("detalheCliente");
		mav.addObject("dependentes", listaDependentes);
		mav.addObject(cliente);
		return mav;
	} */
	/*
	@GetMapping("/detalheCliente/{id}")
	public ModelAndView detalharCliente(@PathVariable("id") long id) {
		Optional<Cliente> cliente = clienteRepo.findById(id);
		ModelAndView mav = new ModelAndView("detalheCliente");
		mav.addObject("cliente", cliente);
		return mav;

	}*/
	
	@RequestMapping(value="/detalheCliente/{id}", method=RequestMethod.GET)
	public ModelAndView detalheCliente(@PathVariable("id") long id){
		Optional<Cliente> cliente = clienteRepo.findById(id);
		ModelAndView mv = new ModelAndView("detalheCliente");
		if( cliente.isPresent() ) 
			mv.addObject("cliente", cliente.get());
		
		//Esse ID não é do depentente, é do cliente. Os IDs são diferentes
		Optional<Dependente> dependente = dependenteRepo.findById(id);
		if( dependente.isPresent() ) 
			mv.addObject("dependente", dependente.get());

		return mv;
	}

	@GetMapping("/adicionarDependente/{id}")
	public ModelAndView cadastrarDependente(@PathVariable("id") long id) {
		Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
		ModelAndView mav = new ModelAndView("adicionarDependente");
		mav.addObject(new Dependente());
		mav.addObject(cliente);
		return mav;
	}

}
package br.com.jeffersonrnascimento.agropopshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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

	@GetMapping("/remover/{id_cliente}")
	public ModelAndView removerCliente(@PathVariable("id_cliente") long id_cliente) {
		Cliente aRemover = clienteRepo.findById(id_cliente)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id_cliente));

		clienteRepo.delete(aRemover);
		return new ModelAndView("redirect:/listarClientes");
	}

	@GetMapping("/editar/{id_cliente}")
	public ModelAndView editarCliente(@PathVariable("id_cliente") long id_cliente) {
		Cliente cliente = clienteRepo.findById(id_cliente)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id_cliente));

		ModelAndView ModelAndView = new ModelAndView("/editarCliente");
		ModelAndView.addObject(cliente);
		return ModelAndView;
	}

	@PostMapping("/editar/{id_cliente}")
	public ModelAndView editandoCliente(@PathVariable("id_cliente") long id_cliente, Cliente cliente) {
		this.clienteRepo.save(cliente);
		return new ModelAndView("redirect:/detalheCliente/{id_cliente}");
	}

	@RequestMapping(value = "/detalheCliente/{id_cliente}", method = RequestMethod.GET)
	public ModelAndView detalheCliente(@PathVariable("id_cliente") long id_cliente) {
		Cliente cliente = clienteRepo.findById(id_cliente).get();
		ModelAndView mv = new ModelAndView("detalheCliente");
		mv.addObject("cliente", cliente);
		
		//List<Dependente> dependentes = dependenteRepo.findByCliente(cliente);
		//mv.addObject("dependentes", dependentes);
		
		// mv;


		// Esse ID não é do depentente, é do cliente. Os IDs são diferentes
		List<Dependente> dependentes = cliente.getDependentes();
		mv.addObject("dependentes", dependentes);
		return mv;
	}
	// PAREI AQUIIIIIIIIIIIIIIIIIIIIIIIIII

	@RequestMapping(value = "/{detalheCliente/{id_cliente}", method = RequestMethod.POST)
	public String detalhesClientePost(@PathVariable("id_cliente") long id_cliente, @Validated Dependente dependente) {
		Cliente cliente = clienteRepo.findById(id_cliente).get();
		dependente.setCliente(cliente);
		dependenteRepo.save(dependente);
		return "redirect:/{detalheCliente/{id_cliente}";
	}

}
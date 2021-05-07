package br.com.jeffersonrnascimento.agropopshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.jeffersonrnascimento.agropopshop.model.Dependente;
import br.com.jeffersonrnascimento.agropopshop.repositories.DependenteRepository;

@Controller
public class DependenteController {
	DependenteRepository dependenteRepo;
	
	
	@Autowired
	public DependenteRepository getDependenteRepo() {
		return dependenteRepo;
	}

	public void setDependenteRepo(DependenteRepository dependenteRepo) {
		this.dependenteRepo = dependenteRepo;
	}
	
	@GetMapping("/removerDependente/{id}")
	public ModelAndView removerDependente(@PathVariable("id") long id){
		Dependente remover = dependenteRepo.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
		
		dependenteRepo.delete(remover);
		return new ModelAndView("redirect:/listarClientes");
	}
	
	@PostMapping("/adicionarDependente")
	public String adicionarDependente(Dependente d) {
		this.dependenteRepo.save(d);
		return "redirect:/listarClientes";
	}
	
	@GetMapping("/editarDependente/{id}")
	public ModelAndView editarDependente (@PathVariable("id") long id) {
		Dependente dependente = dependenteRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
		
		ModelAndView ModelAndView = new ModelAndView("/editarDependente");
		ModelAndView.addObject(dependente);
		return ModelAndView;
	}
	
	@PostMapping("/editarDependente/{id}")
	public ModelAndView editarPessoa(@PathVariable("id")long id, Dependente dependente) {
		this.dependenteRepo.save(dependente);
		return new ModelAndView( "redirect:/listarClientes");
	}
}
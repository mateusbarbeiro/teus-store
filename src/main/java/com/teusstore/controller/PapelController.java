package com.teusstore.controller;

import com.teusstore.models.Papel;
import com.teusstore.repositories.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PapelController {

	@Autowired
	private PapelRepository papelRepository;

	@GetMapping("/administrativo/papeis/cadastrar")
	public ModelAndView create(Papel papel) {
		ModelAndView mv = new ModelAndView("administrativo/papeis/cadastro");
		mv.addObject("papel", papel);
		return mv;
	}

	@GetMapping("/administrativo/papeis/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/papeis/lista");
		var papeis = papelRepository.findAll();

		mv.addObject("listaPapeis", papeis);
		return mv;
	}

	@PostMapping("/administrativo/papeis/salvar")
	public ModelAndView save(@Validated Papel papel, BindingResult result) {
		if (result.hasErrors()) {
			return create(papel);
		}
		papelRepository.saveAndFlush(papel);
		return create(new Papel());
	}

	@GetMapping("/administrativo/papeis/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Papel> papel = papelRepository.findById(id);
		return create(papel.get());
	}

	@GetMapping("/administrativo/papeis/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Papel> papel = papelRepository.findById(id);
		papelRepository.delete(papel.get());
		return get();
	}
}

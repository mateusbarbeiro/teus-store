package com.teusstore.controller;

import com.teusstore.models.Estado;
import com.teusstore.repositories.EstadoRepository;
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
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping("/administrativo/estados/cadastrar")
	public ModelAndView create(Estado estado) {
		ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
		mv.addObject("estado", estado);
		return mv;
	}

	@GetMapping("/administrativo/estados/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/estados/lista");
		var estados = estadoRepository.findAll();

		mv.addObject("listaEstados", estados);
		return mv;
	}

	@PostMapping("/administrativo/estados/salvar")
	public ModelAndView save(@Validated Estado estado, BindingResult result) {
		if (result.hasErrors()) {
			return create(estado);
		}
		estadoRepository.saveAndFlush(estado);
		return create(new Estado());
	}

	@GetMapping("/administrativo/estados/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		return create(estado.get());
	}

	@GetMapping("/administrativo/estados/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		estadoRepository.delete(estado.get());
		return get();
	}
}

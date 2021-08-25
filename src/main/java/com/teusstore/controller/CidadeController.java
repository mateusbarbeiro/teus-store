package com.teusstore.controller;

import com.teusstore.models.Cidade;
import com.teusstore.repositories.CidadeRepository;
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
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping("/administrativo/cidades/cadastrar")
	public ModelAndView create(Cidade cidade) {
		ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro");
		mv.addObject("cidade", cidade);
		mv.addObject("listaEstados", estadoRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/cidades/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
		var cidades = cidadeRepository.findAll();

		mv.addObject("listaCidades", cidades);
		return mv;
	}

	@PostMapping("/administrativo/cidades/salvar")
	public ModelAndView save(@Validated Cidade cidade, BindingResult result) {
		if (result.hasErrors()) {
			return create(cidade);
		}
		cidadeRepository.saveAndFlush(cidade);
		return create(new Cidade());
	}

	@GetMapping("/administrativo/cidades/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		return create(cidade.get());
	}

	@GetMapping("/administrativo/cidades/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		cidadeRepository.delete(cidade.get());
		return get();
	}
}

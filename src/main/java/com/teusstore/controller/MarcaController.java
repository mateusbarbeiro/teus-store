package com.teusstore.controller;

import com.teusstore.models.Marca;
import com.teusstore.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class MarcaController {

	@Autowired
	private MarcaRepository marcaRepository;

	@GetMapping("/administrativo/marcas/cadastrar")
	public ModelAndView create(Marca marca) {
		ModelAndView mv = new ModelAndView("administrativo/marcas/cadastro");
		mv.addObject("marca", marca);
		return mv;
	}

	@GetMapping("/administrativo/marcas/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/marcas/lista");
		mv.addObject("listaMarcas",  marcaRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/marcas/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Marca> marca = marcaRepository.findById(id);
		return create(marca.get());
	}

	@GetMapping("/administrativo/marcas/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Marca> marca = marcaRepository.findById(id);
		marcaRepository.delete(marca.get());
		return get();
	}
}

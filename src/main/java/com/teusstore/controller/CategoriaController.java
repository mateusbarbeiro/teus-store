package com.teusstore.controller;

import com.teusstore.models.Categoria;
import com.teusstore.repositories.CategoriaRepository;
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
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/administrativo/categorias/cadastrar")
	public ModelAndView create(Categoria categoria) {
		ModelAndView mv = new ModelAndView("administrativo/categorias/cadastro");
		mv.addObject("categoria", categoria);
		return mv;
	}

	@PostMapping("/administrativo/categorias/salvar")
	public ModelAndView save(@Validated Categoria categoria, BindingResult result) {
		if (result.hasErrors()) {
			return create(categoria);
		}
		categoriaRepository.saveAndFlush(categoria);
		return create(new Categoria());
	}

	@GetMapping("/administrativo/categorias/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/categorias/lista");
		mv.addObject("listaCategorias",  categoriaRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/categorias/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return create(categoria.get());
	}

	@GetMapping("/administrativo/categorias/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		categoriaRepository.delete(categoria.get());
		return get();
	}
}

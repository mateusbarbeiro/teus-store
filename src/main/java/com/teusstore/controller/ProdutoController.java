package com.teusstore.controller;

import com.teusstore.models.Produto;
import com.teusstore.repositories.ProdutoRepository;
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
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/administrativo/produtos/cadastrar")
	public ModelAndView create(Produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos",  produtoRepository.findAll());
		return mv;
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView save(@Validated Produto produto, BindingResult result) {
		if (result.hasErrors()) {
			return create(produto);
		}
		produtoRepository.saveAndFlush(produto);
		return create(new Produto());
	}

	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return create(produto.get());
	}

	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		produtoRepository.delete(produto.get());
		return get();
	}
}

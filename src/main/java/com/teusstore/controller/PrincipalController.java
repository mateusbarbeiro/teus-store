package com.teusstore.controller;

import com.teusstore.repositories.CategoriaRepository;
import com.teusstore.repositories.FuncionarioRepository;
import com.teusstore.repositories.MarcaRepository;
import com.teusstore.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@GetMapping("/administrativo")
	public ModelAndView accessMain() {
		ModelAndView mv = new ModelAndView("administrativo/home");
		mv.addObject("qntprodutos", produtoRepository.count());
		mv.addObject("qntfuncionarios", funcionarioRepository.count());
		mv.addObject("qntmarcas", marcaRepository.count());
		mv.addObject("qntcategorias", categoriaRepository.count());
		return mv;
	}
}

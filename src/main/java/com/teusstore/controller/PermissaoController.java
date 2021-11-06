package com.teusstore.controller;

import com.teusstore.models.Permissao;
import com.teusstore.models.Permissao;
import com.teusstore.repositories.*;
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
public class PermissaoController {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PapelRepository papelRepository;

	@GetMapping("/administrativo/permissoes/cadastrar")
	public ModelAndView create(Permissao permissao) {
		ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
		mv.addObject("permissao", permissao);
		mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		mv.addObject("listaPapeis", papelRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/permissoes/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
		var permissoes = permissaoRepository.findAll();

		mv.addObject("listaPermissoes", permissoes);
		return mv;
	}

	@PostMapping("/administrativo/permissoes/salvar")
	public ModelAndView save(@Validated Permissao permissao, BindingResult result) {
		if (result.hasErrors()) {
			return create(permissao);
		}
		permissaoRepository.saveAndFlush(permissao);
		return create(new Permissao());
	}

	@GetMapping("/administrativo/permissoes/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		return create(permissao.get());
	}

	@GetMapping("/administrativo/permissoes/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		permissaoRepository.delete(permissao.get());
		return get();
	}
}

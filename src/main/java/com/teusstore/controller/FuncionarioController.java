package com.teusstore.controller;

import br.com.caelum.stella.validation.CPFValidator;
import com.teusstore.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.teusstore.models.Funcionario;
import com.teusstore.repositories.FuncionarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	private List<String> msg = new ArrayList<String>();

	@GetMapping("/administrativo/funcionarios/cadastrar")
	public ModelAndView create(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("funcionario", funcionario);
		mv.addObject("listaCidades", cidadeRepository.findAll());
		mv.addObject("msg", msg);
		msg = new ArrayList<String>();
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		return mv;
	}
	
	@PostMapping("/administrativo/funcionarios/salvar")
	public ModelAndView save(@Validated Funcionario funcionario, BindingResult result) {
		if (result.hasErrors()) {
			for(ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}

			return create(funcionario);
		}
		if (!validateCpf(funcionario.getCpf())) {
			msg.add("CPF inválido.");
			return create(funcionario);
		}

		funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
		funcionarioRepository.saveAndFlush(funcionario);
		return create(new Funcionario());
	}

	@GetMapping("/administrativo/funcionarios/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		return create(funcionario.get());
	}

	@GetMapping("/administrativo/funcionarios/remover/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		funcionarioRepository.delete(funcionario.get());
		return get();
	}

	private boolean validateCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(cpf);
			return  true;
		} catch (Exception e) {
			return false;
		}
	}

}

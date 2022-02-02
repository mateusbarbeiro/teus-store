package com.teusstore.controller;

import com.teusstore.models.Cliente;
import com.teusstore.repositories.CidadeRepository;
import com.teusstore.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@GetMapping("/cliente/cadastrar")
	public ModelAndView create(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/cadastrar");
		mv.addObject("cliente", cliente);
		mv.addObject("listaCidades", cidadeRepository.findAll());
		return mv;
	}

	@PostMapping("/cliente/salvar")
	public ModelAndView save(@Validated Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return create(cliente);
		}
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		clienteRepository.saveAndFlush(cliente);
		return create(new Cliente());
	}

	@GetMapping("/cliente/editar/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return create(cliente.get());
	}
}

package com.teusstore.controller;

import com.teusstore.models.Funcionario;
import com.teusstore.repositories.FuncionarioRepository;
import com.teusstore.service.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmailService emailService;

	List<String> msg = new ArrayList<String>();

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/login");
		return  mv;
	}

	@GetMapping("/resetpassword")
	public ModelAndView resetpassword() {
		ModelAndView mv = new ModelAndView("/reset-password");
		mv.addObject("msg", msg);
		msg = new ArrayList<String>();
		return  mv;
	}

	@PostMapping("/resetpassword")
	public ModelAndView resetpassword(String username) {
		Funcionario funcionario = funcionarioRepository.findFuncionarioByEmail(username);

		if (funcionario == null) {
			msg.add("Usuário não encontrado.");
			return resetpassword();
		}

		String characters = "0123456789ABCDEFG";
		String pwd = RandomStringUtils.random( 10, characters );
		funcionario.setSenha(new BCryptPasswordEncoder().encode(pwd));

		boolean resultSendEmail = emailService.sendEmail(funcionario.getEmail(), "Novas credenciais de acesso do painel administrativo da loja online",
				"Olá,"+ funcionario.getNome() +"\n\nPara acessar o painel adm, entre com: \nEmail: " + funcionario.getEmail() + "\nSenha: " + pwd +
						"\n\nObrigado!");

		if (!resultSendEmail) {
			msg.add("Ocorreu um erro no envio do E-mail de credenciais!");
		}

		return resetpassword();
	}
}

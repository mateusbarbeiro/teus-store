package com.teusstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class NegadoController {

	@GetMapping("/negado")
	public ModelAndView deny() {
		ModelAndView mv = new ModelAndView("/negado");
		return  mv;
	}
}

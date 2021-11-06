package com.teusstore.controller;

import com.teusstore.models.Produto;
import com.teusstore.repositories.CategoriaRepository;
import com.teusstore.repositories.MarcaRepository;
import com.teusstore.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProdutoController {

	private static String imagesPath = "D:/Projects/Faculdade/web-workspace/Teus-Store-Images/";
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/administrativo/produtos/cadastrar")
	public ModelAndView create(Produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		mv.addObject("listaMarcas", marcaRepository.findAll());
		mv.addObject("listaCategorias", categoriaRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos",  produtoRepository.findAll());
		return mv;
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView save(@Validated Produto produto, BindingResult result, @RequestParam("file")MultipartFile file) {
		if (result.hasErrors()) {
			return create(produto);
		}

		produtoRepository.saveAndFlush(produto);

		try {
			if(!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(imagesPath + String.valueOf(produto.getId())+ file.getOriginalFilename());
				Files.write(path, bytes);

				produto.setNomeImagem(String.valueOf(produto.getId())+ file.getOriginalFilename());
				produtoRepository.saveAndFlush(produto);
			}
		} catch	(IOException e) {
			e.printStackTrace();
		}

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

	@GetMapping("/administrativo/produtos/mostraImagem/{image}")
	@ResponseBody
	public byte[] getImage(@PathVariable("image") String image) throws IOException {
		File imageFile = new File(imagesPath + image);
		if (imageFile != null || image.trim().length() > 0) {
				return Files.readAllBytes(imageFile.toPath());
		}
		return null;
	}
}

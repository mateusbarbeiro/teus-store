package com.teusstore.controller;

import com.teusstore.models.Categoria;
import com.teusstore.models.ImagensProduto;
import com.teusstore.models.Marca;
import com.teusstore.models.Produto;
import com.teusstore.repositories.CategoriaRepository;
import com.teusstore.repositories.ImagensProdutoRepository;
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
	@Autowired
	private ImagensProdutoRepository imagensProdutoRepository;

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
		mv.addObject("listaMarcas", marcaRepository.findAll());
		mv.addObject("listaCategorias", categoriaRepository.findAll());

		return mv;
	}

	@GetMapping("/administrativo/produtos/listar/descricao")
	public ModelAndView getByDescription(String descricao) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos",  produtoRepository.findAllByDescricaoContains(descricao));
		mv.addObject("listaMarcas", marcaRepository.findAll());
		mv.addObject("listaCategorias", categoriaRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar/marca")
	public ModelAndView getByBrand(Long marcaId) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		Marca marca = marcaRepository.getById(marcaId);
		mv.addObject("listaProdutos",  produtoRepository.findAllByMarca(marca));
		mv.addObject("listaMarcas", marcaRepository.findAll());
		mv.addObject("listaCategorias", categoriaRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar/categoria")
	public ModelAndView getByCategory(Long categoriaId) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		Categoria categoria = categoriaRepository.getById(categoriaId);
		mv.addObject("listaProdutos",  produtoRepository.findAllByCategoria(categoria));
		mv.addObject("listaMarcas", marcaRepository.findAll());
		mv.addObject("listaCategorias", categoriaRepository.findAll());
		return mv;
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView save(@Validated Produto produto, BindingResult result, @RequestParam("files")MultipartFile[] files) {
		if (result.hasErrors()) {
			return create(produto);
		}

		produtoRepository.saveAndFlush(produto);

		for (MultipartFile file: files) {
			try {
				if(!file.isEmpty()) {
					byte[] bytes = file.getBytes();
					String imageName = String.valueOf(produto.getId())+ file.getOriginalFilename();
					Path path = Paths.get(imagesPath + imageName);

					Files.write(path, bytes);

					ImagensProduto image = new ImagensProduto();
					image.setNomeImagem(imageName);
					image.setProduto(produto);
					imagensProdutoRepository.saveAndFlush(image);
				}
			} catch	(IOException e) {
				e.printStackTrace();
			}
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

	@GetMapping("/administrativo/produtos/inlot50k")
	public ModelAndView inLot50k() {
		for(int i = 0; i < 50000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoRepository.saveAndFlush(produto);
		}
		return new ModelAndView("administrativo/home");
	}

	@GetMapping("/administrativo/produtos/inlot100k")
	public ModelAndView inLot100k() {
		for(int i = 0; i < 100000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoRepository.saveAndFlush(produto);
		}
		return new ModelAndView("administrativo/home");
	}

	@GetMapping("/administrativo/produtos/inlot150k")
	public ModelAndView inLot150k() {
		for(int i = 0; i < 150000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoRepository.saveAndFlush(produto);
		}
		return new ModelAndView("administrativo/home");
	}

	@GetMapping("/administrativo/produtos/inlot200k")
	public ModelAndView inLot200k() {
		for(int i = 0; i < 200000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoRepository.saveAndFlush(produto);
		}
		return new ModelAndView("administrativo/home");
	}
}

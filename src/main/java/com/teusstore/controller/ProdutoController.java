package com.teusstore.controller;

import com.teusstore.models.ImagensProduto;
import com.teusstore.models.Produto;
import com.teusstore.models.dtos.Paged;
import com.teusstore.models.dtos.Paging;
import com.teusstore.repositories.CategoriaRepository;
import com.teusstore.repositories.ImagensProdutoRepository;
import com.teusstore.repositories.MarcaRepository;
import com.teusstore.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
		return get("", 1, 10);
	}

	@GetMapping("/administrativo/produtos/listar/paged")
	public ModelAndView get(@RequestParam(value = "filtro", required = false, defaultValue = "") String filtro, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
						@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		ModelAndView mv = new ModelAndView("administrativo/produtos/listapaginada");

		Pageable pageable = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
		Page<Produto> postPage = produtoRepository.findAll(filtro, pageable);

		mv.addObject("listaprodutos", new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size)));
		mv.addObject("listaMarcas", marcaRepository.findAll());
		mv.addObject("listaCategorias", categoriaRepository.findAll());
		return mv;
	}


	@GetMapping("/administrativo/produtos/inlot50k")
	public ModelAndView inLot50k() {
		List<Produto> produtoList = new ArrayList<Produto>();
		for(int i = 0; i < 50000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoList.add(produto);
		}

		produtoRepository.saveAll(produtoList);
		return new ModelAndView("administrativo/home");
	}

	@GetMapping("/administrativo/produtos/inlot100k")
	public ModelAndView inLot100k() {
		List<Produto> produtoList = new ArrayList<Produto>();
		for(int i = 0; i < 100000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoList.add(produto);
		}

		produtoRepository.saveAll(produtoList);
		return new ModelAndView("administrativo/home");
	}

	@GetMapping("/administrativo/produtos/inlot150k")
	public ModelAndView inLot150k() {
		List<Produto> produtoList = new ArrayList<Produto>();
		for(int i = 0; i < 150000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoList.add(produto);
		}

		produtoRepository.saveAll(produtoList);
		return new ModelAndView("administrativo/home");
	}

	@GetMapping("/administrativo/produtos/inlot200k")
	public ModelAndView inLot200k() {
		List<Produto> produtoList = new ArrayList<Produto>();
		for(int i = 0; i < 200000; i++) {
			Produto produto = new Produto();
			produto.setDescricao("Product" + i);
			produto.setValorVenda(i + 0.0);

			produtoList.add(produto);
		}


		produtoRepository.saveAll(produtoList);
		return new ModelAndView("administrativo/home");
	}
}

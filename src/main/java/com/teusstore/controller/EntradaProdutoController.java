package com.teusstore.controller;

import com.teusstore.models.EntradaItens;
import com.teusstore.models.EntradaProduto;
import com.teusstore.models.Produto;
import com.teusstore.repositories.EntradaItensRepository;
import com.teusstore.repositories.EntradaProdutoRepository;
import com.teusstore.repositories.FuncionarioRepository;
import com.teusstore.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaProdutoController {

	private  List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

	@Autowired
	private EntradaProdutoRepository entradaProdutoRepository;

	@Autowired
	private EntradaItensRepository entradaItensRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ProdutoRepository produtosRepository;

	@GetMapping("/administrativo/entradas/cadastrar")
	public ModelAndView create(EntradaProduto entrada, EntradaItens entradaItens) {
		ModelAndView mv = new ModelAndView("administrativo/entradas/cadastro");
		mv.addObject("entrada", entrada);
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		mv.addObject("listaProdutos", produtosRepository.findAll());
		mv.addObject("valorTotal", calculateTotalValue());
		return mv;
	}

//	@GetMapping("/administrativo/entradas/listar")
//	public ModelAndView get() {
//		ModelAndView mv = new ModelAndView("administrativo/entradas/lista");
//		var entradas = entradaProdutoRepository.findAll();
//
//		mv.addObject("listaEstados", entradas);
//		return mv;
//	}

	@PostMapping("/administrativo/entradas/salvar")
	public ModelAndView save(String acao, EntradaProduto entrada, EntradaItens entradaItens) {
		if (acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		} else if (acao.equals("salvar")) {
			entradaProdutoRepository.saveAndFlush(entrada);
			for(EntradaItens it:listaEntrada) {
				it.setEntradaProduto(entrada);
				entradaItensRepository.saveAndFlush(it);
				Optional<Produto> prod = produtosRepository.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				produtosRepository.saveAndFlush(produto);
			}
			this.listaEntrada = new ArrayList<>();
			return create(new EntradaProduto(), new EntradaItens());
		}

		System.out.println(listaEntrada.size());

		return create(entrada, new EntradaItens());
	}

//	@GetMapping("/administrativo/entradas/editar/{id}")
//	public ModelAndView update(@PathVariable("id") Long id) {
//		Optional<Estado> entrada = entradaProdutoRepository.findById(id);
//		return create(entrada.get());
//	}

//	@GetMapping("/administrativo/entradas/remover/{id}")
//	public ModelAndView delete(@PathVariable("id") Long id) {
//		Optional<Estado> entrada = entradaProdutoRepository.findById(id);
//		entradaProdutoRepository.delete(entrada.get());
//		return get();
//	}
	private Double calculateTotalValue() {
		Double soma = 0.;
		for (EntradaItens item: listaEntrada) {
			soma += item.getValorVenda() * item.getQuantidade();
		}

		return soma;
	}
}

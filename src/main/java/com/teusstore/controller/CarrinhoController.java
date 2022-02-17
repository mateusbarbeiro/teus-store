package com.teusstore.controller;

import com.teusstore.models.Cliente;
import com.teusstore.models.Compra;
import com.teusstore.models.ItensCompra;
import com.teusstore.models.Produto;
import com.teusstore.repositories.ClienteRepository;
import com.teusstore.repositories.CompraRepository;
import com.teusstore.repositories.ItensCompraRepository;
import com.teusstore.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarrinhoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ItensCompraRepository itensCompraRepository;

    private List<ItensCompra> itensCompraLista = new ArrayList<ItensCompra>();

    private Compra compra = new Compra();

    private Cliente cliente;

    private void calcularTotal() {
        Double total = 0.;
        for (ItensCompra it: itensCompraLista) {
            total += it.getValorTotal();
        }
        compra.setValorTotal(total);
    }

    @GetMapping("/carrinho")
    public ModelAndView chamarCarrinho() {
        ModelAndView mv = new ModelAndView("cliente/carrinho");
        calcularTotal();

        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompraLista);
        return  mv;
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
       for (ItensCompra it: itensCompraLista) {
            if (it.getProduto().getId().equals(id)) {
                if (acao.equals(1)) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                } else if (acao.equals(0) && !it.getQuantidade().equals(1)) {
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                }
                break;
            }
       }

       return "redirect:/carrinho";
    }

    @GetMapping("/removerProduto/{id}")
    public String removerProduto(@PathVariable Long id) {
        for (ItensCompra it: itensCompraLista) {
            if (it.getProduto().getId().equals(id)) {
                itensCompraLista.remove(it);
                break;
            }
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/adicionarCarrinho/{id}")
    public String adicionarCarrinho(@PathVariable Long id) {
        Optional<Produto> prod = produtoRepository.findById(id);
        Produto produto = prod.get();

        int controle = 0;
        for (ItensCompra it: itensCompraLista) {
            if(it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(it.getQuantidade() * it.getValorUnitario());
                controle = 1;
                break;
            }
        }

        if (controle == 0) {
            ItensCompra item = new ItensCompra();
            item.setProduto(produto);
            item.setValorUnitario(produto.getValorVenda());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getQuantidade() * item.getValorUnitario());

            itensCompraLista.add(item);
        }

        return "redirect:/carrinho";
    }

    private void buscarUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            cliente = clienteRepository.buscarClienteEmail(email).get(0);
        }

    }

    @GetMapping("/finalizar")
    public ModelAndView finalizarCompra() {
        ModelAndView mv = new ModelAndView("cliente/finalizar");
        buscarUsuarioLogado();
        calcularTotal();

        mv.addObject("compra", compra);
        mv.addObject("listaItens", itensCompraLista);
        mv.addObject("cliente", cliente);
        return  mv;
    }

    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaPagamento) {
        ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
        compra.setCliente(cliente);
        compra.setFormaPagamento(formaPagamento);
        compraRepository.saveAndFlush(compra);

        for (ItensCompra item : itensCompraLista) {
            item.setCompra(compra);
            itensCompraRepository.saveAndFlush(item);
        }

        itensCompraLista = new ArrayList<>();
        compra = new Compra();
        return  mv;
    }
}

package com.teusstore.controller;

import com.teusstore.models.ImagensProduto;
import com.teusstore.models.Produto;
import com.teusstore.repositories.ImagensProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class ImagemController {

    private static String imagesPath = "D:/Projects/Faculdade/web-workspace/Teus-Store-Images/";
    @Autowired
    private ImagensProdutoRepository imagensProdutoRepository;

    @GetMapping("/mostraImagem/{productid}")
    @ResponseBody
    public byte[] getImage(@PathVariable("productid") Long productid) throws IOException {
        Produto produto = new Produto();
        produto.setId(productid);
        List<ImagensProduto> imagens = imagensProdutoRepository.findImagensProdutoByProduto(produto);
        if (imagens.size() > 0) {
            File imageFile = new File(imagesPath + imagens.get(0).getNomeImagem());
            if (imageFile != null || imagens.get(0).getNomeImagem().trim().length() > 0) {
                return Files.readAllBytes(imageFile.toPath());
            }
            return null;
        }
        return null;
    }
}

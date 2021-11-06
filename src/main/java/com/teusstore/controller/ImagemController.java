package com.teusstore.controller;

import com.teusstore.models.Produto;
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
public class ImagemController {

    private static String imagesPath = "D:/Projects/Faculdade/web-workspace/Teus-Store-Images/";

    @GetMapping("/mostraImagem/{image}")
    @ResponseBody
    public byte[] getImage(@PathVariable("image") String image) throws IOException {
        File imageFile = new File(imagesPath + image);
        if (imageFile != null || image.trim().length() > 0) {
            return Files.readAllBytes(imageFile.toPath());
        }
        return null;
    }
}

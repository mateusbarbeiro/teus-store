package com.teusstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

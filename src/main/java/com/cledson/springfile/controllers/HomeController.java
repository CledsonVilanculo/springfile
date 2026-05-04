package com.cledson.springfile.controllers;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cledson.springfile.FileManager;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    // onde o ficheior e carregado
    @PostMapping("/upload")                     // o ficheiro no body do fetch
    public ResponseEntity<Boolean> fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean foiSalvo = FileManager.saveOnDisk(file);
        if (foiSalvo) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    // para obter a lista dos ficheiros disponiveis
    @RequestMapping("/getallfiles")
    public ResponseEntity<ArrayList<String>> getAllFiles() {
        return ResponseEntity.ok(FileManager.getAllFiles());
    }

    // para obter o tamanho dos arquivos
    @RequestMapping("/getfilessize")
    public ResponseEntity<ArrayList<Long>> getFilesSize() {
        return ResponseEntity.ok(FileManager.getFilesSize());
    }
}
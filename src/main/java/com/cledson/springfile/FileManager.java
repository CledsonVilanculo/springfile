package com.cledson.springfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {
    private static ArrayList<String> allFiles = new ArrayList<>();
    private static ArrayList<Long> filesSize = new ArrayList<>();

    public static boolean saveOnDisk(MultipartFile file) {
        try {
            new File("src/main/resources/static/files").mkdir(); // cria a pasta, caso ela nao exista
            String diretorioDestino = "src/main/resources/static/files/";
            String nomeArquivo = file.getOriginalFilename();
            Path path = Paths.get(diretorioDestino + nomeArquivo);
            
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            allFiles.add(nomeArquivo); // guarda o nome do arquivo
            filesSize.add(file.getSize()); // guarda o tamanho
            return true;
        } catch (IOException a) {
            System.out.println(a);
            return false;
        }
    }

    public static ArrayList<String> getAllFiles() {
        return allFiles;
    }

    public static ArrayList<Long> getFilesSize() {
        return filesSize;
    }
}
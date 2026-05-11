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

    /**
     * Funcao que guarda o ficheiro recebido pelo front-end no disco local
     * @param file o ficheiro no body da request em <code>MultipartFile</code>
     * @return <code>false</code> se o ficheiro for vazio ou se der <code>IOException</code> ao guardar o mesmo. E retorna <code>true</code> caso o ficheiro for guardado com sucesso
     */
    public static boolean saveOnDisk(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }

        try {
            new File("C:/springfile/files/").mkdirs(); // cria a pasta, caso ela nao exista
            String diretorioDestino = "C:/springfile/files/";
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

    /**
     * @return a lista de todos os nomes dos ficheiros em um <code>ArrayList</code>
     */
    public static ArrayList<String> getAllFiles() {
        return allFiles;
    }

    /**
     * 
     * @return a lista dos tamanhos dos ficheiros em um <code>ArrayList</code>
     */
    public static ArrayList<Long> getFilesSize() {
        return filesSize;
    }

    /**
    * Apaga os ficheiros guardados pelo servidor do disco local, para evitar que o computador 
    * que está hospedando fique cheio de ficheiros duplicados
    */
    public static void deleteTempFiles() {
        for (String file : allFiles) {
            new File("C:/springfile/files/" + file).delete();
        }
    }
}
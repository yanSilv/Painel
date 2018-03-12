/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.controler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaguar
 */
public class CtlManipulaArquivo {

    /*  Metodo que retorna um array list do tipo String*/
    public ArrayList<String> lerArquivo(String nameArquivo) throws IOException {
        ArrayList<String> lerDados = new ArrayList<String>();

        File file = new File(nameArquivo);

        if (file.exists()) {
            BufferedReader buffeRead = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            while (buffeRead.ready()) {
                lerDados.add(buffeRead.readLine());
            }

            buffeRead.close();
        } else {
            System.out.println("Arquivo " + file.getAbsoluteFile() + " não existe");
        }

        return lerDados;
    }

    public int gravaArquivo(File nomeArquivo, String conteudo, int status) {

        int ret = 0;

        ret = status;
        Path path = Paths.get(nomeArquivo.getAbsolutePath());
        Charset utf8 = StandardCharsets.UTF_8;
        ArrayList<String> linhasArq = new ArrayList<String>();

        if (!nomeArquivo.exists()) {
            status = 1;
        }


        /*
        	Deleta o arquivo
        	Cria um novo arquivo
         */
        if (status == 1) {
            ret = status;
            nomeArquivo.delete();

            try {
                nomeArquivo.createNewFile();
                status = 2;
            } catch (IOException ex) {
                Logger.getLogger(CtlManipulaArquivo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


        /*
        	Escreve no arquivo
         */
        if (status == 2) {

            try (BufferedReader readl = Files.newBufferedReader(path, utf8)) {
                while (readl.ready()) {
                    linhasArq.add(readl.readLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            linhasArq.add(conteudo);

            try (BufferedWriter write = Files.newBufferedWriter(path, utf8)) {
                for (String string : linhasArq) {
                    write.write(string);
                    write.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        	Escreve no arquivo
         */
        if (status == 3) {
            nomeArquivo.delete();
            ret = status;
        }

        return ret;
    }
    
    public boolean criaDiretorio(String endereco){
        
        String nomeDiretorio = null;
        
        try {
            nomeDiretorio = endereco;
            
            if(!new File(nomeDiretorio).exists()) {
                new File(nomeDiretorio).mkdir();
                System.out.println("Diretorio criado com sucesso!!");
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao criar o diretorio do banco");
        }
        return false;
    }
    
    public String enderecoArquivo (String path) {
        String endereco = "";
        String[] endAux = path.split("/");
        
        for(int i = 0; i < endAux.length - 1; i++){
            endereco = endereco.concat("/"+endAux[i]);
        }
        
        return endereco;
    }
    
    public Properties getProp() {
        Properties pros = new Properties();
        
        try {
            FileInputStream file = new FileInputStream("src/Configuracao/configuracao.properties");
            pros.load(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CtlManipulaArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CtlManipulaArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pros;
    }

}

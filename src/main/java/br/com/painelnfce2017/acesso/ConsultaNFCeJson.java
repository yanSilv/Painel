/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.acesso;

import br.com.painelnfce2017.controler.ArquivoProperties;
import br.com.painelnfce2017.controler.CtlConsultaNFCe;
import br.com.painelnfce2017.model.Configuracao;
import br.com.painelnfce2017.model.ModCupom;
import br.com.painelnfce2017.model.ModEstruturaNFCE;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author programador
 */
@Path("/consultachave")
public class ConsultaNFCeJson {
    
    /*@GET
    public ArrayList<ModCupom> consultaNFCe() {
        ArrayList<ModCupom> arrayLis = new ArrayList<ModCupom>();
        ModCupom modCup = new ModCupom();
        modCup.setCpCoo("002233");
        modCup.setCpCaixa("01");
        modCup.setCpFilial("001");
        modCup.setCpData("20170518");
        
        arrayLis.add(modCup);
        
        return arrayLis;
    }*/
    
    /*@GET
    public String retornoNfce (@Context UriInfo info) {
        String nome = null;
        nome = info.getQueryParameters().getFirst("nome");
        return nome;
    }*/
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ModEstruturaNFCE consultaChaveNFce (@Context UriInfo info) {
        
        String chave = null;
        String bancoDb;
        ModEstruturaNFCE arrayRet;
        Configuracao configuracao = new Configuracao();
        CtlConsultaNFCe ctlConsu = new CtlConsultaNFCe();
        ArquivoProperties conf = new ArquivoProperties();
        
        try {
            configuracao = conf.configInfo();
        } catch (IOException ex) {
            Logger.getLogger(ConsultaNFCeJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        chave = info.getQueryParameters().getFirst("chave");
        
        bancoDb = configuracao.getDirBanco();
        
        //Consulta do banco Chave NFCe.
        arrayRet = ctlConsu.consulChaveNFCe(bancoDb, chave);
        
        return arrayRet;
    }
    
}

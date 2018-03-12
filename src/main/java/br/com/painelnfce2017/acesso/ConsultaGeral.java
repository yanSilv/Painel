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
import br.com.painelnfce2017.model.ModPagamento;
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
@Path("/consultageral")
public class ConsultaGeral {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<ModEstruturaNFCE> consultaData(@Context UriInfo info) {
        String data = null;
        String validos = null;
        String contigencia = null;
        
        String bancoDb;
        ArrayList<ModEstruturaNFCE> arrayRet;
        
        Configuracao configuracao= new Configuracao();
        CtlConsultaNFCe ctlConsu = new CtlConsultaNFCe();
        ArquivoProperties conf   = new ArquivoProperties();
        
        try {
            configuracao = conf.configInfo();
        } catch (IOException ex) {
            Logger.getLogger(ConsultaNFCeJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        data = info.getQueryParameters().getFirst("data");
        validos = info.getQueryParameters().getFirst("validos");
        contigencia = info.getQueryParameters().getFirst("contigencia");
        
        System.out.println(data+" - "+validos+" - "+contigencia+" - ");
        bancoDb = configuracao.getDirBanco();
        
        arrayRet = ctlConsu.consulDataGeral(bancoDb, data, validos, contigencia);
        
        return arrayRet;
    }
}

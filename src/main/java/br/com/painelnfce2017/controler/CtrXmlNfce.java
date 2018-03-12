/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.controler;

import br.com.painelnfce2017.model.Configuracao;
import br.com.painelnfce2017.model.ModCupom;
import br.com.painelnfce2017.model.ModEstruturaNFCE;
import br.com.painelnfce2017.model.ModStatusServ;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import br.com.painelnfce2017.util.Utils;

/**
 *
 * @author programador
 */
public class CtrXmlNfce {

    public String xmlStatus(Configuracao conf) {
        String versao = conf.getVersao();
        String ambiente = conf.getAmbiente();
        String codUf = conf.getCodUf();
        /**
         * Xml de Status.
         */
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<consStatServ versao=\"" + versao + "\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
                .append("<tpAmb>" + ambiente + "</tpAmb>")
                .append("<cUF>" + codUf + "</cUF>")
                .append("<xServ>STATUS</xServ>")
                .append("</consStatServ>");

        return xml.toString();
    }

    public String xmlConsulta(Configuracao conf, String chaveNFCe) {
        String versao = conf.getVersao();
        String ambiente = conf.getAmbiente();
        String codUf = conf.getCodUf();
        /**
         * Xml de Consulta.
         */
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
           .append("<consSitNFe versao=\""+versao+"\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
           .append("<tpAmb>"+ambiente+"</tpAmb >")
           .append("<xServ>CONSULTAR</xServ >")
           .append("<chNFe>"+chaveNFCe+"</chNFe>")
           .append("</consSitNFe>");
        
        
        return xml.toString();
    }

    public boolean xmlAnaliseRetornoStatus(String camposXml) {
        boolean status = false;

        String cStat = null;
        String xMotivo = null;

        Document doc = Utils.parseXml(camposXml);
        Element root = doc.getDocumentElement();

        NodeList nRet = root.getElementsByTagName("tpAmb");
        NodeList nVerAp = root.getElementsByTagName("verAplic");
        NodeList nCStat = root.getElementsByTagName("cStat");
        NodeList nXMotivo = root.getElementsByTagName("xMotivo");
        NodeList nCUF = root.getElementsByTagName("cUF");
        NodeList nDhRec = root.getElementsByTagName("dhRecbto");
        NodeList nTMed = root.getElementsByTagName("tMed");

        if (nCStat.getLength() > 0) {
            cStat = nCStat.item(0).getTextContent();
        }

        if (nXMotivo.getLength() > 0) {
            xMotivo = nXMotivo.item(0).getTextContent();
        }

        if (cStat.equals("107")) {
            status = true;
        }

        return status;
    }
    
    public ModEstruturaNFCE xmlAnaliseRetornoConsulta(String camposXml, ModEstruturaNFCE modEstru) {
        boolean status = false;
        
        String cStat = "";
        String chNFe = "";
        String dhRecbto = "";
        String nProt = "";
        String xMotivo = "";
        
        ModCupom modCupom = new ModCupom();
        
        Document doc = Utils.parseXml(camposXml);
        Element root = doc.getDocumentElement();
        
        NodeList nRet = root.getElementsByTagName("tpAmb");
        NodeList nVerAp = root.getElementsByTagName("verAplic");
        NodeList nCStat = root.getElementsByTagName("cStat");
        NodeList nChNFe = root.getElementsByTagName("chNFe");
        NodeList nDhRecbto = root.getElementsByTagName("dhRecbto");
        NodeList nNProt = root.getElementsByTagName("nProt");
        NodeList nXMotivo = root.getElementsByTagName("xMotivo");
        
        
        for(int i = 0; i < nCStat.getLength(); i++) cStat = nCStat.item(i).getTextContent();
        for(int i = 0; i < nChNFe.getLength(); i++) chNFe = nChNFe.item(i).getTextContent();
        for(int i = 0; i < nDhRecbto.getLength(); i++) dhRecbto = nDhRecbto.item(i).getTextContent();
        for(int i = 0; i < nNProt.getLength(); i++) nProt = nNProt.item(i).getTextContent();
        for(int i = 0; i < nXMotivo.getLength(); i++) xMotivo = nXMotivo.item(i).getTextContent();
        
        if (cStat.equals("100")) {
            modCupom = modEstru.getModCup();
            modCupom.setCpAutorizacao(nProt);
            modCupom.setCpMotivo(xMotivo);
            modCupom.setStatus(1);
            modEstru.setModCup(modCupom);
        } else {
            System.out.println("Retorno XML "+ camposXml);
            modCupom = modEstru.getModCup();
            modCupom.setCpAutorizacao(nProt);
            modCupom.setCpMotivo(xMotivo);
            modCupom.setStatus(0);
            modEstru.setModCup(modCupom);
        }
        
        return modEstru;
    }
    
    
    /**
     * Metodo utilizado no desenvolvimento do programa.
     */
    public void xmlAnaliseRetornoConsulta(String camposXml) {
        boolean status = false;
        
        String cStat = null;
        String chNFe = null;
        String dhRecbto = null;
        String nProt = null;
        String xMotivo = null;
        
        Document doc = Utils.parseXml(camposXml);
        Element root = doc.getDocumentElement();
        
        NodeList nRet = root.getElementsByTagName("tpAmb");
        NodeList nVerAp = root.getElementsByTagName("verAplic");
        NodeList nCStat = root.getElementsByTagName("cStat");
        NodeList nChNFe = root.getElementsByTagName("chNFe");
        NodeList nDhRecbto = root.getElementsByTagName("dhRecbto");
        NodeList nNProt = root.getElementsByTagName("nProt");
        NodeList nXMotivo = root.getElementsByTagName("xMotivo");
        
        
        for(int i = 0; i < nCStat.getLength(); i++) cStat = nCStat.item(i).getTextContent();
        for(int i = 0; i < nChNFe.getLength(); i++) chNFe = nChNFe.item(i).getTextContent();
        for(int i = 0; i < nDhRecbto.getLength(); i++) dhRecbto = nDhRecbto.item(i).getTextContent();
        for(int i = 0; i < nNProt.getLength(); i++) nProt = nNProt.item(i).getTextContent();
        for(int i = 0; i < nXMotivo.getLength(); i++) xMotivo = nXMotivo.item(i).getTextContent();
        
        if (cStat.equals("100")) {
            System.out.println("Chave            -- "+chNFe);
            System.out.println("Data Recebimento -- "+dhRecbto);
            System.out.println("Numero Protocolo -- "+nProt);
            System.out.println("Motivo           -- "+ xMotivo);
        } else {
            System.out.println("Retorno XML "+ camposXml);
        }
    }
}

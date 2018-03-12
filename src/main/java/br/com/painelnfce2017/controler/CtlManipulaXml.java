/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.controler;

import br.com.painelnfce2017.model.ModCupom;
import br.com.painelnfce2017.model.ModEstruturaNFCE;
import br.com.painelnfce2017.model.ModItem;
import br.com.painelnfce2017.model.ModPagamento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import br.com.painelnfce2017.util.Utils;

/**
 *
 * @author programador
 */
public class CtlManipulaXml {

    CtlManipulaArquivo ctlArq;
    ModEstruturaNFCE modEst;

    public CtlManipulaXml() {
        ctlArq = new CtlManipulaArquivo();
    }

    public ModEstruturaNFCE lerXml(String nomeXml) {
        String camposXml = "";

        ArrayList<ModItem> arryItem = new ArrayList<ModItem>();
        ArrayList<ModPagamento> arryPag = new ArrayList<ModPagamento>();
        modEst = new ModEstruturaNFCE();
        
        try {
            camposXml = ctlArq.lerArquivo(nomeXml).get(0);
            Document doc = Utils.parseXml(camposXml);
            Element root = doc.getDocumentElement();

            NodeList nlIde  = root.getElementsByTagName("ide");
            NodeList nlEmit = root.getElementsByTagName("emit");
            NodeList nlDet  = root.getElementsByTagName("det");
            NodeList nlTotal  = root.getElementsByTagName("total");
            NodeList nlTransp = root.getElementsByTagName("transp");
            NodeList nlPag = root.getElementsByTagName("pag");
            NodeList nlInfAdic = root.getElementsByTagName("infAdic");
            
            if (nlIde.getLength() > 0) {
                for (int i = 0; i < nlIde.item(0).getChildNodes().getLength(); i++) {
                    switch (i) {
                        case 0://cUF
                            modEst.setcUF(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 1://cNF
                            modEst.setcNF(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 2://natOp
                            modEst.setNatOp(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 3://indPag
                            modEst.setIndPag(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 4://mod
                            modEst.setMod(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 5://serie
                            modEst.setSerie(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 6://nNF
                            modEst.setnNF(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 7://dhEmi
                            modEst.setDhEmi(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 8://tpNF
                            modEst.setTpNF(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 9://idDest
                            modEst.setIdDest(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 10://cMunFG
                            modEst.setcMunFG(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 11://tpImp
                            modEst.setTpImp(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 12://tpEmis
                            modEst.setTpEmis(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 13://cDV
                            modEst.setcDV(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 14://tpAmb
                            modEst.setTpAmb(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 15://finNFe    
                            modEst.setFinNFe(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 16://indFinal
                            modEst.setIndFinal(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 17://indPres
                            modEst.setIndPres(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 18://procEmi
                            modEst.setProcEmi(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 19://verProc
                            modEst.setVerProc(nlIde.item(0).getChildNodes().item(i).getTextContent());
                            break;
                    }
                }
            }
            
            if (nlEmit.getLength() > 0) {
                for (int i = 0; i < nlEmit.item(0).getChildNodes().getLength(); i++) {
                    switch (i) {
                        case 0://CNPJ
                            modEst.setCNPJ(nlEmit.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 1://xNome
                            modEst.setxNome(nlEmit.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 2://xFant
                            modEst.setxFant(nlEmit.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 3://enderEmit
                            for (int j = 0; j < nlEmit.item(0).getChildNodes().item(3).getChildNodes().getLength(); j++) {
                                switch (j) {
                                    case 0://xLgr
                                        modEst.setxLgr(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 1://nro
                                        modEst.setNro(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 2://xBairro
                                        modEst.setxBairro(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 3://cMun
                                        modEst.setcMun(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 4://xMun
                                        modEst.setxMun(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 5://UF
                                        modEst.setUF(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 6://CEP
                                        modEst.setCEP(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 7://cPais
                                        modEst.setcPais(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 8://xPais
                                        modEst.setxPais(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                    case 9://fone
                                        modEst.setFone(nlEmit.item(0).getChildNodes().item(i).getChildNodes().item(j).getTextContent());
                                        break;
                                }
                            }
                            break;
                        case 4://IE
                            modEst.setIE(nlEmit.item(0).getChildNodes().item(i).getTextContent());
                            break;
                        case 5://CRT
                            modEst.setCRT(nlEmit.item(0).getChildNodes().item(i).getTextContent());
                            break;
                    }
                }
            }
            
            if (nlDet.getLength() > 0) {
                for (int i = 0; i < nlDet.getLength(); i++) {
                    ModItem modItemAux = new ModItem();

                    for (int j = 0; j < nlDet.item(i).getChildNodes().getLength(); j++) {
                        switch (j) {
                            case 0:
                                //prod
                                modItemAux.setcProd(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(0).getTextContent());
                                modItemAux.setcEAN(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(1).getTextContent());
                                modItemAux.setxProd(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(2).getTextContent());
                                modItemAux.setNCM(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(3).getTextContent());
                                modItemAux.setCFOP(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(4).getTextContent());
                                modItemAux.setuCom(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(5).getTextContent());
                                modItemAux.setqCom(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(6).getTextContent());
                                modItemAux.setvUnCom(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(7).getTextContent());
                                modItemAux.setvProd(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(8).getTextContent());
                                modItemAux.setcEANTrib(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(9).getTextContent());
                                modItemAux.setuTrib(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(10).getTextContent());
                                modItemAux.setqTrib(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(11).getTextContent());
                                modItemAux.setvUnTrib(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(12).getTextContent());
                                //System.out.println(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(13).getNodeName());
                                if (nlDet.item(i).getChildNodes().item(j).getChildNodes().item(13).getNodeName().equals("vDesc")) {
                                    modItemAux.setvDesc(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(13).getTextContent());
                                    modItemAux.setIndTot(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(14).getTextContent());
                                }
                                if (nlDet.item(i).getChildNodes().item(j).getChildNodes().item(13).getNodeName().equals("indTot")) {
                                    modItemAux.setIndTot(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(13).getTextContent());
                                }
                                
                                break;
                            case 1:
                                //imposto ICMS
                                modItemAux.setIcmsOrig(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getTextContent());
                                modItemAux.setIcmsCST(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(1).getTextContent());

                                //imposto PIS
                                //modItemAux.setPisCST(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(0).getNodeName());
                                //modItemAux.setPisvBC(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(1).getNodeName());
                                //modItemAux.setPispPIS(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(2).getNodeName());
                                //modItemAux.setPisvPIS(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(3).getNodeName());

                                //imposto COFINS
                                //modItemAux.setCofCST(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(2).getChildNodes().item(0).getChildNodes().item(0).getNodeName());
                                //modItemAux.setCofvBC(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(2).getChildNodes().item(0).getChildNodes().item(0).getNodeName());
                                //modItemAux.setCofpCOFINS(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(2).getChildNodes().item(0).getChildNodes().item(0).getNodeName());
                                //modItemAux.setCofvCOFINS(nlDet.item(i).getChildNodes().item(j).getChildNodes().item(2).getChildNodes().item(0).getChildNodes().item(0).getNodeName());

                                break;
                        }
                    }
                    arryItem.add(modItemAux);
                }
                
                modEst.setDetItem(arryItem);
            }
            
            if (nlTotal.getLength() > 0) {
                modEst.setvBC(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(1).getTextContent());
                modEst.setvICMSDeson(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(2).getTextContent());
                modEst.setvFCPUFDest(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(3).getTextContent());
                modEst.setvBCST(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(4).getTextContent());
                modEst.setvST(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(5).getTextContent());
                modEst.setvProd(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(6).getTextContent());
                modEst.setvFrete(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(7).getTextContent());
                modEst.setvSeg(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(8).getTextContent());
                modEst.setvDesc(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(9).getTextContent());
                modEst.setvII(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(10).getTextContent());
                modEst.setvIPI(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(11).getTextContent());
                modEst.setvPIS(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(12).getTextContent());
                modEst.setvCOFINS(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(13).getTextContent());
                modEst.setvOutro(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(14).getTextContent());
                modEst.setvNF(nlTotal.item(0).getChildNodes().item(0).getChildNodes().item(15).getTextContent());
            }
            
            if (nlTransp.getLength() > 0) {
                modEst.setModFrete(nlTransp.item(0).getChildNodes().item(0).getTextContent());
            }
            
            if (nlPag.getLength() > 0) {
                
                for(int i = 0; i < nlPag.getLength(); i++) {
                    ModPagamento modPagAux = new ModPagamento();
                    modPagAux.settPag(nlPag.item(i).getChildNodes().item(0).getTextContent());
                    modPagAux.setvPag(nlPag.item(i).getChildNodes().item(1).getTextContent());
                    arryPag.add(modPagAux);
                }
                
                modEst.setPagamento(arryPag);
            }
            
            if(nlInfAdic.getLength() > 0) {
                modEst.setInfCpl(nlInfAdic.item(0).getChildNodes().item(0).getTextContent());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CtlManipulaXml.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modEst;
    }

    public ModEstruturaNFCE InfoCupom(String nomeXml, ModEstruturaNFCE modEstru) {
        
        System.out.println("Xml -- " + nomeXml);
        String data   = null;
        String filial = null;
        String caixa  = null;
        String coo    = null;
        String nfce   = null;
        String numNfce = null;
        
        ModCupom modCup = new ModCupom();
        
        String[] vetorXml = nomeXml.split("_");
        
        for(int i = 0; i< vetorXml.length; i++) {
            switch (i) {
                case 1:
                    data = vetorXml[i];
                    break;
                case 2:
                    filial = vetorXml[i].substring(0, 3);
                    caixa  = vetorXml[i].substring(3, 5);
                    break;
                case 3:
                    coo = vetorXml[i];
                    break;
                case 4:
                    nfce = vetorXml[i];
                    break;
                case 5:
                    numNfce = vetorXml[i].substring(0, 44);
                    break;
            }
        }
        
        modCup.setCpData(data);
        modCup.setCpFilial(filial);
        modCup.setCpCaixa(caixa);
        modCup.setCpCoo(coo);
        modCup.setCpNfce(nfce);
        modCup.setCpNumnfce(numNfce);
            
        modEstru.setModCup(modCup);
        
        return modEstru;
    }
}

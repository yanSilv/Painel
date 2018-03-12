/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.DAO;

import br.com.painelnfce2017.model.ModCupom;
import br.com.painelnfce2017.model.ModEstruturaNFCE;
import br.com.painelnfce2017.model.ModItem;
import br.com.painelnfce2017.model.ModPagamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author programador
 */
public class DaoConsultaNFCe {
    
    private String db;
    
    private PreparedStatement ps;
    private PreparedStatement psSelect;
    private ResultSet rs;
    private ResultSet rsAux;
    
    public DaoConsultaNFCe(String bancoDados) {
        this.db = bancoDados;
    }
    
    public ModEstruturaNFCE consultaCupom(String chaveNFCe) {
        ModEstruturaNFCE modEst = new ModEstruturaNFCE();
        
        String sqlselect;
       
        sqlselect = "SELECT * "
                + "FROM tb_cupons_cx cx "
                + "INNER JOIN tb_nfce nfce "
                + "ON (cx.id_cupons = nfce.id_nfce AND cx.cp_numnfce = ?) "
                + "INNER JOIN tb_item_nfce item  "
                + "ON (cx.id_cupons = item.id_nfce) "
                + "INNER JOIN tb_pagamento pag "
                + "ON (cx.id_cupons = pag.id_nfce) "
                + "INNER JOIN tb_total_nfce tot "
                + "ON (cx.id_cupons = tot.id_nfce) "
                + "LEFT OUTER JOIN tb_tef_cx tef   " 
                + "ON (cx.id_cupons = tef.id_cx)   "
                + "LEFT OUTER JOIN tb_documentos doc " 
                + "ON (cx.cp_coo = doc.doc_coo)  " 
                + "GROUP BY cx.id_cupons, item.id_it, pag.id_pag, tot.id_total, tef.id_tef, doc.id_doc";
        
        try {
            
            psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselect);
            psSelect.setString(1, chaveNFCe);
            rs = psSelect.executeQuery();
            
            modEst = montaModCup(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoConsultaNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modEst;
    }
    
    public ArrayList<ModEstruturaNFCE> consultaCupomData(String dataNFCe) {
        ArrayList<ModEstruturaNFCE> arrayCupomCx = new ArrayList<ModEstruturaNFCE>();
        ModEstruturaNFCE modEst = new ModEstruturaNFCE();
        ModCupom modCup = new ModCupom();
        
        String sqlselectTudo;
        String sqlselectData;
        sqlselectData = "SELECT * FROM tb_cupons_cx cx WHERE cp_data = ? ORDER BY cx.cp_filial;";
        
        sqlselectTudo = "SELECT * "
                + "FROM tb_cupons_cx cx "
                + "INNER JOIN tb_nfce nfce "
                + "ON (cx.id_cupons = nfce.id_nfce AND cx.cp_numnfce = ?) "
                + "INNER JOIN tb_item_nfce item  "
                + "ON (cx.id_cupons = item.id_nfce) "
                + "INNER JOIN tb_pagamento pag "
                + "ON (cx.id_cupons = pag.id_nfce) "
                + "INNER JOIN tb_total_nfce tot "
                + "ON (cx.id_cupons = tot.id_nfce) "
                + "LEFT OUTER JOIN tb_tef_cx tef   " 
                + "ON (cx.id_cupons = tef.id_cx)   "
                + "LEFT OUTER JOIN tb_documentos doc " 
                + "ON (cx.cp_coo = doc.doc_coo)  " 
                + "GROUP BY cx.id_cupons, item.id_it, pag.id_pag, tot.id_total, tef.id_tef, doc.id_doc";
        
        try {
            psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselectData);
            psSelect.setString(1, dataNFCe);
            rs = psSelect.executeQuery();
            while (rs.next()) {
                modCup.setCpNumnfce(rs.getString("cp_numnfce"));
                
                psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselectTudo);
                psSelect.setString(1, modCup.getCpNumnfce());
                rsAux = psSelect.executeQuery();
                
                arrayCupomCx.add(montaModCup(rsAux));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoConsultaNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayCupomCx;
    }
    
    public ArrayList<ModEstruturaNFCE> consultaCupomData(String dataNFCe, String validos, String contigencia) {
        ArrayList<ModEstruturaNFCE> arrayCupomCx = new ArrayList<ModEstruturaNFCE>();
        ModEstruturaNFCE modEst = new ModEstruturaNFCE();
        ModCupom modCup = new ModCupom();
        
        String sqlselectTudo;
        String sqlselectData = "";
        
        if (validos.equals("true") && contigencia.equals("true")) {
            sqlselectData = "SELECT * FROM tb_cupons_cx cx WHERE cp_data = ? ORDER BY cx.cp_filial;";
        } else if (validos.equals("true")) {
            sqlselectData = "SELECT * FROM tb_cupons_cx cx WHERE cp_data = ? AND cp_status = 1 ORDER BY cx.cp_filial;";
        } else if (contigencia.equals("true")) {
            sqlselectData = "SELECT * FROM tb_cupons_cx cx WHERE cp_data = ? AND cp_status = 0 ORDER BY cx.cp_filial;";
        }
        
        sqlselectTudo = "SELECT * "
                + "FROM tb_cupons_cx cx "
                + "INNER JOIN tb_nfce nfce "
                + "ON (cx.id_cupons = nfce.id_nfce AND cx.cp_numnfce = ?) "
                + "INNER JOIN tb_item_nfce item  "
                + "ON (cx.id_cupons = item.id_nfce) "
                + "INNER JOIN tb_pagamento pag "
                + "ON (cx.id_cupons = pag.id_nfce) "
                + "INNER JOIN tb_total_nfce tot "
                + "ON (cx.id_cupons = tot.id_nfce) "
                + "LEFT OUTER JOIN tb_tef_cx tef   " 
                + "ON (cx.id_cupons = tef.id_cx)   "
                + "LEFT OUTER JOIN tb_documentos doc " 
                + "ON (cx.cp_coo = doc.doc_coo)  " 
                + "GROUP BY cx.id_cupons, item.id_it, pag.id_pag, tot.id_total, tef.id_tef, doc.id_doc";
        
        try {
            System.out.println(sqlselectTudo);
            psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselectData);
            psSelect.setString(1, dataNFCe);
            rs = psSelect.executeQuery();
            while (rs.next()) {
                modCup.setCpNumnfce(rs.getString("cp_numnfce"));
               
                psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselectTudo);
                psSelect.setString(1, modCup.getCpNumnfce());
                rsAux = psSelect.executeQuery();
                
                arrayCupomCx.add(montaModCup(rsAux));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoConsultaNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arrayCupomCx;
    }
    
    private ModEstruturaNFCE montaModCup(ResultSet rs) {
        
        ModEstruturaNFCE modEst = new ModEstruturaNFCE();
        ModCupom modCup = new ModCupom();
        
        int itemNum = 0;
        int itemAux = 0;
        int itemAux1 = 0;
        
        ArrayList<ModItem> arrayItem = new ArrayList<ModItem>();
        ArrayList<ModPagamento> arrayPag = new ArrayList<ModPagamento>();
        try {
            while (rs.next()) {
                
                //Pagamento
                ModPagamento modPag = new ModPagamento();
                modPag.setIdPag(rs.getString("id_pag"));
                modPag.settPag(rs.getString("pag_tpag"));
                modPag.setvPag(rs.getString("pag_vpag"));
                
                arrayPag.add(modPag);
                
                //Cupom
                modCup.setCpData(rs.getString("cp_data"));
                modCup.setCpFilial(rs.getString("cp_filial"));
                modCup.setCpCaixa(rs.getString("cp_caixa"));
                modCup.setCpCoo(rs.getString("cp_coo"));
                modCup.setCpNfce(rs.getString("cp_nfce"));
                modCup.setCpNumnfce(rs.getString("cp_numnfce"));
                modCup.setCpAutorizacao(rs.getString("cp_autorizacao"));
                modCup.setCpMotivo(rs.getString("cp_motivo"));
                modCup.setStatus(rs.getInt("cp_status"));

                //NFCE
                modEst.setcNF(rs.getString("nf_cnf"));
                modEst.setcUF(rs.getString("nf_cuf"));
                modEst.setNatOp(rs.getString("nf_nat_op"));
                modEst.setIndPag(rs.getString("nf_ind_pag"));
                modEst.setMod(rs.getString("nf_mod"));
                modEst.setSerie(rs.getString("nf_serie"));
                modEst.setnNF(rs.getString("nf_nnf"));
                modEst.setDhEmi(rs.getString("nf_dhemi"));
                modEst.setTpNF(rs.getString("nf_tp_nf"));
                modEst.setIdDest(rs.getString("nf_id_dest"));
                modEst.setcMunFG(rs.getString("nf_cmun_fg"));
                modEst.setTpImp(rs.getString("nf_tpimp"));
                modEst.setTpEmis(rs.getString("nf_tp_emis"));
                modEst.setcDV(rs.getString("nf_cd_dv"));
                modEst.setTpAmb(rs.getString("nf_tpamb"));
                modEst.setFinNFe(rs.getString("nf_fin_nfe"));
                modEst.setIndFinal(rs.getString("nf_ind_final"));
                modEst.setIndPres(rs.getString("nf_ind_press"));
                modEst.setProcEmi(rs.getString("nf_proc_emi"));
                modEst.setCNPJ(rs.getString("nf_cnpj"));
                modEst.setxNome(rs.getString("nf_xnome"));
                modEst.setxFant(rs.getString("nf_xfant"));
                modEst.setxLgr(rs.getString("nf_xlgr"));
                modEst.setNro(rs.getString("nf_nro"));
                modEst.setxBairro(rs.getString("nf_xbairro"));
                modEst.setcMun(rs.getString("nf_cmun"));
                modEst.setxMun(rs.getString("nf_xmun"));
                modEst.setUF(rs.getString("nf_uf"));
                modEst.setCEP(rs.getString("nf_cep"));
                modEst.setcPais(rs.getString("nf_cpais"));
                modEst.setxPais(rs.getString("nf_xpais"));
                modEst.setFone(rs.getString("nf_fone"));
                modEst.setIE(rs.getString("nf_ie"));
                modEst.setCRT(rs.getString("nf_crt"));
                modEst.setInfCpl(rs.getString("nf_infcpl"));

                //Total
                modEst.setvBC(rs.getString("to_vbc"));
                modEst.setvICMS(rs.getString("to_vicms"));
                modEst.setvICMSDeson(rs.getString("to_vicms_des"));
                modEst.setvFCPUFDest(rs.getString("to_vfcpuf"));
                modEst.setvBCST(rs.getString("to_vbcst"));
                modEst.setvST(rs.getString("to_vst"));
                modEst.setvProd(rs.getString("to_vprod"));
                modEst.setvFrete(rs.getString("to_vfrent"));
                modEst.setvSeg(rs.getString("to_vseg"));
                modEst.setvDesc(rs.getString("to_vdesc"));
                modEst.setvII(rs.getString("to_vii"));
                modEst.setvIPI(rs.getString("to_vipi"));
                modEst.setvPIS(rs.getString("to_vpis"));
                modEst.setvCOFINS(rs.getString("to_vconfins"));
                modEst.setvOutro(rs.getString("to_voutro"));
                modEst.setvNF(rs.getString("to_vnf"));

                //Item
                ModItem modItem = new ModItem();
                modItem.setIdItAux(rs.getString("id_it"));
                itemAux1 = Integer.parseInt(modItem.getIdItAux());
                
                if (itemAux == itemAux1) {
                    continue;
                } else {
                    itemAux = itemAux1;
                    ++itemNum;
                    modItem.setIdIt(String.valueOf(itemNum));
                }
                
                modItem.setcProd(rs.getString("it_cprod"));
                modItem.setcEAN(rs.getString("it_ean"));
                modItem.setxProd(rs.getString("it_xprod"));
                modItem.setNCM(rs.getString("it_ncm"));
                modItem.setCFOP(rs.getString("it_cfop"));
                modItem.setuCom(rs.getString("it_ucom"));
                modItem.setqCom(rs.getString("it_qcom"));
                modItem.setvUnCom(rs.getString("it_vuncom"));
                modItem.setvProd(rs.getString("it_vprod"));
                modItem.setcEANTrib(rs.getString("it_ean_trib"));
                modItem.setuTrib(rs.getString("it_utrib"));
                modItem.setqTrib(rs.getString("it_qtrib"));
                modItem.setuTrib(rs.getString("it_un_trib"));
                modItem.setIndTot(rs.getString("it_ind_tot"));
                modItem.setIcmsOrig(rs.getString("it_orig"));
                modItem.setIcmsCST(rs.getString("it_cst"));
                modItem.setvDesc(rs.getString("it_vdesc"));
                arrayItem.add(modItem);
            }
            
            modEst.setModCup(modCup);
            modEst.setDetItem(arrayItem);
            modEst.setPagamento(arrayPag);
        } catch (SQLException ex) {
            Logger.getLogger(DaoConsultaNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modEst;
    }
    
}

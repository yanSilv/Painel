/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.DAO;

import br.com.painelnfce2017.model.ModEstruturaNFCE;
import br.com.painelnfce2017.model.ModItem;
import br.com.painelnfce2017.model.ModPagamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author programador
 */
public class DaoSalvarNFCe {

    private String db;

    private PreparedStatement ps;
    private PreparedStatement psSelect;
    private ResultSet rs;

    public DaoSalvarNFCe(String bancoDados) {
        this.db = bancoDados;
    }

    public boolean salvarNFCe(ModEstruturaNFCE modEst) {
        boolean status = false;
        String sql;
        String sqlselect;

        try {
            sql = "PRAGMA SYNCHRONOUS=OFF";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            ps.execute();

            sql = "INSERT INTO tb_nfce (id_nfce, nf_cnf,nf_cuf ,nf_nat_op ,nf_ind_pag ,nf_mod ,nf_serie ,nf_nnf ,nf_dhemi ,nf_tp_nf ,nf_id_dest ,nf_cmun_fg ,nf_tpimp ,nf_tp_emis ,nf_cd_dv ,nf_tpamb ,nf_fin_nfe ,nf_ind_final ,nf_ind_press ,nf_proc_emi ,nf_cnpj ,nf_xnome ,nf_xfant ,nf_xlgr ,nf_nro ,nf_xbairro ,nf_cmun ,nf_xmun ,nf_uf ,nf_cep ,nf_cpais ,nf_xpais ,nf_fone ,nf_ie ,nf_crt, nf_infcpl)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            
            ps.setString(1, modEst.getModCup().getIdCupom());
            ps.setString(2, modEst.getcNF());
            ps.setString(3, modEst.getcUF());
            ps.setString(4, modEst.getNatOp());
            ps.setString(5, modEst.getIndPag());
            ps.setString(6, modEst.getMod());
            ps.setString(7, modEst.getSerie());
            ps.setString(8, modEst.getnNF());
            ps.setString(9, modEst.getDhEmi());
            ps.setString(10, modEst.getTpNF());
            ps.setString(11, modEst.getIdDest());
            ps.setString(12, modEst.getcMunFG());
            ps.setString(13, modEst.getTpImp());
            ps.setString(14, modEst.getTpEmis());
            ps.setString(15, modEst.getcDV());
            ps.setString(16, modEst.getTpAmb());
            ps.setString(17, modEst.getFinNFe());
            ps.setString(18, modEst.getIndFinal());
            ps.setString(19, modEst.getIndPres());
            ps.setString(20, modEst.getProcEmi());
            ps.setString(21, modEst.getCNPJ());
            ps.setString(22, modEst.getxNome());
            ps.setString(23, modEst.getxFant());
            ps.setString(24, modEst.getxLgr());
            ps.setString(25, modEst.getNro());
            ps.setString(26, modEst.getxBairro());
            ps.setString(27, modEst.getcMun());
            ps.setString(28, modEst.getxMun());
            ps.setString(29, modEst.getUF());
            ps.setString(30, modEst.getCEP());
            ps.setString(31, modEst.getcPais());
            ps.setString(32, modEst.getxPais());
            ps.setString(33, modEst.getFone());
            ps.setString(34, modEst.getIE());
            ps.setString(35, modEst.getCRT());
            ps.setString(36, modEst.getInfCpl());
            ps.execute();
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean salvarItens(ModEstruturaNFCE modEst) {

        boolean status = false;
        String sql;
        String sqlSelect;

        try {
            sql = "PRAGMA SYNCHRONOUS=OFF";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            ps.execute();

            sql = "INSERT INTO tb_item_nfce (id_nfce, it_cprod,it_ean,it_xprod,it_ncm,it_cfop,it_ucom,it_qcom,it_vuncom,it_vprod,it_ean_trib,it_utrib,it_qtrib,it_un_trib,it_ind_tot,it_orig,it_cst,it_vdesc )"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);

            for (ModItem item : modEst.getDetItem()) {
                ps.setString(1, modEst.getModCup().getIdCupom());
                ps.setString(2, item.getcProd());
                ps.setString(3, item.getcEAN());
                ps.setString(4, item.getxProd());
                ps.setString(5, item.getNCM());
                ps.setString(6, item.getCFOP());
                ps.setString(7, item.getuCom());
                ps.setString(8, item.getqCom());
                ps.setString(9, item.getvUnCom());
                ps.setString(10, item.getvProd());
                ps.setString(11, item.getcEANTrib());
                ps.setString(12, item.getuTrib());
                ps.setString(13, item.getqTrib());
                ps.setString(14, item.getvUnTrib());
                ps.setString(15, item.getIndTot());
                ps.setString(16, item.getIcmsOrig());
                ps.setString(17, item.getIcmsCST());
                ps.setString(18, item.getvDesc());
                ps.execute();

            }
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean salvarTotal(ModEstruturaNFCE modEst) {
        boolean status = false;
        String sql;
        String sqlselect;

        try {
            sql = "PRAGMA SYNCHRONOUS=OFF";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            ps.execute();

            sql = "INSERT INTO tb_total_nfce (id_nfce,to_vbc,to_vicms,to_vicms_des,to_vfcpuf,to_vbcst,to_vst,to_vprod,to_vfrent,to_vseg,to_vdesc,to_vii,to_vipi,to_vpis,to_vconfins,to_voutro,to_vnf)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);

            ps.setString(1, modEst.getModCup().getIdCupom());
            ps.setString(2, modEst.getvBC());
            ps.setString(3, modEst.getvICMS());
            ps.setString(4, modEst.getvICMSDeson());
            ps.setString(5, modEst.getvFCPUFDest());
            ps.setString(6, modEst.getvBCST());
            ps.setString(7, modEst.getvST());
            ps.setString(8, modEst.getvProd());
            ps.setString(9, modEst.getvFrete());
            ps.setString(10, modEst.getvSeg());
            ps.setString(11, modEst.getvDesc());
            ps.setString(12, modEst.getvII());
            ps.setString(13, modEst.getvIPI());
            ps.setString(14, modEst.getvPIS());
            ps.setString(15, modEst.getvCOFINS());
            ps.setString(16, modEst.getvOutro());
            ps.setString(17, modEst.getvNF());
            ps.execute();

            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean salvarPag(ModEstruturaNFCE modEst) {
        boolean status = false;
        String sql;
        String sqlselect;

        try {
            sql = "PRAGMA SYNCHRONOUS=OFF";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            ps.execute();

            sql = "INSERT INTO tb_pagamento (id_nfce, pag_tpag, pag_vpag)"
                    + "VALUES(?,?,?)";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            for (ModPagamento pag : modEst.getPagamento()) {
                ps.setString(1, modEst.getModCup().getIdCupom());
                ps.setString(2, pag.gettPag());
                ps.setString(3, pag.getvPag());
                ps.execute();
            }

            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean salvarCupom(ModEstruturaNFCE modEstru) {
        boolean status = false;
        String sql;
        String sqlselect;
        
        try {
            
            /* Verifica se o codigo do produto já está cadastrado*/
            sqlselect = "SELECT cp_numnfce FROM tb_cupons_cx WHERE cp_numnfce = ?";
            psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselect);
            psSelect.setString(1, modEstru.getModCup().getCpNumnfce());
            rs = psSelect.executeQuery();

            rs.next();
            if (rs.getRow() > 0) {
                return status;
            }
            
            sql = "PRAGMA SYNCHRONOUS=OFF";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            ps.execute();
            
            sql = "INSERT INTO tb_cupons_cx (cp_data, cp_filial, cp_caixa, cp_coo, cp_nfce, cp_numnfce, cp_autorizacao, cp_motivo, cp_status)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            ps = CreateDatabase.getConexao(this.db).prepareStatement(sql);
            System.out.println(modEstru.getModCup().getCpData());
            ps.setString(1, modEstru.getModCup().getCpData());
            ps.setString(2, modEstru.getModCup().getCpFilial());
            ps.setString(3, modEstru.getModCup().getCpCaixa());
            ps.setString(4, modEstru.getModCup().getCpCoo());
            ps.setString(5, modEstru.getModCup().getCpNfce());
            ps.setString(6, modEstru.getModCup().getCpNumnfce());
            ps.setString(7, modEstru.getModCup().getCpAutorizacao());
            ps.setString(8, modEstru.getModCup().getCpMotivo());
            ps.setInt(9, modEstru.getModCup().getStatus());
            ps.execute();
            
            /* Verifica se o codigo do produto já está cadastrado*/
            
            sqlselect = "SELECT max(id_cupons) FROM tb_cupons_cx";
            psSelect = CreateDatabase.getConexao(this.db).prepareStatement(sqlselect);
            rs = psSelect.executeQuery();

            rs.next();
            if (rs.getRow() > 0) {
                modEstru.getModCup().setIdCupom(String.valueOf(rs.getInt("max(id_cupons)")));
            }
            System.out.println(modEstru.getModCup().getIdCupom());
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return status;
    }
}

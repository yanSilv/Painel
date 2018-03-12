/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.DAO;

import br.com.painelnfce2017.controler.CtlManipulaArquivo;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author programador
 */
public class CreateDatabase {

    private Connection conn;
    private static Connection sconn;
    private Statement stm;

    public static Connection getConexao(String arquivo) throws SQLException {
        CtlManipulaArquivo mani = new CtlManipulaArquivo();
        File file_arquivo = new File(arquivo);

        if (file_arquivo.exists()) {
            try {
                Class.forName("org.sqlite.JDBC");
                if (sconn == null) {
                    sconn = DriverManager.getConnection("jdbc:sqlite:/" + file_arquivo.getAbsolutePath());
                }
                return sconn;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void criaDB(String arquivo) throws SQLException, ClassNotFoundException {

        CtlManipulaArquivo mani = new CtlManipulaArquivo();
        File file_arquivo = new File(arquivo);
        File endereco = new File(mani.enderecoArquivo(file_arquivo.getAbsolutePath()));

        if (!file_arquivo.exists()) {
            endereco.mkdir();
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:/" + file_arquivo.getAbsolutePath());
            this.stm = this.conn.createStatement();
            construirBD();
            System.out.println("Arquivo do banco criado com sucesso!!");
        }
    }

    private boolean construirBD() {
        boolean status = false;
        CtlManipulaArquivo mani = new CtlManipulaArquivo();

        StringBuilder tb_nfce = new StringBuilder();
        StringBuilder tb_item_nfce = new StringBuilder();
        StringBuilder tb_total_nfce = new StringBuilder();
        StringBuilder tb_pagamento = new StringBuilder();
        StringBuilder tb_cupons_cx = new StringBuilder();
        StringBuilder tb_doc = new StringBuilder();
        StringBuilder tb_tef = new StringBuilder();

        tb_cupons_cx.append("CREATE TABLE tb_cupons_cx (");
        tb_cupons_cx.append("id_cupons INTEGER PRIMARY KEY AUTOINCREMENT,");
        tb_cupons_cx.append("cp_data VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_filial VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_caixa VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_coo VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_nfce VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_numnfce VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_autorizacao VARCHAR(45) NULL,");
        tb_cupons_cx.append("cp_motivo TEXT,");
        tb_cupons_cx.append("cp_status INTEGER )");

        tb_nfce.append("CREATE TABLE tb_nfce (");
        tb_nfce.append("id_nfce INTEGER,");
        tb_nfce.append("nf_cnf VARCHAR(45),");
        tb_nfce.append("nf_cuf VARCHAR(45),");
        tb_nfce.append("nf_nat_op VARCHAR(45),");
        tb_nfce.append("nf_ind_pag VARCHAR(45),");
        tb_nfce.append("nf_mod VARCHAR(45),");
        tb_nfce.append("nf_serie VARCHAR(45),");
        tb_nfce.append("nf_nnf VARCHAR(45),");
        tb_nfce.append("nf_dhemi VARCHAR(45),");
        tb_nfce.append("nf_tp_nf VARCHAR(45),");
        tb_nfce.append("nf_id_dest VARCHAR(45),");
        tb_nfce.append("nf_cmun_fg VARCHAR(45),");
        tb_nfce.append("nf_tpimp VARCHAR(45),");
        tb_nfce.append("nf_tp_emis VARCHAR(45),");
        tb_nfce.append("nf_cd_dv VARCHAR(45),");
        tb_nfce.append("nf_tpamb VARCHAR(45),");
        tb_nfce.append("nf_fin_nfe VARCHAR(45),");
        tb_nfce.append("nf_ind_final VARCHAR(45),");
        tb_nfce.append("nf_ind_press VARCHAR(45),");
        tb_nfce.append("nf_proc_emi VARCHAR(45),");
        tb_nfce.append("nf_cnpj VARCHAR(45),");
        tb_nfce.append("nf_xnome VARCHAR(45),");
        tb_nfce.append("nf_xfant VARCHAR(45),");
        tb_nfce.append("nf_xlgr VARCHAR(45),");
        tb_nfce.append("nf_nro VARCHAR(45),");
        tb_nfce.append("nf_xbairro VARCHAR(45),");
        tb_nfce.append("nf_cmun VARCHAR(45),");
        tb_nfce.append("nf_xmun VARCHAR(45),");
        tb_nfce.append("nf_uf VARCHAR(45),");
        tb_nfce.append("nf_cep VARCHAR(45),");
        tb_nfce.append("nf_cpais VARCHAR(45),");
        tb_nfce.append("nf_xpais VARCHAR(45),");
        tb_nfce.append("nf_fone VARCHAR(45),");
        tb_nfce.append("nf_ie VARCHAR(45),");
        tb_nfce.append("nf_crt VARCHAR(45),");
        tb_nfce.append("nf_infcpl TEXT)");

        tb_item_nfce.append("CREATE TABLE tb_item_nfce (");
        tb_item_nfce.append("id_it INTEGER PRIMARY KEY,");
        tb_item_nfce.append("id_nfce INTEGER NOT NULL,");
        tb_item_nfce.append("it_cprod VARCHAR(45) NULL,");
        tb_item_nfce.append("it_ean VARCHAR(45) NULL,");
        tb_item_nfce.append("it_xprod VARCHAR(45) NULL,");
        tb_item_nfce.append("it_ncm VARCHAR(45) NULL,");
        tb_item_nfce.append("it_cfop VARCHAR(45) NULL,");
        tb_item_nfce.append("it_ucom VARCHAR(45) NULL,");
        tb_item_nfce.append("it_qcom VARCHAR(45) NULL,");
        tb_item_nfce.append("it_vuncom VARCHAR(45) NULL,");
        tb_item_nfce.append("it_vprod VARCHAR(45) NULL,");
        tb_item_nfce.append("it_ean_trib VARCHAR(45) NULL,");
        tb_item_nfce.append("it_utrib VARCHAR(45) NULL,");
        tb_item_nfce.append("it_qtrib VARCHAR(45) NULL,");
        tb_item_nfce.append("it_un_trib VARCHAR(45) NULL,");
        tb_item_nfce.append("it_ind_tot VARCHAR(45) NULL,");
        tb_item_nfce.append("it_orig VARCHAR(45) NULL,");
        tb_item_nfce.append("it_cst VARCHAR(45) NULL,");
        tb_item_nfce.append("it_vdesc VARCHAR(45) NULL)");

        tb_total_nfce.append("CREATE TABLE tb_total_nfce (");
        tb_total_nfce.append("id_total INTEGER PRIMARY KEY,");
        tb_total_nfce.append("id_nfce INTEGER NOT NULL,");
        tb_total_nfce.append("to_vbc VARCHAR(45),");
        tb_total_nfce.append("to_vicms VARCHAR(45),");
        tb_total_nfce.append("to_vicms_des VARCHAR(45),");
        tb_total_nfce.append("to_vfcpuf VARCHAR(45),");
        tb_total_nfce.append("to_vbcst VARCHAR(45),");
        tb_total_nfce.append("to_vst VARCHAR(45),");
        tb_total_nfce.append("to_vprod VARCHAR(45),");
        tb_total_nfce.append("to_vfrent VARCHAR(45),");
        tb_total_nfce.append("to_vseg VARCHAR(45),");
        tb_total_nfce.append("to_vdesc VARCHAR(45),");
        tb_total_nfce.append("to_vii VARCHAR(45),");
        tb_total_nfce.append("to_vipi VARCHAR(45),");
        tb_total_nfce.append("to_vpis VARCHAR(45),");
        tb_total_nfce.append("to_vconfins VARCHAR(45),");
        tb_total_nfce.append("to_voutro VARCHAR(45),");
        tb_total_nfce.append("to_vnf VARCHAR(45))");

        tb_pagamento.append("CREATE TABLE tb_pagamento (");
        tb_pagamento.append("id_pag INTEGER PRIMARY KEY,");
        tb_pagamento.append("id_nfce INTEGER NOT NULL,");
        tb_pagamento.append("pag_tpag VARCHAR(45),");
        tb_pagamento.append("pag_vpag VARCHAR(45))");

        tb_tef.append("CREATE TABLE tb_tef_cx (");
        tb_tef.append("id_tef INTEGER PRIMARY KEY,");
        tb_tef.append("id_cx INTEGER NOT NULL,");
        tb_tef.append("tef_cupom VARCHAR(45),");
        tb_tef.append("tef_num_aux VARCHAR(45),");
        tb_tef.append("tef_conteudo TEXT)");

        tb_doc.append("CREATE TABLE tb_documentos (");
        tb_doc.append("id_doc INTEGER PRIMARY KEY,");
        tb_doc.append("doc_data VARCHAR(45),");
        tb_doc.append("doc_filial VARCHAR(45),");
        tb_doc.append("doc_caixa VARCHAR(45),");
        tb_doc.append("doc_coo VARCHAR(45),");
        tb_doc.append("doc_conteudo TEXT )");

        try {
            this.stm.executeUpdate(tb_cupons_cx.toString());
            this.stm.executeUpdate(tb_nfce.toString());
            this.stm.executeUpdate(tb_item_nfce.toString());
            this.stm.executeUpdate(tb_total_nfce.toString());
            this.stm.executeUpdate(tb_pagamento.toString());
            this.stm.executeUpdate(tb_tef.toString());
            this.stm.executeUpdate(tb_doc.toString());
            status = true;
        } catch (SQLException e) {
            //e.printStackTrace();
            status = false;
            
        }
        return status;
    }

}

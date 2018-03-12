/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.model;

/**
 *
 * @author programador
 */
public class Configuracao {
    String versao; //Versão do sistema 3.10
    String ambiente; //Ambiente de acesso 1 - Produção, 2 - Homologação
    String estado; //Codigo do estado segundo tabela do IBGE
    String codUf;  
    String urlStatus; //url de acesso a sefaz de status
    String urlConsult; //url de Consulta
    String certificado; //Caminho onde esta o certificado
    String senha; //Senha do certificado
    String nfecacerts; //Caminho do arquivo com informações de segurança do sistema
    
    String dirRecebe;
    String dirBanco;

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodUf() {
        return codUf;
    }

    public void setCodUf(String codUf) {
        this.codUf = codUf;
    }

    public String getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(String urlStatus) {
        this.urlStatus = urlStatus;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNfecacerts() {
        return nfecacerts;
    }

    public void setNfecacerts(String nfecacerts) {
        this.nfecacerts = nfecacerts;
    }

    public String getUrlConsult() {
        return urlConsult;
    }

    public void setUrlConsult(String urlConsult) {
        this.urlConsult = urlConsult;
    }

    public String getDirRecebe() {
        return dirRecebe;
    }

    public void setDirRecebe(String dirRecebe) {
        this.dirRecebe = dirRecebe;
    }

    public String getDirBanco() {
        return dirBanco;
    }

    public void setDirBanco(String dirBanco) {
        this.dirBanco = dirBanco;
    }
    
    
    
       
}

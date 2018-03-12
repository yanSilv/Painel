/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author programador
 */
@XmlRootElement
public class ModCupom {
    private String idCupom;
    private String cpData;
    private String cpFilial;
    private String cpCaixa;
    private String cpCoo;
    private String cpNfce;
    private String cpNumnfce;
    private String cpMotivo;
    private String cpAutorizacao;
    private int    status;

    public String getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(String idCupom) {
        this.idCupom = idCupom;
    }

    public String getCpData() {
        return cpData;
    }

    public void setCpData(String cpData) {
        this.cpData = cpData;
    }

    public String getCpFilial() {
        return cpFilial;
    }

    public void setCpFilial(String cpFilial) {
        this.cpFilial = cpFilial;
    }

    public String getCpCaixa() {
        return cpCaixa;
    }

    public void setCpCaixa(String cpCaixa) {
        this.cpCaixa = cpCaixa;
    }

    public String getCpCoo() {
        return cpCoo;
    }

    public void setCpCoo(String cpCoo) {
        this.cpCoo = cpCoo;
    }

    public String getCpNfce() {
        return cpNfce;
    }

    public void setCpNfce(String cpNfce) {
        this.cpNfce = cpNfce;
    }

    public String getCpNumnfce() {
        return cpNumnfce;
    }

    public void setCpNumnfce(String cpNumnfce) {
        this.cpNumnfce = cpNumnfce;
    }

    public String getCpMotivo() {
        return cpMotivo;
    }

    public void setCpMotivo(String cpMotivo) {
        this.cpMotivo = cpMotivo;
    }

    public String getCpAutorizacao() {
        return cpAutorizacao;
    }

    public void setCpAutorizacao(String cpAutorizacao) {
        this.cpAutorizacao = cpAutorizacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}

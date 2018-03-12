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
public class ModPagamento {
    
    private String idPag = "";
    private String idNfce = "";
    private String tPag = "";
    private String vPag = "";

    public String getIdPag() {
        return idPag;
    }

    public void setIdPag(String idPag) {
        this.idPag = idPag;
    }

    public String gettPag() {
        return tPag;
    }

    public void settPag(String tPag) {
        this.tPag = tPag;
    }

    public String getvPag() {
        return vPag;
    }

    public void setvPag(String vPag) {
        this.vPag = vPag;
    }

    public String getIdNfce() {
        return idNfce;
    }

    public void setIdNfce(String idNfce) {
        this.idNfce = idNfce;
    }

}

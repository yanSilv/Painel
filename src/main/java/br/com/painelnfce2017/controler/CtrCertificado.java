/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.controler;

import java.security.Security;
import br.com.painelnfce2017.model.Configuracao;

/**
 *
 * @author programador
 */
public class CtrCertificado {

    public void certificadoStore(Configuracao con) {
        /* 
        * Seta as configurações para a conexao 
        **/
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");

        System.clearProperty("javax.net.ssl.keyStore");
        System.clearProperty("javax.net.ssl.keyStorePassword");
        System.clearProperty("javax.net.ssl.trustStore");

        System.setProperty("javax.net.ssl.keyStore", "" + con.getCertificado() + "");
        System.setProperty("javax.net.ssl.keyStorePassword", "" + con.getSenha() + "");

        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", "nfe-cacerts");
        System.setProperty("javax.net.ssl.trustStore", "" + con.getNfecacerts() + "");
    }
}

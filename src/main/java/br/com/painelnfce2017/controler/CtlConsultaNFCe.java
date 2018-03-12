/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.controler;

import br.com.painelnfce2017.DAO.DaoConsultaNFCe;
import java.io.IOException;
import java.net.URL;
import br.com.painelnfce2017.model.ModEstruturaNFCE;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.painelnfce2017.model.Configuracao;
import br.com.painelnfce2017.model.ModCupom;

/**
 *
 * @author programador
 */
public class CtlConsultaNFCe {

    public ModEstruturaNFCE consulChaveNFCe(String bancoDados, String chaveNFCe) {
        ModEstruturaNFCE modEst = null;
        DaoConsultaNFCe daoCon = new DaoConsultaNFCe(bancoDados);

        modEst = daoCon.consultaCupom(chaveNFCe);

        return modEst;
    }

    public ArrayList<ModCupom> consulDataNFCe(String bancoDados, String dataNFCe) {
        ArrayList<ModEstruturaNFCE> arrayModEst = null;
        ArrayList<ModCupom> arrayCup = new ArrayList<ModCupom>();
        DaoConsultaNFCe daoCon = new DaoConsultaNFCe(bancoDados);

        arrayModEst = daoCon.consultaCupomData(dataNFCe);

        for (ModEstruturaNFCE modEstruturaNFCE : arrayModEst) {
            arrayCup.add(modEstruturaNFCE.getModCup());
        }
        return arrayCup;
    }

    public ArrayList<ModCupom> consulDataNFCe(String bancoDados, String dataNFCe, String validos, String contigencia) {
        ArrayList<ModEstruturaNFCE> arrayModEst = null;
        ArrayList<ModCupom> arrayCup = new ArrayList<ModCupom>();
        DaoConsultaNFCe daoCon = new DaoConsultaNFCe(bancoDados);

        arrayModEst = daoCon.consultaCupomData(dataNFCe, validos, contigencia);

        for (ModEstruturaNFCE modEstruturaNFCE : arrayModEst) {
            arrayCup.add(modEstruturaNFCE.getModCup());
        }
        return arrayCup;
    }
    
    public ArrayList<ModEstruturaNFCE> consulDataGeral(String bancoDados, String dataNFCe, String validos, String contigencia) {
        ArrayList<ModEstruturaNFCE> arrayModEst = null;
        ArrayList<ModEstruturaNFCE> arrayCup = new ArrayList<ModEstruturaNFCE>();
        DaoConsultaNFCe daoCon = new DaoConsultaNFCe(bancoDados);

        arrayModEst = daoCon.consultaCupomData(dataNFCe, validos, contigencia);

        for (ModEstruturaNFCE modEstruturaNFCE : arrayModEst) {
            arrayCup.add(modEstruturaNFCE);
        }
        return arrayCup;
    }

}

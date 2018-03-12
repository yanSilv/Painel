/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.painelnfce2017.controler;

import br.com.painelnfce2017.model.ModEstruturaNFCE;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import br.com.painelnfce2017.util.Utils;

/**
 *
 * @author programador
 */
public class CtlArquivoCx {

    Utils util;
    CtlManipulaArquivo ctlMan;

    final String versao = "281";
    final String dirTransmite = "transmite/";

    public CtlArquivoCx() {
        util = new Utils();
        ctlMan = new CtlManipulaArquivo();
    }

    public String montaCx(ModEstruturaNFCE cupomCx) {
        String linhaItem;
        String linhaForm;
        String linhaFina;
        String linhaNFCe;

        StringBuilder result = new StringBuilder();

        linhaItem = montaItem(0, cupomCx);
        linhaForm = montaFormPaga(0, cupomCx);
        linhaFina = montaFormFina(0, cupomCx);
        linhaNFCe = montaDadoNFCe(0, cupomCx);

        result.append(linhaItem);
        result.append(linhaForm);
        result.append(linhaFina);
        result.append(linhaNFCe);

        return result.toString();
    }

    private String montaItem(int opcao, ModEstruturaNFCE cupomCx) {
        String result = null;

        StringBuilder linha = new StringBuilder();

        for (int i = 0; i < cupomCx.getDetItem().size(); i++) {

            if (opcao == 0) {
                linha.append("VS");
            } else {
                linha.append("AC");
            }

            linha.append(versao);
            linha.append("40");
            linha.append(cupomCx.getModCup().getCpFilial());
            linha.append(cupomCx.getModCup().getCpData());
            linha.append(cupomCx.getModCup().getCpCaixa());
            linha.append("00000");
            linha.append(cupomCx.getModCup().getCpCoo());
            linha.append(cupomCx.getDhEmi().substring(11, 19));
            linha.append(infoCpl("Vendedor", cupomCx.getInfCpl(), ""));
            linha.append(util.lpad(cupomCx.getDetItem().get(i).getIdIt(), 3, '0'));
            linha.append(infoCpl("CodProd", cupomCx.getDetItem().get(i).getcProd(), ""));
            linha.append(infoCpl("Quantidade", cupomCx.getDetItem().get(i).getqTrib(), ""));
            linha.append(infoCpl("ValorProd", cupomCx.getDetItem().get(i).getvProd(), ""));
            linha.append(infoCpl("PercentProd", cupomCx.getDetItem().get(i).getvDesc(), cupomCx.getDetItem().get(i).getvProd()));
            linha.append("00");
            linha.append(infoCpl("Cliente", cupomCx.getInfCpl(), ""));
            linha.append("00");
            linha.append("HN");
            linha.append(infoCpl("ValorCrm", " ", ""));
            linha.append("00");
            linha.append(infoCpl("ValorLote", " ", ""));
            linha.append(infoCpl("Quantidade", cupomCx.getDetItem().get(i).getqTrib(), ""));
            linha.append("  ");
            linha.append(infoCpl("ValorDesc", cupomCx.getDetItem().get(i).getvDesc(), ""));
            linha.append("\n");
        }

        result = linha.toString();

        return result;
    }

    private String montaFormPaga(int opcao, ModEstruturaNFCE cupomCx) {
        String result = null;
        int idPag = 0;
        int idPagAux = 0;
        boolean ativo = false;
        ArrayList<Integer> idIt = new ArrayList<Integer>();

        StringBuilder linha = new StringBuilder();

        for (int i = 0; i < cupomCx.getPagamento().size(); i++) {
            idPagAux = Integer.parseInt(cupomCx.getPagamento().get(i).getIdPag());
            ativo = false;
            for (Integer integer : idIt) {
                if (integer == idPagAux) {
                    ativo = true;
                    break;
                }
            }

            if (ativo == false) {
                idIt.add(idPagAux);
            }
        }

        if (idIt.size() == 1) {
            return "";
        } else {
            idIt.removeAll(idIt);
        }

        for (int i = 0; i < cupomCx.getPagamento().size(); i++) {
            /**
             * Irá permitir a inserção de apenas uma forma de pagamento; O array
             * list esta duplicando alguns valores.
             */

            idPagAux = Integer.parseInt(cupomCx.getPagamento().get(i).getIdPag());
            ativo = false;
            for (Integer integer : idIt) {
                if (integer == idPagAux) {
                    ativo = true;
                    break;
                }
            }

            if (ativo == false) {
                idIt.add(idPagAux);
            } else {
                continue;
            }

            /**
             * Monta a estrutura da linha 49.
             */
            if (opcao == 0) {
                linha.append("VS");
            } else {
                linha.append("AC");
            }

            linha.append(versao);
            linha.append("49");
            linha.append(cupomCx.getModCup().getCpFilial());
            linha.append(cupomCx.getModCup().getCpData());
            linha.append(cupomCx.getModCup().getCpCaixa());
            linha.append("00000");
            linha.append(cupomCx.getModCup().getCpCoo());
            linha.append(cupomCx.getDhEmi().substring(11, 19));
            linha.append(infoCpl("TipoPag", cupomCx.getPagamento().get(i).gettPag(), ""));
            linha.append(infoCpl("ValorProd49", cupomCx.getPagamento().get(i).getvPag(), ""));
            linha.append(infoCpl("ValorProd49", infoCpl("TROCO", cupomCx.getInfCpl(), cupomCx.getPagamento().get(i).gettPag()), ""));
            linha.append("HN");
            linha.append("\n");
        }

        result = linha.toString();

        return result;
    }

    private String montaFormFina(int opcao, ModEstruturaNFCE cupomCx) {
        String result = null;
        int idPag = 0;
        int idPagAux = 0;
        boolean ativo = false;
        ArrayList<Integer> idIt = new ArrayList<Integer>();

        StringBuilder linha = new StringBuilder();

        /**
         * Monta a estrutura da linha 50.
         */
        if (opcao == 0) {
            linha.append("VS");
        } else {
            linha.append("AC");
        }

        linha.append(versao);
        linha.append("50");
        linha.append(cupomCx.getModCup().getCpFilial());
        linha.append(cupomCx.getModCup().getCpData());
        linha.append(cupomCx.getModCup().getCpCaixa());
        linha.append("00000");
        linha.append(cupomCx.getModCup().getCpCoo());
        linha.append(cupomCx.getDhEmi().substring(11, 19));
        linha.append(infoCpl("Vendedor", cupomCx.getInfCpl(), ""));
        linha.append(util.lpad(cupomCx.getDetItem().get(cupomCx.getDetItem().size() - 1).getIdIt(), 3, '0'));
        linha.append(infoCpl("ValorProd49", infoCpl("ValorFina50", cupomCx.getvProd(), cupomCx.getvDesc()), ""));
        linha.append(infoCpl("ValorProd49", cupomCx.getvDesc(), ""));
        linha.append("00");

        for (int i = 0; i < cupomCx.getPagamento().size(); i++) {
            idPagAux = Integer.parseInt(cupomCx.getPagamento().get(i).getIdPag());
            ativo = false;
            for (Integer integer : idIt) {
                if (integer == idPagAux) {
                    ativo = true;
                    break;
                }
            }

            if (ativo == false) {
                idIt.add(idPagAux);
            }
        }

        if (idIt.size() > 1) {
            linha.append("99");
        } else {
            linha.append(infoCpl("TipoPag", cupomCx.getPagamento().get(0).gettPag(), ""));
        }

        linha.append(infoCpl("ValorTroco", infoCpl("TROCO", cupomCx.getInfCpl(), cupomCx.getPagamento().get(0).gettPag()), ""));
        linha.append("HN");

        linha.append("\n");

        result = linha.toString();

        return result;
    }

    private String montaDadoNFCe(int opcao, ModEstruturaNFCE cupomCx) {
        String result = null;
        int idPag = 0;
        int idPagAux = 0;
        boolean ativo = false;
        ArrayList<Integer> idIt = new ArrayList<Integer>();

        StringBuilder linha = new StringBuilder();

        /**
         * Monta a estrutura da linha 61.
         */
        if (opcao == 0) {
            linha.append("VS");
        } else {
            linha.append("AC");
        }

        linha.append(versao);
        linha.append("61");
        linha.append(cupomCx.getModCup().getCpFilial());
        linha.append(cupomCx.getModCup().getCpData());
        linha.append(cupomCx.getModCup().getCpCaixa());
        linha.append("00000");
        linha.append(cupomCx.getModCup().getCpCoo());
        linha.append(cupomCx.getDhEmi().substring(11, 19));
        linha.append(infoCpl("Vendedor", cupomCx.getInfCpl(), ""));
        linha.append(infoCpl("Autorizacao", cupomCx.getModCup().getCpAutorizacao(), ""));
        linha.append(infoCpl("ChaveNfce", cupomCx.getModCup().getCpNumnfce(), ""));
        linha.append(infoCpl("NumSerie", cupomCx.getModCup().getCpNumnfce(), ""));
        linha.append(infoCpl("NumNFCE", cupomCx.getModCup().getCpNfce(), ""));
        linha.append("HN");
        linha.append("\n");

        result = linha.toString();

        return result;
    }

    private String infoCpl(String opcao, String infCpl, String valorPas) {
        String result = null;
        int indexPos = 0;
        String conteudo = null;
        String conteudoAux[] = null;

        if (opcao.equals("Vendedor")) {
            indexPos = infCpl.indexOf("Vendedor:");

            if (indexPos == -1) {
                //Layout cupom Yan
                indexPos = infCpl.indexOf("Vended:");
                conteudo = infCpl.substring(indexPos, infCpl.length());
                conteudoAux = conteudo.split(" ");
                String conteudoAux1[] = conteudoAux[0].split("\\:");
                result = conteudoAux1[1];

            } else {
                //Layout cupom Jotelly
                conteudo = infCpl.substring(indexPos, infCpl.length());
                conteudoAux = conteudo.split(" ");
                result = conteudoAux[1];
            }

        }

        if (opcao.equals("TROCO")) {
            if (valorPas.equals("01")) {
                indexPos = infCpl.indexOf("TROCO:");
                conteudo = infCpl.substring(indexPos, infCpl.length());
                conteudoAux = conteudo.split(" ");
                result = conteudoAux[1];
            } else {
                result = "0.00";
            }
        }

        if (opcao.equals("Quantidade")) {
            conteudoAux = infCpl.split("\\.");
            result = util.lpad(conteudoAux[0], 4, '0');
        }

        if (opcao.equals("ValorProd")) {
            result = util.lpad(infCpl, 12, '0');
        }

        if (opcao.equals("PercentProd")) {
            if (infCpl.length() == 0) {
                result = "00.00";
            } else {
                double percent = Double.parseDouble(infCpl);
                double valorTo = Double.parseDouble(valorPas);
                double valorPe = (percent * 100) / valorTo;
                infCpl = String.format(Locale.US,"%.2f", valorPe);
                result = util.lpad(infCpl, 5, '0');
            }
        }

        if (opcao.equals("Cliente")) {

            indexPos = infCpl.indexOf("Clie");
            if (indexPos == -1) {
                indexPos = infCpl.indexOf("CONV");
                conteudo = infCpl.substring(indexPos, infCpl.length());
                conteudoAux = conteudo.split("\\(");
                String conteudoAux1[] = conteudoAux[1].split("\\)");
                result = conteudoAux1[0];
            } else {
                conteudo = infCpl.substring(indexPos, infCpl.length());
                conteudoAux = conteudo.split("\\(");
                String conteudoAux1[] = conteudoAux[1].split("\\)");
                result = conteudoAux1[0];
            }

        }

        if (opcao.equals("ValorDesc")) {
            if (infCpl.length() == 0) {
                result = "00000.00";
            } else {
                result = util.lpad(infCpl, 8, '0');
            }
        }

        if (opcao.equals("ValorProd49")) {
            result = util.lpad(infCpl, 13, '0');
        }

        if (opcao.equals("ValorFina50")) {
            double valorFin = Double.parseDouble(infCpl);
            double valorDes = Double.parseDouble(valorPas);
            double valorRet = valorFin - valorDes;
            infCpl = String.format(Locale.US,"%.2f", valorRet);
            result = infCpl;
        }
        if (opcao.equals("ValorTroco")) {
            result = util.lpad(infCpl, 7, '0');
        }

        if (opcao.equals("ValorCrm")) {
            result = util.lpad(infCpl, 10, ' ');
        }

        if (opcao.equals("ValorLote")) {
            result = util.lpad(infCpl, 20, ' ');
        }

        if (opcao.equals("CodProd")) {
            result = util.lpad(infCpl, 6, '0');
        }

        if (opcao.equals("Autorizacao")) {
            result = util.lpad(infCpl, 15, '0');
        }

        if (opcao.equals("ChaveNfce")) {
            result = util.lpad(infCpl, 44, '0');
        }

        if (opcao.equals("NumSerie")) {
            infCpl = infCpl.substring(22, 25);
            result = util.lpad(infCpl, 3, '0');
        }

        if (opcao.equals("NumNFCE")) {
            result = util.lpad(infCpl, 8, '0');
        }

        if (opcao.equals("TipoPag")) {
            if (infCpl.equals("01")) {
                result = infCpl;
            } else if (infCpl.equals("04")) {
                result = "02";
            } else if (infCpl.equals("02")) {
                result = "03";
            } else if (infCpl.equals("03")) {
                result = "06";
            } else if (infCpl.equals("99")) {
                result = "07";
            }
        }
        return result;
    }

    public void salvarCupom(String resultado, ModEstruturaNFCE cupomCx) {
        StringBuilder nomenclatura = new StringBuilder();

        nomenclatura.append(this.dirTransmite);
        nomenclatura.append("cx");
        nomenclatura.append(cupomCx.getModCup().getCpData());
        nomenclatura.append("_");
        nomenclatura.append(cupomCx.getModCup().getCpFilial());
        nomenclatura.append(cupomCx.getModCup().getCpCaixa());
        nomenclatura.append("_");
        nomenclatura.append(cupomCx.getModCup().getCpCoo());
        nomenclatura.append(".txt");
        File file = new File(nomenclatura.toString());

        ctlMan.gravaArquivo(file, resultado, 1);

    }

}

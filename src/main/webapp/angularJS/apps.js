var app = angular.module("projetoPainel", ['ui.bootstrap', "chart.js"]);
app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});

app.value('urlBase', 'http://10.15.1.105:8080/painelnfce2018/rest/');
app.value('dirNfce', 'nfce/');

app.controller("projetoPainelControle", function ($scope, $http, urlBase, dirNfce) {

    $scope.consulta = {
        data: "",
        validos: true,
        contigencia: true,
        cancelados: true,
        fl_filial: false,
        fl_caixa: false,
        fl_coo: false,
        fl_numNfce: false,
        tx_filial: "",
        tx_caixa: "",
        tx_coo: "",
        tx_numNfce: ""
    };
    $scope.cabecalho = [{
            idCupom: "Cupom",
            cpData: " Data ",
            cpFilial: " Filial ",
            cpCaixa: " Caixa ",
            cpCoo: " Coo ",
            cpNfce: " Nfce ",
            cpNumnfce: " Chave NFCe",
            cpMotivo: " Motivo ",
            cpAutorizacao: " Autorizacao ",
            status: " Status "
        }];
    $scope.cabecalhoCompacto = [{
            cpFilial: " Filial ",
            cpValidados: " Validados ",
            cpContigencia: " Contigencia ",
            cpErro: " Erros "
        }];
    $scope.objeto = [{
            idCupom: "0",
            cpData: "",
            cpFilial: "0",
            cpCaixa: "0",
            cpCoo: "0",
            cpNfce: "0",
            cpNumnfce: "0",
            cpMotivo: "0",
            cpAutorizacao: "0",
            status: "0"
        }];
    
    $scope.objetoGeral = [];
    $scope.objetoCompacto = [];
    $scope.retornoConsulta = "";
    $scope.alimentaTable = [];
    $scope.qtdValidos = 0;
    $scope.qtdContigen = 0;
    $scope.qtdCancelado = 0;
    $scope.qtdErro = 0;
    $scope.pagAtual = 1;
    $scope.pagAtualCompac = 1;
    $scope.qtdItensPag = 10;
    $scope.totalItems = $scope.alimentaTable.length;
    $scope.totalItemsCompac = $scope.objetoCompacto.length;
    $scope.statusSucesso = "0";
    $scope.statusPedente = "0";
    $scope.statusCancelado = "0";
    $scope.itemSelecionado = false;
    $scope.somaTotalDia = 0;
    $scope.dataVendaDia = "";

    //Variaveis do grafico da Venda.
    $scope.labels = [];
    $scope.data = [];
    $scope.seriesVenda = ['Venda em R$'];
    
    $scope.optionsVenda = { 
        title: { 
            display: true, 
            text: 'Formas de pagamento', 
            position: 'bottom', 
            padding: 5 },
        legend: {
            display: true
        }
    };

    //Variaveis do grafico da loja
    $scope.labelsLoja = [];
    $scope.dataLoja = [];
    
    $scope.seriesLoja = ['Venda Loja em R$'];
    
    $scope.optionsLoja = { 
        title: {  
            position: 'bottom', 
            padding: 5 },
        legend: {
            display: true
        }
    };

    $scope.statusXml = function (status, opcao) {
        statusSucesso = "";

        if (status === 1 && opcao === 1) {
            statusSucesso = "sucesso";
        } else if (status === 0 && opcao === 0) {
            statusSucesso = "pendente";
        } else if (status === 2 && opcao === 2) {
            statusSucesso = "cancelado";
        }

        return statusSucesso;
    };

    //Carrega as informações do dia.
    $scope.carregarObjeto = function (consulta) {
        $http.get(urlBase + "consultadata/", {params: {
                data: Time(),
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {
            $scope.objeto = response.data;
            $scope.totalItems = $scope.objeto.length;
            $scope.alimentaTable = $scope.objeto;

            if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }
        });
    };

    //Solicita as informações pela data
    $scope.solicitaData = function (consulta) {
        //console.log(DataBanco(consulta));
        $http.get(urlBase + "consultadata/", {params: {
                data: DataBanco(consulta),
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {
            $scope.objeto = response.data;
            $scope.totalItems = $scope.objeto.length;
            $scope.alimentaTable = $scope.objeto;

            if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }
        });
    };

    //Solicita as informações Gerais.
    $scope.solicitaGeral = function (consulta, opcao) {

        var dataGeral = "";

        if (opcao === 0) {
            dataGeral = Time();
        } else if (opcao === 1) {
            dataGeral = DataBanco(consulta);
        }

        //Refazer essa parte, para não precisar realizar duas consultas na base de dados.
        $http.get(urlBase + "consultadata/", {params: {
                data: dataGeral,
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {
            $scope.objeto = response.data;
            $scope.totalItems = $scope.objeto.length;
            $scope.alimentaTable = $scope.objeto;

            if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }
        });

        $http.get(urlBase + "consultageral/", {params: {
                data: dataGeral,
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {

            //Chama a função que alimenta os graficos
            $scope.montaInfoGeral(consulta, response.data);

        });
    };

    //Pega as informações gerais e organiza nos vetores.
    $scope.montaInfoGeral = function (consulta, returnServer) {
        var finalizadora = 0;
        var dinheiro = 0;
        var debite = 0;
        var credite = 0;
        var convenio = 0;
        var total = 0;
        var filial = 0;
        var valorFi = 0;

        var dinheiroFil = 0;
        var debiteFil = 0;
        var crediteFil = 0;
        var convenioFil = 0;

        //Variaveis do grafico da loja
        $scope.labelsLoja = [];
        $scope.dataLoja = [];
        var labelsLojaAux = [];
        var dataLojaAux = [];
        
        var idPagVetor = [];
        var idRepetido = 0;
        
        var idPagRetorno = 0;

        $scope.objetoGeral = returnServer;

        for (var i = 0; i < $scope.objetoGeral.length; i++) {
            for (var j = 0; j < $scope.objetoGeral[i].pagamento.length; j++) {
                idRepetido = 0;
                if ((idPagRetorno === 0 || idPagRetorno !== $scope.objetoGeral[i].pagamento[j].idPag)) {
                    
                    if (idPagVetor.length === 0) {
                        
                        idPagVetor.push($scope.objetoGeral[i].pagamento[j].idPag);
                    
                    } else {
                        for (var k = 0; k < idPagVetor.length; k++) {
                            if (idPagVetor[k] === $scope.objetoGeral[i].pagamento[j].idPag) {
                                
                                idRepetido = 1 ;
                                break;
                            }
                        }
                        if (idRepetido === 1) {
                            continue;
                        }
                        
                        idPagVetor.push($scope.objetoGeral[i].pagamento[j].idPag);
                    }
                    
                    idPagRetorno = $scope.objetoGeral[i].pagamento[j].idPag;
                    
                    finalizadora = $scope.objetoGeral[i].pagamento[j].tPag;
                    valorFi = dinheiroFil + debiteFil + crediteFil + convenioFil;
                    
                    if (filial === 0) {
                        filial = $scope.objetoGeral[i].modCup.cpFilial;

                    } else if (filial !== $scope.objetoGeral[i].modCup.cpFilial) {

                        labelsLojaAux.push(filial);
                        dataLojaAux.push(valorFi);

                        filial = $scope.objetoGeral[i].modCup.cpFilial;
                        dinheiroFil = 0;
                        debiteFil = 0;
                        crediteFil = 0;
                        convenioFil = 0;
                    }

                    if (finalizadora === "01") {
                        dinheiro += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                        dinheiroFil += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);

                    } else if (finalizadora === "04") {
                        
                        debite += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                        debiteFil += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                    } else if (finalizadora === "03") {
                        
                        credite += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                        crediteFil += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                    } else if (finalizadora === "99") {
                        
                        convenio += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                        convenioFil += parseFloat($scope.objetoGeral[i].pagamento[j].vPag);
                    }
                }

            }

        }

        //Insere a ultima filial no array 
        labelsLojaAux.push(filial);
        dataLojaAux.push(valorFi);

        //Variaveis do grafico da Venda.
        $scope.labels = ['Dinheiro', 'Cartão Debito', 'Cartão Credito', 'Convenio'];
        $scope.data = [
            [dinheiro, debite, credite, convenio]
        ];

        //Chama a função que alimenta os graficos
        $scope.ordenaVetorGrafic(labelsLojaAux, dataLojaAux);

        //Soma o total da venda do dia, que foi realizado a consulta.
        total = dinheiro + debite + credite + convenio;
        $scope.somaTotalDia = total;
        $scope.dataVendaDia = consulta.data;
    };

    //Ordena e preenche a função do grafico
    $scope.ordenaVetorGrafic = function (loja, valores) {

        var lojaAux = "";
        var valorAux = "";

        var loja1 = [];
        var valor1 = [];
        
        var valorGrafico = [valor1];

        //Ordena o vetor
        for (var i = 0; i < valores.length; i++) {
            for (var j = 0; j < valores.length; j++) {
                if (valores[i] > valores[j]) {
                    lojaAux = loja[i];
                    valorAux = valores[i];

                    loja[i] = loja[j];
                    valores[i] = valores[j];

                    loja[j] = lojaAux;
                    valores[j] = valorAux;
                }
            }
        }

        //Limita o vetor a 10 filiais.
        for (var i = 0; i < valores.length; i++) {
            if (i < 10) {
                loja1[i] = loja[i];
                valor1[i] = valores[i];
            } else {
                break;
            }
        }
        
        
        //Coloca o vetor no padrão do grafico
        for (var i = 0; i < valor1.length; i++) {
            valorGrafico[0][i] = valor1[i];
        }
        
        
        $scope.labelsLoja = loja1;
        $scope.dataLoja = valorGrafico;
        
    };

    //Solicita as informações pela data para montar a pagina compactada
    $scope.solicitaDataCompactada = function (consulta) {
        var validos = 0;
        var contigencia = 0;
        var filial = 0;
        var objetoCompactoAux = [];
        var aux = [];
        var data = "";

        if (consulta.data === "") {
            data = Time();
        } else {
            data = DataBanco(consulta);
        }

        $http.get(urlBase + "consultadata/", {params: {
                data: data,
                validos: consulta.validos,
                contigencia: consulta.contigencia,
                filia: consulta.tx_filial,
                caixa: consulta.tx_caixa
            }}).then(function (response) {
            $scope.objeto = response.data;

            if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }

            for (var i = 0; i < $scope.objeto.length; i++) {

                if ($scope.objeto[i].cpFilial === null) {
                    continue;
                }

                if (i === 0) {
                    filial = $scope.objeto[i].cpFilial;
                    if ($scope.objeto[i].status === 0) {
                        contigencia++;
                    } else {
                        validos++;
                    }
                } else {
                    if ($scope.objeto[i].cpFilial === filial) {
                        if ($scope.objeto[i].status === 0) {
                            contigencia++;
                        } else {
                            validos++;
                        }
                    } else {
                        objetoCompactoAux.cpFilial = filial;
                        objetoCompactoAux.cpValidados = validos;
                        objetoCompactoAux.cpContigencia = contigencia;
                        objetoCompactoAux.cpErro = 0;
                        aux.push(objetoCompactoAux);
                        contigencia = 0;
                        validos = 0;
                        filial = $scope.objeto[i].cpFilial;
                        objetoCompactoAux = [];
                        if ($scope.objeto[i].status === 0) {
                            contigencia++;
                        } else {
                            validos++;
                        }
                    }
                }
            }

            objetoCompactoAux.cpFilial = filial;
            objetoCompactoAux.cpValidados = validos;
            objetoCompactoAux.cpContigencia = contigencia;
            objetoCompactoAux.cpErro = 0;
            aux.push(objetoCompactoAux);
            $scope.objetoCompacto = aux;
            $scope.totalItemsCompac = $scope.objetoCompacto.length;

            if ($scope.objetoCompacto[0].cpFilial === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
            }
        });
    };


    //Conta a quantidade de validados no retorno
    $scope.incrementValidos = function () {
        $scope.qtdValidos = $scope.objeto;
        var incre = 0;
        for (var i = 0; i < $scope.qtdValidos.length; i++) {
            if ($scope.qtdValidos[i].status === 1) {
                incre++;
            }
        }

        return incre;
    };

    //Conta a quantidade de contigencia no retorno
    $scope.incrementContige = function () {
        $scope.qtdContigen = $scope.objeto;
        var incre = 0;

        for (var i = 0; i < $scope.qtdContigen.length; i++) {
            if ($scope.qtdContigen[i].status === 0) {
                incre++;
            }
        }

        return incre;
    };

    $scope.incrementCancelados = function () {
        $scope.qtdCancelado = $scope.objeto;
        var incre = 0;

        for (var i = 0; i < $scope.qtdCancelado.length; i++) {
            if ($scope.qtdCancelado[i].status === 2) {
                incre++;
            }
        }

        return incre;
    };

    //limpa os inputs
    $scope.novaConsulta = function () {
        $scope.consulta.tx_caixa = "";
        $scope.consulta.tx_filial = "";
        $scope.consulta.tx_coo = "";
        $scope.consulta.tx_numNfce = "";
        $scope.consulta.data = "";
        $scope.retornoConsulta = "";
        $scope.alimentaTable = $scope.objeto;
        $scope.totalItems = $scope.alimentaTable.length;
        $scope.totalItemsCompac = $scope.objetoCompacto.length;
    };

    //filtra tabela por filiais
    $scope.filtraFilial = function (objeto, consulta) {
        var objetoAux = [];

        if (angular.isUndefined(consulta.tx_filial)) {
            alert("Campo permite apenas numeros");
            consulta.tx_filial = "";
            return;
        }

        for (var i = 0; i < objeto.length; i++) {
            if ($scope.trimleft(objeto[i].cpFilial, "0") === $scope.trimleft(consulta.tx_filial, "0")) {
                objetoAux.push(objeto[i]);
            }
        }
        $scope.alimentaTable = objetoAux;
        $scope.totalItems = $scope.alimentaTable.length;
    };

    //filtra tabela por Caixa.
    $scope.filtraCaixa = function (objeto, consulta) {
        var objetoAux = [];

        if (angular.isUndefined(consulta.tx_caixa)) {
            alert("Campo permite apenas numeros");
            consulta.tx_caixa = "";
            return;
        }

        for (var i = 0; i < objeto.length; i++) {
            if ($scope.trimleft(objeto[i].cpCaixa, "0") === $scope.trimleft(consulta.tx_caixa, "0")) {
                objetoAux.push(objeto[i]);
            }
        }
        $scope.alimentaTable = objetoAux;
        $scope.totalItems = $scope.alimentaTable.length;
    };

    //filtra tabela por Coo
    $scope.filtraCoo = function (objeto, consulta) {
        var objetoAux = [];

        if (angular.isUndefined(consulta.tx_coo)) {
            alert("Campo permite apenas numeros");
            consulta.tx_coo = "";
            return;
        }

        for (var i = 0; i < objeto.length; i++) {
            if ($scope.trimleft(objeto[i].cpCoo, "0") === $scope.trimleft(consulta.tx_coo, "0")) {
                objetoAux.push(objeto[i]);
            }
        }
        $scope.alimentaTable = objetoAux;
        $scope.totalItems = $scope.alimentaTable.length;
    };

    //filtra tabela pelo numero da nfce
    $scope.filtraNumNfce = function (objeto, consulta) {
        var objetoAux = [];

        if (angular.isUndefined(consulta.tx_numNfce)) {
            alert("Campo permite apenas numeros");
            consulta.tx_numNfce = "";
            return;
        }

        for (var i = 0; i < objeto.length; i++) {
            if ($scope.trimleft(objeto[i].cpNfce, "0") === $scope.trimleft(consulta.tx_numNfce, "0")) {
                objetoAux.push(objeto[i]);
            }
        }
        $scope.alimentaTable = objetoAux;
        $scope.totalItems = $scope.alimentaTable.length;
    };

    //Trimleft para numeros apenas
    $scope.trimleft = function (str, caracter) {
        var retorno = "";
        var charIni = 0;

        for (var i = 0; i < str.length; i++) {
            if (i === 0) {
                charIni = caracter;
            }

            if (charIni === str[i]) {
                continue;
            } else {
                charIni = '#';
                retorno = retorno + str[i];
            }
        }

        return retorno;
    };

    $scope.montaPdf = function (dados) {

        var numNfce = dados.cpNfce;

        while (numNfce.length < 9) {
            numNfce = "0" + numNfce;
        }

        var diretorio = dirNfce;
        diretorio = diretorio.concat("pdf/");
        diretorio = diretorio.concat(dados.cpFilial);
        diretorio = diretorio.concat(dados.cpCaixa);
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat(dados.cpData.replace("-", "").replace("-", ""));
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat("nfce_");
        diretorio = diretorio.concat(dados.cpData.replace("-", "").replace("-", ""));
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpFilial);
        diretorio = diretorio.concat(dados.cpCaixa);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpCoo);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(numNfce);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpNumnfce);
        diretorio = diretorio.concat("-nfe.pdf");

        return diretorio;
    };

    $scope.montaXml = function (dados) {

        var numNfce = dados.cpNfce;

        while (numNfce.length < 9) {
            numNfce = "0" + numNfce;
        }

        var diretorio = dirNfce;
        diretorio = diretorio.concat("xml/");
        diretorio = diretorio.concat(dados.cpFilial);
        diretorio = diretorio.concat(dados.cpCaixa);
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat(dados.cpData.replace("-", "").replace("-", ""));
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat("xml");
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat("nfce_");
        diretorio = diretorio.concat(dados.cpData.replace("-", "").replace("-", ""));
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpFilial);
        diretorio = diretorio.concat(dados.cpCaixa);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpCoo);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(numNfce);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpNumnfce);
        diretorio = diretorio.concat("-nfe.xml");

        return diretorio;
    };

    $scope.montaTxt = function (dados) {

        var numNfce = dados.cpNfce;

        while (numNfce.length < 8) {
            numNfce = "0" + numNfce;
        }

        var diretorio = dirNfce;
        diretorio = diretorio.concat("txt/");
        diretorio = diretorio.concat(dados.cpFilial);
        diretorio = diretorio.concat(dados.cpCaixa);
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat(dados.cpData.replace("-", "").replace("-", ""));
        diretorio = diretorio.concat("/");
        diretorio = diretorio.concat("nfce_");
        diretorio = diretorio.concat(dados.cpData.replace("-", "").replace("-", ""));
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpFilial);
        diretorio = diretorio.concat(dados.cpCaixa);
        diretorio = diretorio.concat("_");
        diretorio = diretorio.concat(dados.cpCoo);
        diretorio = diretorio.concat(".txt");

        return diretorio;
    };

    $scope.validaLink = function (urlConsulta) {
        $http.get(urlConsulta).
                then(function mySuccess(response) {
                    window.open(urlConsulta);
                },
                        function myError(response) {
                            window.alert("Arquivo não Encontrado.");
                        });
    };
});

//Pega a data do dia.
function Time() {
    var date = new Date();
    var dia = date.getDate();
    var mes = date.getMonth() + 1;
    var ano = date.getFullYear();

    if (dia < 10) {
        dia = "0" + dia;
    }

    if (mes < 10) {
        mes = "0" + mes;
    }

    return "" + ano + "-" + mes + "-" + dia;
    //return "2017-06-26";
}

//Coloca a data no padrão do banco.
function DataBanco(consulta) {
    var string = consulta.data;

    var dia = string.substring(0, 2);
    var mes = string.substring(3, 5);
    var ano = string.substring(6, 10);

    var data = ano + "-" + mes + "-" + dia;

    return data;
}


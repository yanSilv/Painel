#!/usr/bin/wish
#
# roteador.tcl
#
# Realiza o roteamento de mensagens entre o PAF-ECF e a
# interface com o ERP.
#
# Copyright (C) 2013 M & F Tecnologia de Informacao Ltda ME.
# Todos os direitos reservados.
#

#
# Exibe a janela principal do programa.
#
proc show_main_window {} {
    global main_window_geometry transacoes_erp_pdf transacoes_erp_xml
    global hora watch_consultas_nfce

    # Cria a janela.
    toplevel .main

    # Configura titulo e eventos para a janela.
    wm title .main "ROTEADOR DE MENSAGENS"

    wm protocol .main WM_DELETE_WINDOW {exit}

    # Cria os componentes da janel.
    frame .main.frm1 -relief ridge -borderwidth 2 -background black
    pack .main.frm1 -padx 5 -pady 5 -expand 1 -fill both
    pack propagate .main.frm1 1

    frame .main.frm1.frm2 -background black
    frame .main.frm1.frm2.frm3 -background black
    frame .main.frm1.frm2.frm4 -background black
    frame .main.frm1.frm5 -background black
    

    if {($watch_consultas_nfce)} {
        label .main.frm1.frm2.lbl1 -text "Transacoes (NFCE):" -anchor nw -justify left -background black -foreground white
        label .main.frm1.frm2.frm3.lbl2 -text "PDF:" -anchor sw -justify left -background black -foreground red
        label .main.frm1.frm2.frm3.lbl3 -textvariable transacoes_erp_pdf -anchor se -justify right -background black -foreground red

        label .main.frm1.frm2.frm4.lbl4 -text "XML:" -anchor sw -justify left -background black -foreground green
        label .main.frm1.frm2.frm4.lbl5 -textvariable transacoes_erp_xml -anchor se -justify right -background black -foreground green

        label .main.frm1.frm5.lbl6 -text "Hora" -anchor nw -justify left -background black -foreground white
        label .main.frm1.frm5.lbl7 -textvariable hora -anchor se -justify right -background black -foreground yellow -font "Helvetica 22"
    }

    # Exibe os componentes na janel...
    pack .main.frm1.frm2 -padx 5 -pady 5 -side left -fill y
    pack .main.frm1.frm2.lbl1 -side top -fill x
    pack .main.frm1.frm2.frm3 -side top -fill x
    pack .main.frm1.frm2.frm3.lbl2 -expand 1 -side left -fill x
    pack .main.frm1.frm2.frm3.lbl3 -expand 1 -side left -fill x
    pack .main.frm1.frm2.frm4 -side top -fill x
    pack .main.frm1.frm2.frm4.lbl4 -expand 1 -side left -fill x
    pack .main.frm1.frm2.frm4.lbl5 -expand 1 -side left -fill x
    pack .main.frm1.frm5 -padx 5 -pady 5 -side left -fill both
    pack .main.frm1.frm5.lbl6 -side top -fill x
    pack .main.frm1.frm5.lbl7 -side top -fill x

    wm geometry .main $main_window_geometry
}

#
# para posterior consulta.
#
proc envia_nfce_txt_para_erp {} {
    global ontem hoje transacoes_erp_pdf transacoes_erp_xml hora watch_movimento watch_consultas watch_consultas_mfd watch_recebe_mfd watch_envia_mfd watch_backups
    global roteador_tmp_dir_path
    global paf_entrada_path paf_saida_path
    global retaguarda_movimento_path retaguarda_tef_path retaguarda_auditor_path retaguarda_copia_transacoes_path retaguarda_cache_nfce_path retaguarda_cache_cx_path retaguarda_cache_cx_consolidado_path retaguarda_arquivo_path retaguarda_entrada_path retaguarda_saida_path
    global erp_movimento_path erp_entrada_path erp_saida_path
    global hosts_list ftp_user_name ftp_password
    
    if {![file exists "/ecf2000_roteador/log"]} {
        catch {
            #puts "Criando /ecf2000_roteador/log"
            file mkdir "/ecf2000_roteador/log"
        }
    }

    # Obtem os arquivos MFDs enviados pelos caixas, convete-os em arquivos de
    # movimento de caixa e extrai os cupons individuais, contidos neles.
    set files [glob -nocomplain "${retaguarda_entrada_path}/nfce_*.txt"]
    set n_files [llength $files]

    if {$n_files > 0} {
        for {set i 0} {$i < $n_files} {incr i} {
            set source_file_name [lindex $files $i]
            #set target_backup_directory "${retaguarda_copia_transacoes_path}/[clock format [clock seconds] -format "%Y%m%d"]"
            #set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files $i]]"
            
            set file_name_parts [split [file tail $source_file_name] "_."]
            
            if {[llength $file_name_parts] == 5} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set intervalo [lindex $file_name_parts 3]
            }
            # Determina os nomes do diretorio e do arquivo de cache MFD...
            set target_cache_directory "${retaguarda_cache_nfce_path}/txt/$loja_caixa/${data_movimento}"
            if {[llength $file_name_parts] == 5} {
                set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_${intervalo}.txt"
            }
            
            set target_backup_directory "${retaguarda_copia_transacoes_path}/${data_movimento}/${loja_caixa}/txt"
            set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files $i]]"
            
            # Salva uma copia do arquivo para consultas futuras...
            # Cria o diretorio de backup, caso ele nao exista...
            if {![file exists $target_backup_directory]} {
                catch {
                    file mkdir $target_backup_directory
                }
            }
            
            # Cria o diretorio de cache, caso ele nao exista...
            if {![file exists $target_cache_directory]} {
                catch {
                    file mkdir $target_cache_directory
                }
            }
            
            #puts "\n---------------------NFCE-TXT--------------------------"
            
            # Copia os arquivos...
            set errno [catch {
                #puts "Copiando..."
                #puts "de  : $source_file_name"
                #puts "para: $target_cache_file_name"
                #puts "para: $target_backup_directory"
                #------------------------------------------#
                set qtdcar  [llength $source_file_name]
                #puts "qtd: $qtdcar"
                if { $qtdcar > 0} {

                    set open_file [open $source_file_name "r"]
                    set conteudo  [read $open_file]
                    close $open_file
                    
                    set diretorio_txt [open "$target_cache_file_name" w]
                    puts -nonewline $diretorio_txt $conteudo
                    close $diretorio_txt
                    
                    set diretorio_backup [open "${target_backup_directory}/[file tail [lindex $files $i]]" w]
                    puts -nonewline $diretorio_backup $conteudo
                    close $diretorio_backup
                }
                #------------------------------------------#
            }]
            
            # So apaga o arquivo original se conseguir transferi-lo para o destinatario...
            # Apaga o arquivo MFD...
            if {$errno == 0} {
                catch {
                    file delete -force $source_file_name
                }
                
                # Incrementa o numero de transacoes realizadas hoje...
                #incr transacoes_erp_pdf
            }
        }
    }
    
    # Salva o cache no disco...
    catch {
        exec sync
    }
}

#
# Armazena os arquivos de recuperacao de cupons, em formato MFD,
# para posterior consulta.
#
proc envia_nfce_pdf_para_erp {} {
    global ontem hoje transacoes_erp_pdf transacoes_erp_xml hora watch_movimento watch_consultas watch_consultas_mfd watch_recebe_mfd watch_envia_mfd watch_backups
    global roteador_tmp_dir_path
    global paf_entrada_path paf_saida_path
    global retaguarda_movimento_path retaguarda_tef_path retaguarda_auditor_path retaguarda_copia_transacoes_path retaguarda_cache_nfce_path retaguarda_cache_cx_path retaguarda_cache_cx_consolidado_path retaguarda_arquivo_path retaguarda_entrada_path retaguarda_saida_path
    global erp_movimento_path erp_entrada_path erp_saida_path
    global hosts_list ftp_user_name ftp_password
    
    if {![file exists "/ecf2000_roteador/log"]} {
        catch {
            #puts "Criando /ecf2000_roteador/log"
            file mkdir "/ecf2000_roteador/log"
        }
    }

    # Obtem os arquivos MFDs enviados pelos caixas, convete-os em arquivos de
    # movimento de caixa e extrai os cupons individuais, contidos neles.
    set files [glob -nocomplain "${retaguarda_entrada_path}/nfce_*.pdf"]
    set n_files [llength $files]

    if {$n_files > 0} {
        for {set i 0} {$i < $n_files} {incr i} {
            set source_file_name [lindex $files $i]
            #set target_backup_directory "${retaguarda_copia_transacoes_path}/[clock format [clock seconds] -format "%Y%m%d"]"
            #set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files $i]]"
            
            set file_name_parts [split [file tail $source_file_name] "_."]
            if {[llength $file_name_parts] == 7} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set intervalo [lindex $file_name_parts 3]
                set coo [lindex $file_name_parts 4]
                set chave [lindex $file_name_parts 5]
            }
            if {[llength $file_name_parts] == 5} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set intervalo [lindex $file_name_parts 3]
            } 

            # Determina os nomes do diretorio e do arquivo de cache MFD...
            set target_cache_directory "${retaguarda_cache_nfce_path}/pdf/$loja_caixa/${data_movimento}"
            if {[llength $file_name_parts] == 7} {
                set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_${intervalo}_${coo}_${chave}.pdf"
            }
            if {[llength $file_name_parts] == 5} {
                set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_${intervalo}.pdf"
            }
            
            set target_backup_directory "${retaguarda_copia_transacoes_path}/${data_movimento}/${loja_caixa}/pdf"
            set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files $i]]"
            
            # Salva uma copia do arquivo para consultas futuras...
            # Cria o diretorio de backup, caso ele nao exista...
            if {![file exists $target_backup_directory]} {
                catch {
                    file mkdir $target_backup_directory
                }
            }
            
            # Cria o diretorio de cache, caso ele nao exista...
            if {![file exists $target_cache_directory]} {
                catch {
                    file mkdir $target_cache_directory
                }
            }
            
            #puts "\n---------------------NFCE-PDF--------------------------"

            # Copia os arquivos...
            set errno [catch {
                #puts "Copiando..."
                #puts "de  : $source_file_name"
                #puts "para: $target_cache_file_name"
                exec cp $source_file_name $target_cache_file_name 2>> "log/roteador_io_nfce_pdf_errors.txt"
                exec cp $source_file_name $target_backup_directory 2>> "log/roteador_io_nfce_pdf_errors.txt"
            }]
            
            # So apaga o arquivo original se conseguir transferi-lo para o destinatario...
            # Apaga o arquivo MFD...
            if {$errno == 0} {
                catch {
                    file delete -force $source_file_name
                }                
                # Incrementa o numero de transacoes realizadas hoje...
                incr transacoes_erp_pdf
            }
        }
    }

    # Salva o cache no disco...
    catch {
        exec sync
    }
}


#
# Armazena os arquivos de recuperacao de cupons, em formato MFD,
# para posterior consulta.
#
proc envia_nfce_xml_para_erp {} {
    global ontem hoje transacoes_erp_pdf transacoes_erp_xml hora watch_movimento watch_consultas watch_consultas_mfd watch_recebe_mfd watch_envia_mfd watch_backups retaguarda_cache_nfce_path_web
    global roteador_tmp_dir_path
    global paf_entrada_path paf_saida_path
    global retaguarda_movimento_path retaguarda_tef_path retaguarda_auditor_path retaguarda_copia_transacoes_path retaguarda_cache_nfce_path retaguarda_cache_cx_path retaguarda_cache_cx_consolidado_path retaguarda_arquivo_path retaguarda_entrada_path retaguarda_saida_path
    global erp_movimento_path erp_entrada_path erp_saida_path
    global hosts_list ftp_user_name ftp_password
    
    if {![file exists "/ecf2000_roteador/log"]} {
        catch {
            #puts "Criando /ecf2000_roteador/log"
            file mkdir "/ecf2000_roteador/log"
        }
    }

    # Obtem os arquivos para a pasta de erro
    set files_erro [glob -nocomplain "${retaguarda_entrada_path}/erro-nfce_*.xml"]
    set n_files [llength $files_erro]

    if {$n_files > 0} {
        for {set i 0} {$i < $n_files} {incr i} {
            set source_file_name [lindex $files_erro $i]
           
            
            set file_name_parts [split [file tail $source_file_name] "_."]
            
            if {[llength $file_name_parts] == 7} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set numnfce [lindex $file_name_parts 3]
                set intervalo [lindex $file_name_parts 4]
                set chave [lindex $file_name_parts 5]
            }
            if {[llength $file_name_parts] == 6} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set intervalo [lindex $file_name_parts 3]
                set chave [lindex $file_name_parts 4]
            }

            set target_backup_directory "${retaguarda_copia_transacoes_path}/${data_movimento}/${loja_caixa}/xml_erro"
            set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files_erro $i]]"

            # Determina os nomes do diretorio e do arquivo de cache MFD...
            set target_cache_directory "${retaguarda_cache_nfce_path}/xml/$loja_caixa/${data_movimento}/xml_erro"
            if {[llength $file_name_parts] == 7} {
               set target_cache_file_name "${target_cache_directory}/erro-nfce_${data_movimento}_${loja_caixa}_${numnfce}_[pad ${intervalo} 9 0 "E"]_${chave}.xml"
            }

            if {[llength $file_name_parts] == 6} {
               set target_cache_file_name "${target_cache_directory}/erro-nfce_${data_movimento}_${loja_caixa}_[pad ${intervalo} 9 0 "E"]_${chave}.xml"
            }
            
            # Salva uma copia do arquivo para consultas futuras...
            # Cria o diretorio de backup, caso ele nao exista...
            if {![file exists $target_backup_directory]} {
                catch {
                    file mkdir $target_backup_directory
                }
            }
            
            # Cria o diretorio de cache, caso ele nao exista...
            if {![file exists $target_cache_directory]} {
                catch {
                    file mkdir $target_cache_directory
                }
            }
            
            #puts "\n---------------------NFCE-XML ERRO--------------------------"
            
            # Copia os arquivos...
            set errno [catch {
                #puts "NFCE-XML - Copiando..."
                #puts "de  : $source_file_name"
                #puts "para: $target_cache_file_name"
                #puts "para: $target_backup_file_name"
                exec cp $source_file_name $target_cache_file_name 2>> "log/roteador_io_nfce_xml_errors.txt"
                exec cp $source_file_name $target_backup_file_name 2>> "log/roteador_io_nfce_xml_errors.txt"
            }]
            
            # So apaga o arquivo original se conseguir transferi-lo para o destinatario...
            # Apaga o arquivo MFD...
            if {$errno == 0} {
                catch {
                    file delete -force $source_file_name
                }
                
                # Incrementa o numero de transacoes realizadas hoje...
                incr transacoes_erp_xml
            }
        }
    }

    # Obtem os arquivos para a pasta de contigencia
    set files_contigencia [glob -nocomplain "${retaguarda_entrada_path}/contigencia-nfce_*.xml"]
    set n_files [llength $files_contigencia]

    if {$n_files > 0} {
        for {set i 0} {$i < $n_files} {incr i} {
            set source_file_name [lindex $files_contigencia $i]
            set file_name_parts [split [file tail $source_file_name] "_."]

            if {[llength $file_name_parts] == 7} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set numnfce [lindex $file_name_parts 3]
                set intervalo [lindex $file_name_parts 4]
                set chave [lindex $file_name_parts 5]
            }

            if {[llength $file_name_parts] == 6} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set intervalo [lindex $file_name_parts 3]
                set chave [lindex $file_name_parts 4]
            }

            set target_backup_directory "${retaguarda_copia_transacoes_path}/${data_movimento}/${loja_caixa}/xml"
            set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files_contigencia $i]]"
            
            # Determina os nomes do diretorio e do arquivo de cache MFD...
            set target_cache_directory "${retaguarda_cache_nfce_path}/xml/$loja_caixa/${data_movimento}/xml"
            if {[llength $file_name_parts] == 7} {
               set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_${numnfce}_[pad ${intervalo} 9 0 "E"]_${chave}.xml"
            }

            if {[llength $file_name_parts] == 6} {
               set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_[pad ${intervalo} 9 0 "E"]_${chave}.xml"
            }

            # Salva uma copia do arquivo para consultas futuras...
            # Cria o diretorio de backup, caso ele nao exista...
            if {![file exists $target_backup_directory]} {
                catch {
                    file mkdir $target_backup_directory
                }
            }
            
            # Cria o diretorio de cache, caso ele nao exista...
            if {![file exists $target_cache_directory]} {
                catch {
                    file mkdir $target_cache_directory
                }
            }
            
            #puts "\n---------------------NFCE-XML CONTIGENCIA--------------------------"
            
            # Copia os arquivos...
            set errno [catch {
                #puts "NFCE-XML - Copiando..."
                #puts "de  : $source_file_name"
                #puts "para: $target_cache_file_name"
                #puts "para: $target_backup_file_name"
                exec cp $source_file_name $target_cache_file_name 2>> "log/roteador_io_nfce_xml_errors.txt"
                exec cp $source_file_name $target_backup_file_name 2>> "log/roteador_io_nfce_xml_errors.txt"
            }]
            
            # So apaga o arquivo original se conseguir transferi-lo para o destinatario...
            # Apaga o arquivo MFD...
            if {$errno == 0} {
                catch {
                    file delete -force $source_file_name
                }
                
                # Incrementa o numero de transacoes realizadas hoje...
                incr transacoes_erp_xml
            }
        }
    }

    # Obtem os arquivos para a pasta de validos
    set files_valido [glob -nocomplain "${retaguarda_entrada_path}/validado-nfce_*.xml"]
    set n_files [llength $files_valido]

    if {$n_files > 0} {
        for {set i 0} {$i < $n_files} {incr i} {
            set source_file_name [lindex $files_valido  $i]
            
            set file_name_parts [split [file tail $source_file_name] "_."]
            
            if {[llength $file_name_parts] == 7} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set numnfce [lindex $file_name_parts 3]
                set intervalo [lindex $file_name_parts 4]
                set chave [lindex $file_name_parts 5]
            }

            if {[llength $file_name_parts] == 6} {
                set prefixo [lindex $file_name_parts 0]
                set data_movimento [lindex $file_name_parts 1]
                set loja_caixa [lindex $file_name_parts 2]
                set intervalo [lindex $file_name_parts 3]
                set chave [lindex $file_name_parts 4]
            }

            set target_backup_directory "${retaguarda_copia_transacoes_path}/${data_movimento}/${loja_caixa}/xml"
            set target_backup_file_name "${target_backup_directory}/[file tail [lindex $files_valido $i]]"
            
            # Determina os nomes do diretorio e do arquivo de cache MFD...
            set target_cache_directory "${retaguarda_cache_nfce_path}/xml/$loja_caixa/${data_movimento}/xml"
            if {[llength $file_name_parts] == 7} {
               set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_${numnfce}_[pad ${intervalo} 9 0 "E"]_${chave}.xml"
            }

            if {[llength $file_name_parts] == 6} {
                set target_cache_file_name "${target_cache_directory}/nfce_${data_movimento}_${loja_caixa}_[pad ${intervalo} 9 0 "E"]_${chave}.xml"
            }            
            
            # Salva uma copia do arquivo para consultas futuras...
            # Cria o diretorio de backup, caso ele nao exista...
            if {![file exists $target_backup_directory]} {
                catch {
                    file mkdir $target_backup_directory
                }
            }
            
            # Cria o diretorio de cache, caso ele nao exista...
            if {![file exists $target_cache_directory]} {
                catch {
                    file mkdir $target_cache_directory
                }
            }
            
            #puts "\n---------------------NFCE-XML VALIDO --------------------------"
            
            # Copia os arquivos...
            set errno [catch {
                #puts "NFCE-XML - Copiando..."
                #puts "de  : $source_file_name"
                #puts "para: $target_cache_file_name"
                #puts "para: $target_backup_file_name"
                exec cp $source_file_name $target_cache_file_name 2>> "log/roteador_io_nfce_xml_errors.txt"
                exec cp $source_file_name $target_backup_file_name 2>> "log/roteador_io_nfce_xml_errors.txt"
            }]
            
            # So apaga o arquivo original se conseguir transferi-lo para o destinatario...
            # Apaga o arquivo MFD...

            if {$errno == 0} {
                catch {
            ##puts "apagando  : $source_file_name"
                    file delete -force $source_file_name
                }
                
                # Incrementa o numero de transacoes realizadas hoje...
                incr transacoes_erp_xml
            }
        }
    }
    
    # Salva o cache no disco...
    catch {
        exec sync
    }
}

#formata campo com carac passado
proc pad {stri tama {carac " "} {posi "D"}} {
    set padstr ""
    set stri   [string trim $stri]
    set tamstr [string length $stri]
    if {$tama > $tamstr} {
        set j [expr "$tama - $tamstr"]
        for {set i 1} {$i <= $j} {incr i} {
            append padstr $carac
        }
    } else {
        set tama [expr "$tama - 1"]
        set ret [string range $stri 0 $tama]
        return $ret
    }
    set tama [expr "$tama - 1"]
    set ret ""
    if {$posi == "D"} {
        append ret $stri $padstr
    } else {
        append ret $padstr $stri
    }
    set ret [string range $ret 0 $tama]
    return $ret
}

#
# Observa os diretorios do sistema, em busca de mensagens para
# serem roteadas entre o PAF-ECF e a interface com o ERP.
#
proc watch_dirs {} {
    global ontem hoje transacoes_erp_pdf transacoes_erp_xml hora watch_movimento watch_consultas watch_consultas_mfd watch_consultas_nfce watch_recebe_mfd watch_envia_mfd watch_backups
    global roteador_tmp_dir_path
    global paf_entrada_path paf_saida_path
    global retaguarda_movimento_path retaguarda_tef_path retaguarda_auditor_path retaguarda_copia_transacoes_path retaguarda_cache_nfce_path retaguarda_cache_cx_path retaguarda_cache_cx_consolidado_path retaguarda_arquivo_path retaguarda_entrada_path retaguarda_saida_path
    global erp_movimento_path erp_entrada_path erp_saida_path
    global hosts_list ftp_user_name ftp_password

    # Exibe a hora atual...
    if {$watch_movimento && $watch_consultas} {
        set hora [clock format [clock seconds] -format "%H:%M"]
    } elseif {$watch_movimento && !($watch_consultas)} {
        set hora [clock format [clock seconds] -format "%H:%M"]
    } elseif {!($watch_movimento) && $watch_consultas} {
        set hora [clock format [clock seconds] -format "%d/%m"]
    } elseif {!($watch_movimento && $watch_consultas) && $watch_consultas_mfd} {
        set hora [clock format [clock seconds] -format "%Y"]
    } else {
        set hora [clock format [clock seconds] -format "%H:%M"]
    }

    # Observa a chegada de mensagens de consulta XML e PDF...
    if {$watch_consultas_nfce} {
        envia_nfce_txt_para_erp
        envia_nfce_pdf_para_erp
        envia_nfce_xml_para_erp
    }
        
    # Entra no loop de eventos do TK...
    update

    # Agenda o processamento das transacoes...
    after 500 watch_dirs
}

#
# Inicia o programa.
#

# Posicao default para a janela.
set main_window_geometry "+0+0"

# Configura o watcher para observar todos os tipos de mensagens...
set watch_movimento 1
set watch_consultas 1
set watch_consultas_mfd 1
set watch_consultas_nfce 1
set watch_recebe_mfd 0
set watch_envia_mfd 0
set watch_backups 1

# Obtem os parametros da linha de comando...
set i 0
while {$i < $argc} {
    set arg [lindex $argv $i]

    if {$arg == "--help"} {
        #puts "Copyright (C) 2013 M & F Tecnologia de Informacao Ltda ME."
        #puts "Todos os direitos reservados.\n"
        #puts "Sintaxe: roteador.tcl \[opcoes\]\n"
        #puts "--nfce           habilita somente a observacao de mens. de consulta MFD."
        exit
    } elseif {$arg == "--nfce"} {
        set watch_consultas 0
        set watch_movimento 0
        set watch_consultas_mfd 0
        set watch_backups 0
        set watch_consultas_nfce 1
    } elseif {$arg == "--posicao"} {
        incr i
        set main_window_geometry [lindex $argv $i]
    }

    incr i
}

# Contem o numero de transacoes realizadas entre o PAF e
# o ERP.
set transacoes_erp_pdf 0
set transacoes_erp_xml 0

# Caminhos para os diretorios de troca de mensagens entre o PAF
# e a interface com o ERP.
set retaguarda_cache_nfce_path "/matriz/painelnfce/nfce"
set retaguarda_cache_nfce_path_web "/ecf2000_roteador/retaguarda/nfce/consulta_nfce"
#set paf_entrada_path "/ecf2000/recebe"
#set paf_saida_path "/ecf2000/transmite"

set retaguarda_movimento_path "/ecf2000_retaguarda/recebe"
set retaguarda_tef_path "/ecf2000_retaguarda/tef"
set retaguarda_auditor_path "/ecf2000_roteador/retaguarda/auditor"
set retaguarda_copia_transacoes_path "/matriz/retaguarda/backup"
set retaguarda_cache_mfd_path "/ecf2000_roteador/retaguarda/cache"
set retaguarda_cache_cx_path "/matriz/retaguarda/cache_cx"
set retaguarda_cache_cx_consolidado_path "/matriz/retaguarda/cache_cx_consolidado"
set retaguarda_arquivo_path "/matriz/retaguarda/arquivo"
set retaguarda_entrada_path "/ecf2000_retaguarda/recebe"
set retaguarda_saida_path "/ecf2000_monitor/transmite"

set hosts_list {
    {001 01 127.0.0.1}
}

# Carrega o arquivo de configuracoes...
catch {
    #source "[file dirname $::argv0]/roteador.cfg"
}

puts "Versao 1.0.3 Layout Duplo."
# Oculta a janela do console...
wm withdraw .

# Exibe a janela principal do programa...
show_main_window

set ontem [clock format [clock seconds] -format "%D"]
set hoje [clock format [clock seconds] -format "%D"]

set transacoes 0
set hora [clock format [clock seconds] -format "%H:%M"]

# Agenda o processamento das transacoes...
after 500 watch_dirs

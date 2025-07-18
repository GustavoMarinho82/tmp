#!/bin/bash
# SCRIPT PARA INICIAR O PROGRAMA NUM SISTEMA LINUX

diretorio_raiz=$(dirname "$0")
cd "$diretorio_raiz"
java -cp "." P4nX

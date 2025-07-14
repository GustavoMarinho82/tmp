package controlador;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import entidade.Biblioteca;
import entidade.excecao.*;
import gui.*;
import gui.navegacao.Telas;

public class Controlador implements ActionListener {
	// ATRIBUTOS
	private JanelaPrincipal janela;
	private Biblioteca biblioteca;
	
	private File arqUsuarios;
	private File arqLivros;
	
	private JFileChooser fc = new JFileChooser(".");
	
	// CONSTRUTOR
	public Controlador(JanelaPrincipal janela) {
		this.janela = janela;
	}
	
	// METODOS
	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = event.getActionCommand();

		switch (comando) {
			case "SELECIONAR ARQ USUARIOS":
				int resultado = fc.showOpenDialog(janela);
				
				if (resultado == JFileChooser.APPROVE_OPTION) {
					File arq = fc.getSelectedFile();
					
					if (arq.isFile() && arq.canRead() && arq.canWrite()) {
						arqUsuarios = arq;
						janela.getPainelInicial().alterarTextoArqUsuarios(arq.getName());
					}
				}
				
				break;
				
			case "SELECIONAR ARQ LIVROS":
				break;
				
			case "INICIAR BIBLIOTECA":
				break;
				
			case "SALVAR CADASTROS":
				salvarCadastros();
				break;
			
			case "CARREGAR CADASTROS":
				//dialogoCadastros("Carregar");
				break;
			
			default: 
				janela.trocarPainel(comando);
		}
	}
	
	private boolean arqDefinido(String arq) {
		return !(arq == null || arq.trim().isEmpty());
	}
	
	private void salvarCadastros() {
		Object[] opcoes = {"Usuarios", "Livros", "Ambos"};
		int escolha = exibirDialogo("Salvar", "Salvar os cadastros de: ", opcoes);

		try {	
			switch (escolha) {
				case 0:
					biblioteca.salvaArqUsu(arqUsuarios.getAbsolutePath());
					break;
					
				case 1:
					biblioteca.salvaArqLiv(arqLivros.getAbsolutePath());
					break;
					
				case 2:
					biblioteca.salvaArqUsu(arqUsuarios.getAbsolutePath());
					biblioteca.salvaArqLiv(arqLivros.getAbsolutePath());
					break;
			}
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de salvamento nao foi encontrado ou nao pode ser escrito!");
		
		} catch (IOException e) {
			exibirErro(e.getMessage());
		}
	}
	
	private int exibirDialogo(String titulo, String mensagem, Object[] opcoes) {
		return JOptionPane.showOptionDialog(janela, mensagem, titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
	}
	
	private void exibirErro(String mensagem) {
		JOptionPane.showMessageDialog(janela, mensagem, "ERRO", JOptionPane.ERROR_MESSAGE); 
	}
}

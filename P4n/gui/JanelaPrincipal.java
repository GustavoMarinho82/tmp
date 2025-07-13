package gui;

import java.awt.CardLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import gui.navegacao.Telas;
import entidade.Biblioteca;

public class JanelaPrincipal extends JFrame implements ActionListener {
	// ATRIBUTOS
	private JPanel paineis;
	private BufferedImage icone;
	
	private Biblioteca biblioteca;
	
	// CONSTRUTOR
	public JanelaPrincipal() {
		super("Gerenciador de Biblioteca");
		inicializarComponentes();
		
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(icone);

		setContentPane(paineis);
	}
	
	// METODOS
	private void inicializarComponentes() {
		paineis = new JPanel(new CardLayout());
		paineis.add(new PainelPrincipal(this), Telas.PRINCIPAL.toString());
		paineis.add(new PainelManutencao(this), Telas.MANUTENCAO.toString());
		
		try {
			icone = ImageIO.read(new File ("imagens/IconeLivro.png"));
		
		} catch (IOException e) {
			System.err.println("Nao foi possivel carregar o icone. \nMais detalhes: " + e);
		}
	}
		
	private void trocarPainel(String nomePainel) {
		CardLayout cl = (CardLayout) paineis.getLayout();
		cl.show(paineis, nomePainel);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = event.getActionCommand();
		
		switch (comando) {
			case "SALVAR CADASTROS":
				dialogoCadastros("Salvar");
			break;
			case "CARREGAR CADASTROS":
				dialogoCadastros("Carregar");
			break;
			default: trocarPainel(comando);
		}
	}
	
	private void dialogoCadastros(String acao) {
		Object[] options = {"Usuarios", "Livros", "Ambos"};
		
		int n = JOptionPane.showOptionDialog(null,
			acao + "os cadastro de: ",
			"",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]);
			
		System.out.println(n);
	}
}

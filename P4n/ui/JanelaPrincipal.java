package ui;

import java.awt.CardLayout;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class JanelaPrincipal extends JFrame implements ActionListener {
	// ATRIBUTOS
	private JPanel paineis;
	
	// CONSTRUTOR
	public JanelaPrincipal() {
		super("Gerenciador de Biblioteca");
		
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		try {
			setIconImage(ImageIO.read(new File ("icons/IconeLivro.png")));
		
		} catch (IOException e) {
			System.err.println("Nao foi possivel carregar o icone. \nMais detalhes: " + e);
		}
		
		inicializarSeletorPaineis();
		setContentPane(paineis);
	}
	
	// METODOS
	private void inicializarSeletorPaineis() {
		paineis = new JPanel(new CardLayout());
		paineis.add(new PainelPrincipal(this), Telas.PRINCIPAL.toString());
		//paineis.add(new PainelManutencao(this), MANUTENCAO);
	}
		
	private void trocarPainel(String nomePainel) {
		CardLayout cl = (CardLayout) paineis.getLayout();
		cl.show(paineis, nomePainel);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = event.getActionCommand();
		trocarPainel(comando);
	}
}

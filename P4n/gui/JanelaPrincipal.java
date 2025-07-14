package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import controlador.Controlador;
import gui.navegacao.Telas;

public class JanelaPrincipal extends JFrame {
	// ATRIBUTOS
	private Controlador controlador;
	private JPanel paineis;
	private BufferedImage icone;
	
	private PainelInicial painelInicial;
	private PainelPrincipal painelPrincipal;
	private PainelManutencao painelManutencao;
	
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
		controlador = new Controlador(this);
		painelInicial = new PainelInicial(controlador);
		painelPrincipal = new PainelPrincipal(controlador);
		painelManutencao = new PainelManutencao(controlador);
	
		paineis = new JPanel(new CardLayout());
		paineis.add(painelInicial, Telas.INICIAL.toString());
		paineis.add(painelPrincipal, Telas.PRINCIPAL.toString());
		paineis.add(painelManutencao, Telas.MANUTENCAO.toString());
		
		try {
			icone = ImageIO.read(new File ("imagens/IconeLivro.png"));
		
		} catch (IOException e) {
			System.err.println("Nao foi possivel carregar o icone. \nMais detalhes: " + e);
		}
	}
		
	public void trocarPainel(String nomePainel) {
		CardLayout cl = (CardLayout) paineis.getLayout();
		cl.show(paineis, nomePainel);
	}
	
	public PainelInicial getPainelInicial() {
		return painelInicial;
    }
}

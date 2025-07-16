package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import controle.Controlador;
import gui.tabela.*;
import gui.navegacao.Telas;

public class JanelaPrincipal extends JFrame {
	// ATRIBUTOS
	private JPanel paineis;
	private BufferedImage icone;
	
	private ModeloTabelaUsuarios modeloTabelaUsuarios;
	private ModeloTabelaLivros modeloTabelaLivros;
	private Controlador controlador;
		
	private PainelInicial painelInicial;
	private PainelPrincipal painelPrincipal;
	private PainelManutencao painelManutencao;
	private PainelCadastro painelCadastro;
	private PainelCadUsuario painelCadUsuario;
	private PainelCadLivro painelCadLivro;
	private PainelRelatorio painelRelatorio;
	
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
		modeloTabelaUsuarios = new ModeloTabelaUsuarios();
		modeloTabelaLivros = new ModeloTabelaLivros();
		controlador = new Controlador(this, modeloTabelaUsuarios, modeloTabelaLivros);
		
		painelInicial = new PainelInicial(controlador);
		painelPrincipal = new PainelPrincipal(controlador);
		painelManutencao = new PainelManutencao(controlador);
		painelCadastro = new PainelCadastro(controlador);
		painelCadUsuario = new PainelCadUsuario(controlador);
		painelCadLivro = new PainelCadLivro(controlador);
		painelRelatorio = new PainelRelatorio(controlador, modeloTabelaUsuarios, modeloTabelaLivros);
	
		paineis = new JPanel(new CardLayout());
		paineis.add(painelInicial, Telas.INICIAL.toString());
		paineis.add(painelPrincipal, Telas.PRINCIPAL.toString());
		paineis.add(painelManutencao, Telas.MANUTENCAO.toString());
		paineis.add(painelCadastro, Telas.CADASTRO.toString());
		paineis.add(painelCadUsuario, Telas.CAD_USUARIO.toString());
		paineis.add(painelCadLivro, Telas.CAD_LIVRO.toString());
		paineis.add(painelRelatorio, Telas.RELATORIO.toString());
		
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
    
    public PainelManutencao getPainelManutencao() {
		return painelManutencao;
    }
        
	public PainelCadUsuario getPainelCadUsuario(){
		return painelCadUsuario;
	}
	
	public PainelCadLivro getPainelCadLivro() {
		return painelCadLivro;
	}
}

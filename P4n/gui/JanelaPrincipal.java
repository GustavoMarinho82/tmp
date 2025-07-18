package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import controle.Controlador;
import gui.tabela.*;
import gui.navegacao.Tela;

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
	private PainelCadastroUsuario painelCadastroUsuario;
	private PainelCadastroLivro painelCadastroLivro;
	private PainelEmprestimoDevolucao painelEmprestimoDevolucao;
	private PainelRelatorio painelRelatorio;
	
	// CONSTRUTOR
	public JanelaPrincipal() {
		super("Gerenciador de Biblioteca");
		inicializarComponentes();
		
		setSize(600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(icone);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		painelCadastroUsuario = new PainelCadastroUsuario(controlador);
		painelCadastroLivro = new PainelCadastroLivro(controlador);
		painelEmprestimoDevolucao = new PainelEmprestimoDevolucao(controlador);
		painelRelatorio = new PainelRelatorio(controlador, modeloTabelaUsuarios, modeloTabelaLivros);
	
		paineis = new JPanel(new CardLayout());
		paineis.add(painelInicial, Tela.INICIAL.toString());
		paineis.add(painelPrincipal, Tela.PRINCIPAL.toString());
		paineis.add(painelManutencao, Tela.MANUTENCAO.toString());
		paineis.add(painelCadastro, Tela.CADASTRO.toString());
		paineis.add(painelCadastroUsuario, Tela.CADASTRO_USUARIO.toString());
		paineis.add(painelCadastroLivro, Tela.CADASTRO_LIVRO.toString());
		paineis.add(painelEmprestimoDevolucao, Tela.EMPRESTIMO_DEVOLUCAO.toString());
		paineis.add(painelRelatorio, Tela.RELATORIO.toString());
		
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
        
	public PainelCadastroUsuario getPainelCadastroUsuario(){
		return painelCadastroUsuario;
	}
	
	public PainelCadastroLivro getPainelCadastroLivro() {
		return painelCadastroLivro;
	}
	
	public PainelEmprestimoDevolucao getPainelEmprestimoDevolucao() {
		return painelEmprestimoDevolucao;
	}
}

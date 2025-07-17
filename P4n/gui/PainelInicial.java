package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.navegacao.Telas;

public class PainelInicial extends JPanel {
	// ATRIBUTOS
	private JLabel textoPolitica;
	private JLabel textoArqs;
	private JLabel textoArqUsuarios;
	private JLabel textoArqLivros;
	
	private JRadioButton botaoSim;
	private JRadioButton botaoNao;
	private ButtonGroup botoesPolitica;
	private JButton botaoArqUsuarios;
	private JButton botaoArqLivros;
	private JButton botaoIniciar;
	
	private JPanel painel;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelInicial(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		textoPolitica = new JLabel("Iniciar com uma politica personalizada (config.properties):");
		textoArqs = new JLabel("Carregar dados dos arquivos de cadastros (opcional):");
		textoArqUsuarios = new JLabel("Arquivo: Nao definido");
		textoArqLivros = new JLabel("Arquivo: Nao definido");
		
		botaoSim = new JRadioButton("Sim");
		botaoNao = new JRadioButton("Nao", true);
		botoesPolitica = new ButtonGroup();
		botoesPolitica.add(botaoSim);
		botoesPolitica.add(botaoNao);
		
		botaoArqUsuarios = new JButton("Usuarios");
		botaoArqLivros = new JButton("Livros");
		botaoIniciar = new JButton("Iniciar Biblioteca");
		
		painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
		
		botaoArqUsuarios.addActionListener(al);
		botaoArqLivros.addActionListener(al);
		botaoIniciar.addActionListener(al);
		
		botaoArqUsuarios.setActionCommand("SELECIONAR ARQ USUARIOS");
		botaoArqLivros.setActionCommand("SELECIONAR ARQ LIVROS");
		botaoIniciar.setActionCommand("INICIAR BIBLIOTECA");
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fontePadrao = new Font("Monospaced", Font.PLAIN, 12);
		
		textoPolitica.setFont(fontePadrao);
		textoArqs.setFont(fontePadrao);
		textoArqUsuarios.setFont(fontePadrao);
		textoArqLivros.setFont(fontePadrao);
		botaoSim.setFont(fontePadrao);
		botaoNao.setFont(fontePadrao);
		botaoArqUsuarios.setFont(fontePadrao);
		botaoArqLivros.setFont(fontePadrao);
		botaoIniciar.setFont(new Font("Monospaced", Font.PLAIN, 18));
		
		// DIMENSOES
		Dimension tamanhoBotoesArqs = new Dimension(100, 25);
		
		botaoArqUsuarios.setPreferredSize(tamanhoBotoesArqs);
		botaoArqLivros.setPreferredSize(tamanhoBotoesArqs);
		botaoIniciar.setPreferredSize(new Dimension(350, 50));
		
		// BORDAS
		EmptyBorder bordaBotoes = new EmptyBorder(0, 10, 10, 0);
		
		textoPolitica.setBorder(new EmptyBorder(0, 0, 10, 0));
		botaoSim.setBorder(bordaBotoes);
		botaoNao.setBorder(bordaBotoes);
		textoArqs.setBorder(new EmptyBorder(10, 0, 7, 0));
	}
	
	private void montarLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(30, 30, 10, 30));

		painel.add(textoPolitica);
		painel.add(botaoSim);
		painel.add(botaoNao);
		painel.add(textoArqs);
		painel.add(criarPainelArq(botaoArqUsuarios, textoArqUsuarios));
		painel.add(criarPainelArq(botaoArqLivros, textoArqLivros));
		
		add(painel, BorderLayout.NORTH);
		add(enveloparBotao(botaoIniciar), BorderLayout.SOUTH);
	}	
		
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao);
		return painel;
	}
	
	private JPanel criarPainelArq(JButton botao, JLabel texto) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		painel.add(botao);
		painel.add(texto);
		
		return painel;
	}

	public void alterarTextoArqUsuarios(String nomeArq) {
		textoArqUsuarios.setText("Arquivo: " + nomeArq);
	}

	public void alterarTextoArqLivros(String nomeArq) {
		textoArqLivros.setText("Arquivo: " + nomeArq);
	}
	
	public boolean ativadaPoliticaPersonalizada() {
		return botaoSim.isSelected();
	}
}

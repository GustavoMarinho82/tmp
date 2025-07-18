package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controle.Acao;
import gui.navegacao.Tela;

public class PainelManutencao extends JPanel {
	// ATRIBUTOS
	private JLabel titulo;
	private JLabel textoArqUsuarios;
	private JLabel textoArqLivros;
	
	private JButton botaoSalvar;
	private JButton botaoCarregar;
	private JButton botaoSelecionar;
	private JButton botaoVoltar;
	
	private JPanel painelPrincipal;
	private JPanel painelTextos;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelManutencao(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("MANUTENCAO");
		textoArqUsuarios = new JLabel("Arquivo de Usuarios: Nao definido");
		textoArqLivros = new JLabel("Arquivo de Livros: Nao definido");
		
		botaoSalvar = new JButton("Salvar Cadastros");
		botaoCarregar = new JButton("Carregar Cadastros");
		botaoSelecionar = new JButton("Selecionar Arquivos");
		botaoVoltar = new JButton("Voltar");
		
		painelPrincipal = new JPanel(new GridLayout(5, 1));
		painelTextos = new JPanel();
		painelTextos.setLayout(new BoxLayout(painelTextos, BoxLayout.Y_AXIS));
		
		botaoSalvar.addActionListener(al);
		botaoCarregar.addActionListener(al);
		botaoSelecionar.addActionListener(al);
		botaoVoltar.addActionListener(al);
		
		botaoSalvar.setActionCommand(Acao.SALVAR_CADASTROS.toString());
		botaoCarregar.setActionCommand(Acao.CARREGAR_CADASTROS.toString());
		botaoSelecionar.setActionCommand(Acao.SELECIONAR_ARQUIVOS.toString());
		botaoVoltar.setActionCommand(Tela.PRINCIPAL.toString());
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fonteTextos = new Font("Monospaced", Font.PLAIN, 16);
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
		textoArqUsuarios.setFont(fonteTextos);
		textoArqLivros.setFont(fonteTextos);
		botaoSalvar.setFont(fonteBotoes);
		botaoCarregar.setFont(fonteBotoes);
		botaoSelecionar.setFont(fonteBotoes);
		botaoVoltar.setFont(fonteBotoes);
		
		// DIMENSOES
		Dimension tamanhoBotoes = new Dimension(350, 50);
		
		botaoSalvar.setPreferredSize(tamanhoBotoes);
		botaoCarregar.setPreferredSize(tamanhoBotoes);
		botaoSelecionar.setPreferredSize(tamanhoBotoes);
		botaoVoltar.setPreferredSize(tamanhoBotoes);
		
		// BORDAS
		titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
		textoArqUsuarios.setBorder(new EmptyBorder(10, 0, 0, 0));
		textoArqLivros.setBorder(new EmptyBorder(5, 0, 0, 0));
		
		// ALINHAMENTOS
		titulo.setHorizontalAlignment(JLabel.CENTER);
		textoArqUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoArqLivros.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void montarLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 0, 10, 0));

		painelPrincipal.add(titulo);
		painelPrincipal.add(enveloparBotao(botaoSalvar));
		painelPrincipal.add(enveloparBotao(botaoCarregar));
		painelPrincipal.add(enveloparBotao(botaoSelecionar));
		painelTextos.add(textoArqUsuarios);
		painelTextos.add(textoArqLivros);
		painelPrincipal.add(painelTextos);
		
		add(painelPrincipal, BorderLayout.NORTH);
		add(enveloparBotao(botaoVoltar), BorderLayout.SOUTH);
	}
	
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao);
		return painel;
	}
	
	public void alterarTextoArqUsuarios(String nomeArq) {
		textoArqUsuarios.setText("Arquivo de Usuarios: " + nomeArq);
	}

	public void alterarTextoArqLivros(String nomeArq) {
		textoArqLivros.setText("Arquivo de Livros: " + nomeArq);
	}
}

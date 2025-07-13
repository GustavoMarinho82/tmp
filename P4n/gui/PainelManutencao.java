package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.navegacao.Telas;

public class PainelManutencao extends JPanel {
	// ATRIBUTOS
	private JLabel titulo;
	private JButton botaoSalvar;
	private JButton botaoCarregar;
	private JButton botaoSelecionar;
	private JButton botaoVoltar;
	private JPanel painel;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelManutencao(ActionListener al) {
		super();
		
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		
		setLayout(new BorderLayout());
		add(painel, BorderLayout.NORTH);
		add(enveloparBotao(botaoVoltar), BorderLayout.SOUTH);
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("MANUTENCAO");
		botaoSalvar = new JButton("Salvar Cadastros");
		botaoCarregar = new JButton("Carregar Cadastros");
		botaoSelecionar = new JButton("Selecionar Arquivos");
		botaoVoltar = new JButton("Voltar");
		painel = new JPanel(new GridLayout(4, 1));
		
		botaoSalvar.addActionListener(al);
		botaoCarregar.addActionListener(al);
//		botaoSelecionar.addActionListener(al);
		botaoVoltar.addActionListener(al);
		
		botaoSalvar.setActionCommand("SALVAR CADASTROS");
		botaoCarregar.setActionCommand("CARREGAR CADASTROS");
//		botaoSelecionar.setActionCommand(Telas.EMPRESTIMO.toString());
		botaoVoltar.setActionCommand(Telas.PRINCIPAL.toString());
	}
	
	private void configurarComponentes() {
		Font fonteTitulo = new Font("Monospaced", Font.BOLD, 20);
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		titulo.setFont(fonteTitulo);
		botaoSalvar.setFont(fonteBotoes);
		botaoCarregar.setFont(fonteBotoes);
		botaoSelecionar.setFont(fonteBotoes);
		botaoVoltar.setFont(fonteBotoes);
		
		Dimension tamanhoBotoes = new Dimension(350, 50);
		
		botaoSalvar.setPreferredSize(tamanhoBotoes);
		botaoCarregar.setPreferredSize(tamanhoBotoes);
		botaoSelecionar.setPreferredSize(tamanhoBotoes);
		botaoVoltar.setPreferredSize(tamanhoBotoes);
		
		titulo.setHorizontalAlignment(JLabel.CENTER);
		titulo.setBorder(new EmptyBorder(25, 0, 25, 0));
		
		painel.add(titulo);
		painel.add(enveloparBotao(botaoSalvar));
		painel.add(enveloparBotao(botaoCarregar));
		painel.add(enveloparBotao(botaoSelecionar));
	}
    
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.setBorder(new EmptyBorder(0, 0, 10, 0));
		painel.add(botao);
		return painel;
	}
}

package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.navegacao.Telas;

public class PainelCadastro extends JPanel {
	// ATRIBUTOS
	private JLabel titulo;
	
	private JButton botaoCadUsuario;
	private JButton botaoCadLivro;
	private JButton botaoSalvar;
	private JButton botaoVoltar;
	
	private JPanel painel;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelCadastro(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("CADASTRO");
		
		botaoCadUsuario = new JButton("Cadastrar Usuario");
		botaoCadLivro = new JButton("Cadastrar Livro");
		botaoSalvar = new JButton("Salvar Cadastros");
		botaoVoltar = new JButton("Voltar");
		
		painel = new JPanel(new GridLayout(4, 1));
		
		botaoCadUsuario.addActionListener(al);
		botaoCadLivro.addActionListener(al);
		botaoSalvar.addActionListener(al);
		botaoVoltar.addActionListener(al);
		
		botaoCadUsuario.setActionCommand(Telas.CAD_USUARIO.toString());
		botaoCadLivro.setActionCommand(Telas.CAD_LIVRO.toString());
		botaoSalvar.setActionCommand("SALVAR CADASTROS");
		botaoVoltar.setActionCommand(Telas.PRINCIPAL.toString());
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
		botaoCadUsuario.setFont(fonteBotoes);
		botaoCadLivro.setFont(fonteBotoes);
		botaoSalvar.setFont(fonteBotoes);
		botaoVoltar.setFont(fonteBotoes);
		
		// DIMENSOES
		Dimension tamanhoBotoes = new Dimension(350, 50);
		
		botaoCadUsuario.setPreferredSize(tamanhoBotoes);
		botaoCadLivro.setPreferredSize(tamanhoBotoes);
		botaoSalvar.setPreferredSize(tamanhoBotoes);
		botaoVoltar.setPreferredSize(tamanhoBotoes);
		
		// BORDAS
		titulo.setBorder(new EmptyBorder(0, 0, 15, 0));

		// ALINHAMENTOS
		titulo.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void montarLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 0, 10, 0));

		painel.add(titulo);
		painel.add(enveloparBotao(botaoCadUsuario));
		painel.add(enveloparBotao(botaoCadLivro));
		painel.add(enveloparBotao(botaoSalvar));
		
		add(painel, BorderLayout.NORTH);
		add(enveloparBotao(botaoVoltar), BorderLayout.SOUTH);
	}
	
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao);
		return painel;
	}
}

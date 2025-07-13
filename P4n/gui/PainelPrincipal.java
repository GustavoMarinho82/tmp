package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.navegacao.Telas;

public class PainelPrincipal extends JPanel {
	// ATRIBUTOS
	private JLabel texto;
	private JButton botaoManutencao;
	private JButton botaoCadastro;
	private JButton botaoEmprestimo;
	private JButton botaoRelatorio;
	private JPanel painel;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelPrincipal(ActionListener al) {
		super();
		
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		
		setLayout(new BorderLayout());
		add(painel, BorderLayout.NORTH);
	}
	
	// METODOS
	private void inicializarComponentes() {
		texto = new JLabel("Voce deseja ir para qual secao?");
		botaoManutencao = new JButton("Manutencao");
		botaoCadastro = new JButton("Cadastro");
		botaoEmprestimo = new JButton("Emprestimo");
		botaoRelatorio = new JButton("Relatorio");
		painel = new JPanel(new GridLayout(5, 1));

		botaoManutencao.addActionListener(al);
		botaoCadastro.addActionListener(al);
		botaoEmprestimo.addActionListener(al);
		botaoRelatorio.addActionListener(al);
		
		botaoManutencao.setActionCommand(Telas.MANUTENCAO.toString());
		botaoCadastro.setActionCommand(Telas.CADASTRO.toString());
		botaoEmprestimo.setActionCommand(Telas.EMPRESTIMO.toString());
		botaoRelatorio.setActionCommand(Telas.RELATORIO.toString());
	}
	
	private void configurarComponentes() {
		Font fonteTexto = new Font("Monospaced", Font.BOLD, 20);
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		texto.setFont(fonteTexto);
		botaoManutencao.setFont(fonteBotoes);
		botaoCadastro.setFont(fonteBotoes);
		botaoEmprestimo.setFont(fonteBotoes);
		botaoRelatorio.setFont(fonteBotoes);
		
		Dimension tamanhoBotoes = new Dimension(350, 50);
		
		botaoManutencao.setPreferredSize(tamanhoBotoes);
		botaoCadastro.setPreferredSize(tamanhoBotoes);
		botaoEmprestimo.setPreferredSize(tamanhoBotoes);
		botaoRelatorio.setPreferredSize(tamanhoBotoes);
		
		texto.setHorizontalAlignment(JLabel.CENTER);
		texto.setBorder(new EmptyBorder(25, 0, 25, 0));
		
		painel.add(texto);
		painel.add(enveloparBotao(botaoManutencao));
		painel.add(enveloparBotao(botaoCadastro));
		painel.add(enveloparBotao(botaoEmprestimo));
		painel.add(enveloparBotao(botaoRelatorio));
	}
    
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.setBorder(new EmptyBorder(0, 0, 10, 0));
		painel.add(botao);
		return painel;
	}
}

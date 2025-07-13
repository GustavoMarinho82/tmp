package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PainelManutencao extends JPanel {
	// COMPONENTES
	private JLabel texto;
	private JButton botaoManutencao;
	private JButton botaoCadastro;
	private JButton botaoEmprestimo;
	private JButton botaoRelatorio;
	
	// CONSTRUTOR
	public PainelManutencao() {
		super();
		inicializarComponentes();
		configurarComponentes();
		
		setLayout(new BorderLayout());
		
		JPanel painel = new JPanel(new GridLayout(5, 1));
		
		painel.add(texto);
		painel.add(enveloparBotao(botaoManutencao));
		painel.add(enveloparBotao(botaoCadastro));
		painel.add(enveloparBotao(botaoEmprestimo));
		painel.add(enveloparBotao(botaoRelatorio));
		
		add(painel, BorderLayout.NORTH);
	}
	
	// METODOS
	private void inicializarComponentes() {
		texto = new JLabel("Voce deseja ir para qual secao?");
		botaoManutencao = new JButton("AAAAAAAAAAAAAAaaa");
		botaoCadastro = new JButton("Cadastro");
		botaoEmprestimo = new JButton("Emprestimo");
		botaoRelatorio = new JButton("Relatorio");
	}
	
	private void configurarComponentes() {
		Font fonteTexto = new Font("Arial", Font.BOLD, 20);
		Font fonteBotoes = new Font("Arial", Font.PLAIN, 18);
		
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
	}
    
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.setBorder(new EmptyBorder(0, 0, 10, 0));
		painel.add(botao);
		return painel;
	}
}

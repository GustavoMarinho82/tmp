package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controle.Acao;
import gui.navegacao.Tela;
import gui.util.SpringUtilities;

public class PainelEmprestimoDevolucao extends JPanel {
	// ATRIBUTOS
	private final int CODIGO_PADRAO = 1;
	
	private JLabel titulo;
	private JLabel textoCPF;
	private JLabel textoCodigo;
	
	private JFormattedTextField campoCPF;
	private JSpinner campoCodigo;
	
	private JButton botaoEmprestimo;
	private JButton botaoDevolucao;
	private JButton botaoVoltar;
	
	private JPanel painelFormulario;
	private SpringLayout layout;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelEmprestimoDevolucao(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("EMPRESTIMO / DEVOLUCAO");
		textoCPF = new JLabel("CPF do Usuario:");
		textoCodigo = new JLabel("Codigo do Livro:");
		
		campoCodigo = new JSpinner(new SpinnerNumberModel(CODIGO_PADRAO, 1, 99, 1));
		
		try {
			campoCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));	
			
		} catch (ParseException e) {
			System.err.println(""); // Nunca vai ser executado
		}
			
		botaoEmprestimo = new JButton("Emprestar");
		botaoDevolucao = new JButton("Devolver");
		botaoVoltar = new JButton("Voltar");
		
		layout = new SpringLayout();
		painelFormulario = new JPanel(layout);
		
		botaoEmprestimo.addActionListener(al);
		botaoDevolucao.addActionListener(al);
		botaoVoltar.addActionListener(al);
		
		botaoEmprestimo.setActionCommand(Acao.FAZER_EMPRESTIMO.toString());
		botaoDevolucao.setActionCommand(Acao.FAZER_DEVOLUCAO.toString());
		botaoVoltar.setActionCommand(Tela.PRINCIPAL.toString());
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fonteTextos = new Font("Monospaced", Font.PLAIN, 16);
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
		textoCPF.setFont(fonteTextos);
		textoCodigo.setFont(fonteTextos);
		campoCPF.setFont(fonteTextos);
		campoCodigo.setFont(fonteTextos);
		
		botaoEmprestimo.setFont(fonteBotoes);
		botaoDevolucao.setFont(fonteBotoes);
		botaoVoltar.setFont(fonteBotoes);
		
		// DIMENSOES
		Dimension tamanhoCampos = new Dimension(240, 20);
		Dimension tamanhoBotoes = new Dimension(172, 50);
		
		campoCPF.setPreferredSize(tamanhoCampos);
		campoCodigo.setPreferredSize(tamanhoCampos);
		botaoEmprestimo.setPreferredSize(tamanhoBotoes);
		botaoDevolucao.setPreferredSize(tamanhoBotoes);
		botaoVoltar.setPreferredSize(new Dimension(350, 50));
		
		// BORDAS
		titulo.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		// ALINHAMENTOS
		titulo.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void montarLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 30, 10, 30));
    
		montarFormulario();
            
		add(titulo, BorderLayout.NORTH);
		add(painelFormulario, BorderLayout.CENTER);
		add(enveloparBotoes(botaoEmprestimo, botaoDevolucao, botaoVoltar), BorderLayout.SOUTH);
	}
	
	private void montarFormulario() {
		painelFormulario.setBorder(new EmptyBorder(90, 20, 90, 30));
	
		painelFormulario.add(textoCPF);
		painelFormulario.add(campoCPF);
		painelFormulario.add(textoCodigo);
		painelFormulario.add(campoCodigo);
		
		SpringUtilities.makeCompactGrid(painelFormulario, 2, 2, 5, 5, 5, 5);
	}
	
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao);
		return painel;
	}
	
	private JPanel enveloparBotoes(JButton botao1, JButton botao2, JButton botao3) {
		JPanel painelInterior = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel painelExterior = new JPanel(new BorderLayout());;
		painelInterior.add(botao1);
		painelInterior.add(botao2);
		painelExterior.add(painelInterior, BorderLayout.NORTH);
		painelExterior.add(enveloparBotao(botao3), BorderLayout.SOUTH);
		return painelExterior;
	}
	
	public String getCPF() {
		return campoCPF.getText();
	}
	
	public int getCodigo() {
		return ((Number) campoCodigo.getValue()).intValue();
	}
	
	public void resetarCampos() {
		campoCPF.setText("");
		campoCodigo.setValue(CODIGO_PADRAO);
	}
}

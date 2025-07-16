package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.tabela.*;
import gui.navegacao.Telas;

public class PainelRelatorio extends JPanel {
	// ATRIBUTOS
	private JLabel titulo;
	
	private JTable tabelaUsuarios;
	private JTable tabelaLivros;
	
	private JButton botaoVoltar;
	
	private JTabbedPane painelAbas;
	private JScrollPane painelUsuarios;
	private JScrollPane painelLivros;
	
	private ActionListener al;
	private ModeloTabelaUsuarios modeloTabelaUsuarios;
	private ModeloTabelaLivros modeloTabelaLivros;
	
	// CONSTRUTOR
	public PainelRelatorio(ActionListener al, ModeloTabelaUsuarios modeloTabelaUsuarios, ModeloTabelaLivros modeloTabelaLivros) {
		super();
		this.al = al;
		this.modeloTabelaUsuarios = modeloTabelaUsuarios;
		this.modeloTabelaLivros = modeloTabelaLivros;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("RELATORIO");
		
		tabelaUsuarios = new JTable(modeloTabelaUsuarios);
		tabelaLivros = new JTable(modeloTabelaLivros);

		botaoVoltar = new JButton("Voltar");
		
		painelAbas = new JTabbedPane();
		painelUsuarios = new JScrollPane(tabelaUsuarios);
		painelLivros = new JScrollPane(tabelaLivros);
		
		botaoVoltar.addActionListener(al);

		botaoVoltar.setActionCommand(Telas.PRINCIPAL.toString());
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fonteCabecalhos = new Font("Monospaced", Font.BOLD, 10);
		Font fonteDados = new Font("Monospaced", Font.PLAIN, 10);

		titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
		painelAbas.setFont(new Font("Monospaced", Font.BOLD, 12));
		tabelaUsuarios.getTableHeader().setFont(fonteCabecalhos);
		tabelaLivros.getTableHeader().setFont(fonteCabecalhos);
		tabelaUsuarios.setFont(fonteDados);
		tabelaLivros.setFont(fonteDados);
		botaoVoltar.setFont(new Font("Monospaced", Font.PLAIN, 18));
		
		// DIMENSOES
		int larguraNumeros = 10;
		
		tabelaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(125);
		tabelaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(30);
		tabelaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(55);
		tabelaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(larguraNumeros);
		tabelaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(larguraNumeros);
		tabelaUsuarios.getColumnModel().getColumn(5).setPreferredWidth(45);
		
		botaoVoltar.setPreferredSize(new Dimension(350, 50));

		// BORDAS
		titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
		
		// ALINHAMENTOS
		titulo.setHorizontalAlignment(JLabel.CENTER);
		
		// OUTROS
		tabelaUsuarios.getTableHeader().setReorderingAllowed(false);
		tabelaUsuarios.setFillsViewportHeight(true);
		tabelaLivros.getTableHeader().setReorderingAllowed(false);
		tabelaLivros.setFillsViewportHeight(true);
		
	}
	
	private void montarLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 0, 10, 0));
		
		painelAbas.addTab("Usuarios", painelUsuarios);
		painelAbas.addTab("Livros", painelLivros);
		
		add(titulo, BorderLayout.NORTH);
		add(painelAbas, BorderLayout.CENTER);
		add(enveloparBotao(botaoVoltar), BorderLayout.SOUTH);
	}
	
	private JPanel enveloparBotao(JButton botao) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao);
		painel.setBorder(new EmptyBorder(8, 0, 0, 0));
		return painel;
	}
}

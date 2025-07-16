package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import gui.navegacao.Telas;
import gui.util.SpringUtilities;

public class PainelCadLivro extends JPanel {
	// ATRIBUTOS
	private final int CODIGO_PADRAO = 1;
	private final int DISPONIVEIS_PADRAO = 0;
	
	private JLabel titulo;
	private JLabel textoCodigo;
	private JLabel textoTitulo;
	private JLabel textoCategoria;
	private JLabel textoDisponiveis;
	
	private JSpinner campoCodigo;
	private JTextField campoTitulo;
	private JTextField campoCategoria;
	private JSpinner campoDisponiveis;
	
	private JButton botaoCadastrar;
	private JButton botaoVoltar;
	
	private JPanel painelFormulario;
	private SpringLayout layout;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelCadLivro(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("CADASTRAR LIVRO");
		textoCodigo = new JLabel("Codigo:");
		textoTitulo = new JLabel("Titulo:");
		textoCategoria = new JLabel("Categoria:");
		textoDisponiveis = new JLabel("Disponiveis:");
		
		campoCodigo = new JSpinner(new SpinnerNumberModel(CODIGO_PADRAO, 1, 99, 1));
		campoTitulo = new JTextField();
		campoCategoria = new JTextField();
		campoDisponiveis = new JSpinner(new SpinnerNumberModel(DISPONIVEIS_PADRAO, 0, Integer.MAX_VALUE, 1));
		
		botaoCadastrar = new JButton("Cadastrar");
		botaoVoltar = new JButton("Voltar");
		
		layout = new SpringLayout();
		painelFormulario = new JPanel(layout);
		
		botaoCadastrar.addActionListener(al);
		botaoVoltar.addActionListener(al);
		
		botaoCadastrar.setActionCommand("CADASTRAR LIVRO");
		botaoVoltar.setActionCommand(Telas.CADASTRO.toString());
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fonteTextos = new Font("Monospaced", Font.PLAIN, 16);
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
		textoCodigo.setFont(fonteTextos);
		textoTitulo.setFont(fonteTextos);
		textoCategoria.setFont(fonteTextos);
		textoDisponiveis.setFont(fonteTextos);
		campoCodigo.setFont(fonteTextos);
		campoTitulo.setFont(fonteTextos);
		campoCategoria.setFont(fonteTextos);
		campoDisponiveis.setFont(fonteTextos);
		
		botaoCadastrar.setFont(fonteBotoes);
		botaoVoltar.setFont(fonteBotoes);
		
		// DIMENSOES
		Dimension tamanhoCampos = new Dimension(240, 20);
		Dimension tamanhoBotoes = new Dimension(172, 50);
		
		campoCodigo.setPreferredSize(tamanhoCampos);
		campoTitulo.setPreferredSize(tamanhoCampos);
		campoCategoria.setPreferredSize(tamanhoCampos);
		campoDisponiveis.setPreferredSize(tamanhoCampos);
		botaoCadastrar.setPreferredSize(tamanhoBotoes);
		botaoVoltar.setPreferredSize(tamanhoBotoes);
		
		// BORDAS
		titulo.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		// ALINHAMENTOS
		titulo.setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void montarLayout() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 20, 10, 20));
    
		montarFormulario();
            
		add(titulo, BorderLayout.NORTH);
		add(painelFormulario, BorderLayout.CENTER);
		add(enveloparBotoes(botaoVoltar, botaoCadastrar), BorderLayout.SOUTH);

	}
	
	private void montarFormulario() {
		painelFormulario.setBorder(new EmptyBorder(65, 20, 85, 30));
	
		painelFormulario.add(textoCodigo);
		painelFormulario.add(campoCodigo);
		painelFormulario.add(textoTitulo);
		painelFormulario.add(campoTitulo);
		painelFormulario.add(textoCategoria);
		painelFormulario.add(campoCategoria);
		painelFormulario.add(textoDisponiveis);
		painelFormulario.add(campoDisponiveis);
		
		SpringUtilities.makeCompactGrid(painelFormulario, 4, 2, 5, 5, 5, 5);
	}
	
	private JPanel enveloparBotoes(JButton botao1, JButton botao2) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao1);
		painel.add(botao2);
		return painel;
	}
	
	public int getCodigo() {
		return ((Number) campoCodigo.getValue()).intValue();
	}
	
	public String getTitulo() {
		return campoTitulo.getText();
	}
	
	public String getCategoria() {
		return campoCategoria.getText();
	}
	
	public int getDisponiveis() {
		return ((Number) campoDisponiveis.getValue()).intValue();
	}
	
	public void resetarCampos() {
		campoCodigo.setValue(CODIGO_PADRAO);
		campoTitulo.setText("");
		campoCategoria.setText("");
		campoDisponiveis.setValue(DISPONIVEIS_PADRAO);

	}
}

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

public class PainelCadastroUsuario extends JPanel {
	// ATRIBUTOS
	private final double PESO_PADRAO = 50;
	private final double ALTURA_PADRAO = 1.7;
	
	private JLabel titulo;
	private JLabel textoNome;
	private JLabel textoSobrenome;
	private JLabel textoDataNasc;
	private JLabel textoCPF;
	private JLabel textoPeso;
	private JLabel textoAltura;
	private JLabel textoEndereco;
	
	private JTextField campoNome;
	private JTextField campoSobrenome;
	private JFormattedTextField campoDataNasc;
	private JFormattedTextField campoCPF;
	private JSpinner campoPeso;
	private JSpinner campoAltura;
	private JTextField campoEndereco;
	
	private JButton botaoCadastrar;
	private JButton botaoVoltar;
	
	private JPanel painelFormulario;
	private SpringLayout layout;
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelCadastroUsuario(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		titulo = new JLabel("CADASTRAR USUARIO");
		textoNome = new JLabel("Nome:");
		textoSobrenome = new JLabel("Sobrenome:");
		textoDataNasc = new JLabel("Data de Nasc.:");
		textoCPF = new JLabel("CPF:");
		textoPeso = new JLabel("Peso (em kg):");
		textoAltura = new JLabel("Altura (em m):");
		textoEndereco = new JLabel("Endereco:");
		
		campoNome = new JTextField();
		campoSobrenome = new JTextField();
		campoPeso = new JSpinner(new SpinnerNumberModel(PESO_PADRAO, 0.3, 650.0, 0.1));
		campoAltura = new JSpinner(new SpinnerNumberModel(ALTURA_PADRAO, 0.5, 3.0, 0.01));
		campoEndereco = new JTextField();
		
		try {
			campoDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
			campoCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));	
		
		} catch (ParseException e) {
			System.err.println(""); // Nunca vai ser executado
		}
		
		botaoCadastrar = new JButton("Cadastrar");
		botaoVoltar = new JButton("Voltar");
		
		layout = new SpringLayout();
		painelFormulario = new JPanel(layout);
		
		botaoCadastrar.addActionListener(al);
		botaoVoltar.addActionListener(al);
		
		botaoCadastrar.setActionCommand(Acao.CADASTRAR_USUARIO.toString());
		botaoVoltar.setActionCommand(Tela.CADASTRO.toString());
	}
	
	private void configurarComponentes() {
		// FONTES
		Font fonteTextos = new Font("Monospaced", Font.PLAIN, 16);
		Font fonteBotoes = new Font("Monospaced", Font.PLAIN, 18);
		
		titulo.setFont(new Font("Monospaced", Font.BOLD, 20));
		textoNome.setFont(fonteTextos);
		textoSobrenome.setFont(fonteTextos);
		textoDataNasc.setFont(fonteTextos);
		textoCPF.setFont(fonteTextos);
		textoPeso.setFont(fonteTextos);
		textoAltura.setFont(fonteTextos);
		textoEndereco.setFont(fonteTextos);
		campoNome.setFont(fonteTextos);
		campoSobrenome.setFont(fonteTextos);
		campoDataNasc.setFont(fonteTextos);
		campoCPF.setFont(fonteTextos);
		campoPeso.setFont(fonteTextos);
		campoAltura.setFont(fonteTextos);
		campoEndereco.setFont(fonteTextos);
		
		botaoCadastrar.setFont(fonteBotoes);
		botaoVoltar.setFont(fonteBotoes);
		
		// DIMENSOES
		Dimension tamanhoCampos = new Dimension(240, 20);
		Dimension tamanhoBotoes = new Dimension(172, 50);
		
		campoNome.setPreferredSize(tamanhoCampos);
		campoSobrenome.setPreferredSize(tamanhoCampos);
		campoDataNasc.setPreferredSize(tamanhoCampos);
		campoCPF.setPreferredSize(tamanhoCampos);
		campoPeso.setPreferredSize(tamanhoCampos);
		campoAltura.setPreferredSize(tamanhoCampos);
		campoEndereco.setPreferredSize(tamanhoCampos);
		botaoCadastrar.setPreferredSize(tamanhoBotoes);
		botaoVoltar.setPreferredSize(tamanhoBotoes);
		
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
		add(enveloparBotoes(botaoVoltar, botaoCadastrar), BorderLayout.SOUTH);
	}
	
	private void montarFormulario() {
		painelFormulario.setBorder(new EmptyBorder(10, 20, 20, 30));
	
		painelFormulario.add(textoNome);
		painelFormulario.add(campoNome);
		painelFormulario.add(textoSobrenome);
		painelFormulario.add(campoSobrenome);
		painelFormulario.add(textoDataNasc);
		painelFormulario.add(campoDataNasc);
		painelFormulario.add(textoCPF);
		painelFormulario.add(campoCPF);
		painelFormulario.add(textoPeso);
		painelFormulario.add(campoPeso);
		painelFormulario.add(textoAltura);
		painelFormulario.add(campoAltura);
		painelFormulario.add(textoEndereco);
		painelFormulario.add(campoEndereco);
		
		SpringUtilities.makeCompactGrid(painelFormulario, 7, 2, 5, 5, 5, 5);
	}
	
	private JPanel enveloparBotoes(JButton botao1, JButton botao2) {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painel.add(botao1);
		painel.add(botao2);
		return painel;
	}
	
	public String getNome() {
		return campoNome.getText();
	}
	
	public String getSobrenome() {
		return campoSobrenome.getText();
	}
	
	public String getDataNasc() {
		return campoDataNasc.getText();
	}
	
	public String getCPF() {
		return campoCPF.getText();
	}
	
	public float getPeso() {
		return ((Number) campoPeso.getValue()).floatValue();
	}
	
	public float getAltura() {
		return ((Number) campoAltura.getValue()).floatValue();
	}
	
	public String getEndereco() {
		return campoEndereco.getText();
	}
	
	public void resetarCampos() {
		campoNome.setText("");
		campoSobrenome.setText("");
		campoDataNasc.setText("");
		campoCPF.setText("");
		campoPeso.setValue(PESO_PADRAO);
		campoAltura.setValue(ALTURA_PADRAO);
		campoEndereco.setText("");		
	}
}

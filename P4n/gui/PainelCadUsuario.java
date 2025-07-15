package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import gui.navegacao.Telas;

public class PainelCadUsuario extends JPanel {
	// ATRIBUTOS
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
	
	private ActionListener al;
	
	// CONSTRUTOR
	public PainelCadUsuario(ActionListener al) {
		super();
		this.al = al;
		inicializarComponentes();
		configurarComponentes();
		montarLayout();
	}
	
	// METODOS
	private void inicializarComponentes() {
		campoNome = new JTextField();
		campoSobrenome = new JTextField();
		campoPeso = new JSpinner(new SpinnerNumberModel(50.0, 0.3, 650.0, 0.1));
		campoAltura = new JSpinner(new SpinnerNumberModel(1.7, 0.5, 3.0, 0.01));
		campoEndereco = new JTextField();
		
		try {
			campoDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
			campoCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));	
		} catch (Exception e) {
			System.err.println("");
		}
	}
	
	private void configurarComponentes() {
		System.out.println("A");
	}
	
	private void montarLayout() {
	System.out.println("A");
	}
}

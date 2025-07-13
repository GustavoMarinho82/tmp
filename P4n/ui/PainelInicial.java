package ui;

import java.awt.*;
import javax.swing.*;

public class PainelInicial extends JPanel {
	// COMPONENTES
	private JLabel textoPolPer = new JLabel("Voce deseja iniciar a biblioteca com uma politica personalizada (do arquivo config.properties)?", JLabel.LEFT);
	private JLabel textoArqsCad = new JLabel("Iniciar a biblioteca a partir de arquivos de cadastro? (s/n)", JLabel.LEFT);
	private JButton botaoPol = new JButton("Selecionar arquivo");
	private JButton botao = new JButton("Selecionar arquivo");
	
	// CONSTRUTOR
	public PainelInicial() {
		super();
		add(textoPolPer);
		add(botaoPol);
		add(textoArqsCad);
		add(botao);
		
		setLayout(new GridLayout(2, 2));
	}
}

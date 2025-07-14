import java.awt.Font;
import javax.swing.*;

import gui.JanelaPrincipal;

public class P4nX {
	public static void main(String[] args) {
		Font fontePadrao = new Font("Monospaced", Font.PLAIN, 12);
		UIManager.put("OptionPane.messageFont", fontePadrao);
		UIManager.put("OptionPane.buttonFont", fontePadrao);
        
		JanelaPrincipal janela = new JanelaPrincipal();
		janela.setVisible(true);
	}
}

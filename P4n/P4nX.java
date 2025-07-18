import java.awt.Font;
import javax.swing.*;

import gui.JanelaPrincipal;

public class P4nX {
	private Font fonteDialogos;
	private JanelaPrincipal janela;
	
	// METODO PRINCIPAL
	public static void main(String[] args) {
		P4nX programa = new P4nX();
		programa.executa();
	}
	
	// METODO AUXILIAR
	private void executa() {
		fonteDialogos = new Font("Monospaced", Font.PLAIN, 12);
		UIManager.put("OptionPane.messageFont", fonteDialogos);
		UIManager.put("OptionPane.buttonFont", fonteDialogos);
		UIManager.put("OptionPane.messageFont", fonteDialogos);
        
		janela = new JanelaPrincipal();
		janela.setVisible(true);
	}
}

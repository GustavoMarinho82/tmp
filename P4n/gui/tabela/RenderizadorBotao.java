package gui.tabela;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class RenderizadorBotao extends JButton implements TableCellRenderer {
	// CONSTRUTOR
	public RenderizadorBotao() {
		setFont(new Font("Monospaced", Font.PLAIN, 10));
	}

	// METODO
	@Override
	public Component getTableCellRendererComponent(JTable tabela, Object dado, boolean selecionado, boolean emFoco, int linha, int coluna) {
		setText((dado == null) ? "" : dado.toString());
		return this;
	}
}

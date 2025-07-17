package gui.tabela;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class RendererBotao extends JButton implements TableCellRenderer {
	// CONSTRUTOR
	public RendererBotao() {
		setOpaque(true);
	}

	// METODO
	@Override
	public Component getTableCellRendererComponent(JTable tabela, Object dado, boolean selecionado, boolean emFoco, int linha, int coluna) {
		setText((dado == null) ? "" : dado.toString());
		return this;
	}
}

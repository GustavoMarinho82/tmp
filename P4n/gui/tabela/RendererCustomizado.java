package gui.tabela;

import java.time.LocalDate;
import javax.swing.table.DefaultTableCellRenderer;

import entidade.validacao.ValidaData;

public class RendererCustomizado extends DefaultTableCellRenderer {
	// CONSTRUTOR
	public RendererCustomizado() {
		super();
	}

	// METODO
	@Override
	protected void setValue(Object dado) {
		if (dado instanceof LocalDate) {
			setText(ValidaData.formata((LocalDate) dado));
			
		} else if (dado instanceof Float) {
			setText(String.valueOf((float) dado).replace(",", "."));
			
		} else {
			super.setValue(dado);
		}
	}
}

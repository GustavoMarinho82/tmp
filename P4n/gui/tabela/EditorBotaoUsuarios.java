package gui.tabela;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class EditorBotaoUsuarios extends EditorBotao {
	// CONSTRUTOR
	public EditorBotaoUsuarios(ActionListener al) {
		super(al);
	}

	// METODOS
	@Override
	public Component getTableCellEditorComponent(JTable tabela, Object dado, boolean selecionado, int linha, int coluna) {
		int linhaAtualizada = tabela.convertRowIndexToModel(linha);
		texto = (dado == null) ? "" : dado.toString();
		
		botao.setText(texto);
		botao.setActionCommand("MOSTRAR HISTORICO USUARIO - " + linhaAtualizada);

		pressionado = true;
		
		return botao;
	}
}

package gui.tabela;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class EditorBotaoLivros extends EditorBotao {
	// CONSTRUTOR
	public EditorBotaoLivros(ActionListener al) {
		super(al);
	}

	// METODOS
	@Override
	public Component getTableCellEditorComponent(JTable tabela, Object dado, boolean selecionado, int linha, int coluna) {
		int linhaAtualizada = tabela.convertRowIndexToModel(linha);
		texto = (dado == null) ? "" : dado.toString();
		
		botao.setText(texto);
		botao.setActionCommand("MOSTRAR HISTORICO LIVRO - " + linhaAtualizada);

		pressionado = true;
		
		return botao;
	}
}

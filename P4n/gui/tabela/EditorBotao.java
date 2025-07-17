package gui.tabela;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;

public class EditorBotao extends DefaultCellEditor {
	// ATRIBUTOS
	private String texto;
	private boolean pressionado;
	
	private JButton botao;
	
	private ActionListener al;

	// CONSTRUTOR
	public EditorBotao(ActionListener al) {
		super(new JCheckBox());
		this.al = al;
		
		botao = new JButton();
		botao.setOpaque(true);
		
		botao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				fireEditingStopped();
				al.actionPerformed(evento);
			}
		});
	}

	// METODOS
	@Override
	public Component getTableCellEditorComponent(JTable tabela, Object dado, boolean selecionado, int linha, int coluna) {
		texto = (dado == null) ? "" : dado.toString();
		
		botao.setText(texto);
		botao.setActionCommand("MOSTRAR HISTORICO USUARIO - " + linha);

		pressionado = true;
		
		return botao;
	}

	@Override
	public Object getCellEditorValue() {
		pressionado = false;
		return texto;
	}

	@Override
	public boolean stopCellEditing() {
		pressionado = false;
		return super.stopCellEditing();
	}
}

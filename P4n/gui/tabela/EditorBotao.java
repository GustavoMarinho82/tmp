package gui.tabela;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class EditorBotao extends DefaultCellEditor {
	// ATRIBUTOS
	protected String texto;
	protected boolean pressionado;
	
	protected JButton botao;
	
	private ActionListener al;

	// CONSTRUTOR
	public EditorBotao(ActionListener al) {
		super(new JCheckBox());
		this.al = al;
		
		botao = new JButton();
		botao.setFont(new Font("Monospaced", Font.BOLD, 10));
		
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
	public abstract Component getTableCellEditorComponent(JTable tabela, Object dado, boolean selecionado, int linha, int coluna);

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

package gui.tabela;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import entidade.Usuario;
import entidade.validacao.*;

public class ModeloTabelaUsuarios extends AbstractTableModel {
	// ATRIBUTOS
	private final String[] NOMES_COLUNAS = {"Nome", "Nascimento", "CPF", "Peso", "Altura", "Endereco", "Historico"};
	private ArrayList<Usuario> usuarios = new ArrayList<>();
	
	// CONSTRUTOR
	public ModeloTabelaUsuarios() {
		super();
	}
	
	// METODOS
	@Override
	public int getRowCount() {
		return usuarios.size();
	}
	
	@Override
	public int getColumnCount() {
		return NOMES_COLUNAS.length;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Usuario usuario = usuarios.get(linha);
		
		switch (coluna) {
			case 0:
				return usuario.getNome() + " " + usuario.getSobreNome();
				
			case 1: 
				return usuario.getDataNasc();
				
			case 2:
				return ValidaCPF.formataCPF(usuario.getNumCPF());
				
			case 3:
				return usuario.getPeso();
			
			case 4:
				return usuario.getAltura();
			
			case 5:
				return usuario.getEndereco();
			
			case 6:
				return "Ver";
				
			default:
				return "";
		}
	}
	
	@Override
	public String getColumnName(int coluna) {
		return NOMES_COLUNAS[coluna];
	}
	
	@Override
	public Class<?> getColumnClass(int coluna) {
		switch (coluna) {
			case 0: case 2: case 5:
				return String.class;

			case 1:
				return LocalDate.class; 

			case 3: case 4:
				return Float.class;
			
			case 6:
				return JButton.class;
				
			default:
				return Object.class;
		}
	}

	@Override
	public boolean isCellEditable(int linha, int coluna) {
		return (coluna == 6);
	}
	
	public Usuario getUsuario(int linha) {
		return usuarios.get(linha);
	}
		
	public void atualizarTabela(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
		fireTableDataChanged();
	}
}

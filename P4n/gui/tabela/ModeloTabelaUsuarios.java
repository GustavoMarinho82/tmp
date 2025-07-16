package gui.tabela;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import entidade.Usuario;
import entidade.validacao.*;

public class ModeloTabelaUsuarios extends AbstractTableModel {
	// ATRIBUTOS
	private final String[] NOMES_COLUNAS = {"Nome"/*, "Sobrenome"*/, "Nasc.", "CPF", "Peso", "Altura", "Endereco"};
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
				return ValidaData.formata(usuario.getDataNasc());
				
			case 2:
				return ValidaCPF.formataCPF(usuario.getNumCPF());
				
			case 3:
				return usuario.getPeso() + " kg";
			
			case 4:
				return usuario.getAltura() + " m";
			
			case 5:
				return usuario.getEndereco();
			
			default:
				return "";
		}
	}
	
	@Override
	public String getColumnName(int coluna) {
		return NOMES_COLUNAS[coluna];
	}
	
	public void atualizarTabela(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
		fireTableDataChanged();
	}
}

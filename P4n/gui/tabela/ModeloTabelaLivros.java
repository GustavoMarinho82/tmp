package gui.tabela;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import entidade.Livro;

public class ModeloTabelaLivros extends AbstractTableModel {
	// ATRIBUTOS
	private final String[] NOMES_COLUNAS = {"Codigo", "Titulo", "Categoria", "Disponiveis", "Emprestados"};
	private ArrayList<Livro> livros = new ArrayList<>();
	
	// CONSTRUTOR
	public ModeloTabelaLivros() {
		super();
	}
	
	// METODOS
	@Override
	public int getRowCount() {
		return livros.size();
	}
	
	@Override
	public int getColumnCount() {
		return NOMES_COLUNAS.length;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Livro livro = livros.get(linha);
		
		switch (coluna) {
			case 0:
				return livro.getCodigo();
				
			case 1: 
				return livro.getTitulo();
				
			case 2: 
				return livro.getCategoria();
				
			case 3:
				return livro.getDisponiveis();
				
			case 4:
				return livro.getEmprestados();
			
			default:
				return "";
		}
	}
	
	@Override
	public String getColumnName(int coluna) {
		return NOMES_COLUNAS[coluna];
	}
	
	public void atualizarTabela(ArrayList<Livro> livros) {
		this.livros = livros;
		fireTableDataChanged();
	}
}

package entidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import entidade.excecao.*;

public class Livro implements Serializable {
	// ATRIBUTOS
	private static final long serialVersionUID = 30L;
	
	private int codigo;
	private String titulo;
	private String categoria;
	private int disponiveis;
	private int emprestados;
	private ArrayList<EmprestPara> hist = new ArrayList<>();

	// CONSTRUTOR
	public Livro(int codigo, String titulo, String categoria, int disponiveis, int emprestados) {
		setCodigo(codigo);
		setTitulo(titulo);
		setCategoria(categoria);
		setDisponiveis(disponiveis);
		setEmprestados(emprestados);
	}

	// GETTERS
	public int getCodigo() {
		return codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public int getDisponiveis() {
		return disponiveis;
	}

	public int getEmprestados() {
		return emprestados;
	}

	public ArrayList<EmprestPara> getHist() {
		return hist;
	}

	public String getHistFormatado() {
		if (hist.size() == 0) {
			return "vazio";
		}
		
		StringBuilder histFormatado = new StringBuilder();
		
		for (int i = 0; i < hist.size(); i++) {
			EmprestPara registro = hist.get(i);
			histFormatado.append(String.format("\n Registro %d - CPF do usuario: %s | Data do emprestimo: %s | Data da devolucao: %s", 
				(i+1), 
				registro.getCPFFormatado(),
				registro.getDataEmprestimoFormatada(), 
				registro.getDataDevolucaoFormatada()
			));
		}

		return histFormatado.toString();
	}

	// SETTERS
	public void setCodigo(int codigo) {
		if (codigo < 1 || codigo > 999) {
			throw new IllegalArgumentException("O codigo do livro deve estar entre 1 e 999!");
		}
		
		this.codigo = codigo;
	}

	public void setTitulo(String titulo) {
		if (titulo == null || titulo.trim().isEmpty()) {
			throw new IllegalArgumentException("O titulo nao pode ser vazio!");
		}
		
		this.titulo = titulo;
	}

	public void setCategoria(String categoria) {
		if (categoria == null || categoria.trim().isEmpty()) {
			throw new IllegalArgumentException("A categoria nao pode ser vazia!");
		}
		
		this.categoria = categoria;
	}

	public void setDisponiveis(int disponiveis) {
		if (disponiveis < 0) {
			throw new IllegalArgumentException("O numero de livros disponiveis nao pode ser negativo!");
		}
	
		this.disponiveis = disponiveis;
	}

	public void setEmprestados(int emprestados) {
		if (emprestados < 0) {
			throw new IllegalArgumentException("O numero de livros emprestados nao pode ser negativo!");
		}
	
		this.emprestados = emprestados;
	}

	// OUTROS METODOS	
	public void empresta() throws CopiaNaoDisponivelEx {
		if (disponiveis == 0) {
			throw new CopiaNaoDisponivelEx();
		}
		
		disponiveis--;
		emprestados++;
	}
	
	public void devolve() throws NenhumaCopiaEmprestadaEx {
		if (emprestados == 0) {
			throw new NenhumaCopiaEmprestadaEx();
		}
		
		disponiveis++;
		emprestados--;
	}
	
	public void addUsuarioHist(long CPF, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
		hist.add(new EmprestPara(CPF, dataEmprestimo, dataDevolucao));
	}
	
	@Override
	public String toString() {
		return String.format("Codigo: %d \nTitulo: %s \nCategoria: %s \nN° de disponiveis: %d \nN° de emprestados: %d \n",
			getCodigo(),
			getTitulo(),
			getCategoria(),
			getDisponiveis(),
			getEmprestados()
		);
	}

	public String toStringCompleto() {
		return this.toString() + String.format("Historico: %s\n", getHistFormatado());
	}
}

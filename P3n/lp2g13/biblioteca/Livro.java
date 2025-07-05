package lp2g13.biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;

public class Livro {
	// ATRIBUTOS
	private static ArrayList<Integer> codsEmUso = new ArrayList<>(); // Armzena os codigos que estao vinculados a algum livro

	private int codigo;
	private String titulo;
	private String categoria;
	private int disponiveis; // Numero de copias disponiveis no acervo
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

	// SETTERS
	public void setCodigo(int codigo) {
		if (codigo < 1 || codigo > 999) {
			throw new IllegalArgumentException("O codigo do livro deve estar entre 1 e 999!");
		}
		
		if (codsEmUso.contains(codigo)) {
			throw new IllegalArgumentException("Codigo ja cadastrado! Cadastre o livro com um codigo diferente.");
		}
		
		codsEmUso.remove(Integer.valueOf(this.codigo));
		codsEmUso.add(codigo);
		
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
	public static boolean conferirCodEmUso(int codigo) {
		// Retorna se ja existe um livro com o codigo informado
		return codsEmUso.contains(codigo);
	}

	public void empresta() throws CopiaNaoDisponivelEx {
		if (emprestados == disponiveis) {
			throw new CopiaNaoDisponivelEx();
		}
		
		emprestados++;
	}
	
	public void devolve() throws NenhumaCopiaEmprestadaEx {
		if (emprestados == 0) {
			throw new NenhumaCopiaEmprestadaEx();
		}
		
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
}

package lp2g13.biblioteca;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Pessoa implements Serializable {
	// ATRIBUTOS
	private String endereco;

	private ArrayList<Emprest> hist = new ArrayList<>();
	private int numLivrosEmprestados = 0; // Numero de livros que estao sendo emprestados ao usuario atualmente

	// CONSTRUTOR
	public Usuario(String nome, String sobreNome, LocalDate dataNasc, String CPF, float peso, float altura, String endereco) {
		super(nome, sobreNome, dataNasc, CPF, peso, altura);
		diminuirNumPessoas();
		setEndereco(endereco);
		aumentarNumPessoas();
	}
	
	public Usuario(String nome, String sobreNome, LocalDate dataNasc, long numCPF, float peso, float altura, String endereco) {
		this(nome, sobreNome, dataNasc, Long.toString(numCPF), peso, altura, endereco);
	}

	// GETTERS
	public String getEndereco() {
		return endereco;
	}

	public ArrayList<Emprest> getHist() {
		return hist;
	}
	
	public String getHistFormatado() {
		StringBuilder histFormatado = new StringBuilder();
		
		for (int i = 0; i < hist.size(); i++) {
			Emprest registro = hist.get(i);
			histFormatado.append(String.format("Registro {} - Emprestimo: {} | Devolucao: {} \n", 
				(i+1), 
				registro.getDataEmprestimoFormatada(), 
				registro.getDataDevolucaoFormatada()
			));
		}

		return histFormatado.toString();
	}

	public int getNumLivrosEmprestados() {
		return numLivrosEmprestados;
	}

	// SETTERS
	public void setEndereco(String endereco) {
		if (endereco == null || endereco.trim().isEmpty()) {
			throw new IllegalArgumentException("O endereco nao pode ser vazio!");
		}
	
		this.endereco = endereco;
	}

	// OUTROS METODOS
	public void aumentarNumLivrosEmprestados() {
		numLivrosEmprestados++;
	}

	public void diminuirNumLivrosEmprestados() {
		numLivrosEmprestados--;
	}

	public void addLivroHist(int codLivro, LocalDate dataEmprestimo) {
		hist.add(new Emprest(codLivro, dataEmprestimo));
		aumentarNumLivrosEmprestados();
	}

	@Override
	public String toString() {
		return super.toString() + "Endereco: " + getEndereco() + "\n";
	}

	public String toStringCompleto() {
		return this.toString() + "Historico: \n" + getHistFormatado();
	}
}

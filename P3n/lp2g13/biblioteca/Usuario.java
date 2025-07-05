package lp2g13.biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Pessoa {
	// ATRIBUTOS
	private String endereco;
	private ArrayList<Emprest> hist;
	private int numLEA = 0; // Numero de livros que estao sendo emprestados ao usuario atualmente

	// CONSTRUTOR
	public Usuario(String nome, String sobreNome, String dataNasc, String CPF, float peso, float altura, String endereco) {
		super(nome, sobreNome, dataNasc, CPF, peso, altura);
		diminuirNumPessoas();
		setEndereco(endereco);
		aumentarNumPessoas();
	}
	
	public Usuario(String nome, String sobreNome, String dataNasc, long numCPF, float peso, float altura, String endereco) {
		this(nome, sobreNome, dataNasc, Long.toString(numCPF), peso, altura, endereco);
	}

	// GETTERS
	public String getEndereco() {
		return endereco;
	}

	// SETTERS
	public void setEndereco(String endereco) {
		if (endereco == null || endereco.trim().isEmpty()) {
			throw new IllegalArgumentException("O endereco nao pode ser vazio!");
		}
	
		this.endereco = endereco;
	}

	// OUTROS METODOS
	public void aumentarNumLEA() {
		numLEA++;
	}

	public void diminuirNumLEA() {
		numLEA--;
	}

	public void addLivroHist(int codLivro, String dataEmprestimo) {
		hist.add(new Emprest(codLivro, dataEmprestimo));
		aumentarNumLEA();
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Endereco: %s\n", getEndereco());
	}
}

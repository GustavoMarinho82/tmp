package entidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Pessoa implements Serializable {
	// ATRIBUTOS
	private static final long serialVersionUID = 20L;
	
	private String endereco;
	private ArrayList<Emprest> hist = new ArrayList<>();
	private int numLivrosEmprestados = 0; // Numero de livros que estao sendo emprestados ao usuario atualmente

	// CONSTRUTOR
	public Usuario(String nome, String sobreNome, LocalDate dataNasc, String CPF, float peso, float altura, String endereco) {
		super(nome, sobreNome, dataNasc, CPF, peso, altura);
		setEndereco(endereco);
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
		if (hist.size() == 0) {
			return "vazio";
		}
		
		StringBuilder histFormatado = new StringBuilder();
		
		for (int i = 0; i < hist.size(); i++) {
			Emprest registro = hist.get(i);
			histFormatado.append(String.format("\nRegistro %d - Cod. do livro: %d | Data do emprestimo: %s | Data da devolucao: %s", 
				(i+1), 
				registro.getCodLivro(),
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

	public void addLivroHist(int codLivro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
		hist.add(new Emprest(codLivro, dataEmprestimo, dataDevolucao));
		
		if (dataDevolucao == null) {
			aumentarNumLivrosEmprestados();
		}
	}

	public boolean possuiLivroAtraso(int periodoDevolucao) {
		for (Emprest registro: hist) {
			LocalDate dataLimite = registro.getDataEmprestimo().plusDays(periodoDevolucao);
			
			if (registro.getDataDevolucao() == null && dataLimite.isBefore(LocalDate.now())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean possuiLivroAtraso(int codLivro, int periodoDevolucao) {
		for (Emprest registro: hist) {
			LocalDate dataLimite = registro.getDataEmprestimo().plusDays(periodoDevolucao);
			
			if (registro.getCodLivro() == codLivro && registro.getDataDevolucao() == null && dataLimite.isBefore(LocalDate.now())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("Endereco: %s\n", getEndereco());
	}

	public String toStringCompleto() {
		return this.toString() + String.format("Historico: %s\n", getHistFormatado());
	}
}

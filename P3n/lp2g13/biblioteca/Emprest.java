package lp2g13.biblioteca;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprest implements Serializable {
	// ATRIBUTOS
	private int codLivro;	
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao = null;
	
	// CONSTRUTORES
	public Emprest(int codLivro, LocalDate dataEmprestimo) {
		setCodLivro(codLivro);
		setDataEmprestimo(dataEmprestimo);
	}
	
	public Emprest(int codLivro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
		this(codLivro, dataEmprestimo);
		setDataDevolucao(dataDevolucao);
	}

	// GETTERS
	public int getCodLivro() {
		return codLivro;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public String getDataEmprestimoFormatada() {
		return dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public String getDataDevolucaoFormatada() {
		return (dataDevolucao != null) ? dataDevolucao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Pendente";
	}

	// SETTERS
	public void setCodLivro(int codLivro) {
		if (codLivro < 1 || codLivro > 999) {
			throw new IllegalArgumentException("O codigo do livro deve estar entre 1 e 999!");
		}
		
		this.codLivro = codLivro;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		if (!ValidaData.isAno(dataEmprestimo.getYear())) {
			throw new IllegalArgumentException("Data de emprestimo invalida!");
		}

		this.dataEmprestimo = dataEmprestimo;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		if (!ValidaData.isAno(dataDevolucao.getYear())) {
			throw new IllegalArgumentException("Data de devolucao invalida!");
		}
		
		if (dataDevolucao.isBefore(dataEmprestimo)) {
			throw new IllegalArgumentException("A data de devolucao nao pode ser anterior a data de emprestimo!");
		}
		
		this.dataDevolucao = dataDevolucao;
	}
	
	// OUTROS METODOS
	@Override
	public String toString() {
		return String.format("Codigo do Livro: %d \nData do Emprestimo: %s \nData de Devolucao: %s \n",
			getCodLivro(),
			getDataEmprestimoFormatada(),
			getDataDevolucaoFormatada()
		);
	}
}

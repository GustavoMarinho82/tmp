package lp2g13.biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lp2g13.biblioteca.validacao.ValidaData;

public class Emprest {
	// ATRIBUTOS
	int codLivro;	
	LocalDate dataEmprestimo;
	LocalDate dataDevolucao = null;
	
	// CONSTRUTORES
	public Emprest(int codLivro, String dataEmprestimo) {
		setCodLivro(codLivro);
		setDataEmprestimo(dataEmprestimo);
	}
	
	public Emprest(int codLivro, String dataEmprestimo, String dataDevolucao) {
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

	public void setDataEmprestimo(int dia, int mes, int ano) {
		if (!ValidaData.isDataValida(dia, mes, ano)) {
			throw new IllegalArgumentException("Data de emprestimo invalida!");
		}
	
		this.dataEmprestimo = LocalDate.of(ano, mes, dia);
	}
	
	public void setDataEmprestimo(String dataEmprestimo) {
		int[] dataInt = ValidaData.toInt(dataEmprestimo);
	
		this.dataEmprestimo = LocalDate.of(dataInt[2], dataInt[1], dataInt[0]);
	}

	public void setDataDevolucao(int dia, int mes, int ano) {
		if (!ValidaData.isDataValida(dia, mes, ano)) {
			throw new IllegalArgumentException("Data de devolucao invalida!");
		}

		if (dataEmprestimo.isAfter(LocalDate.of(ano, mes, dia))) {
			throw new IllegalArgumentException("A data de devolucao nao pode ser anterior a data de emprestimo!");
		}

		this.dataDevolucao = LocalDate.of(ano, mes, dia);
	}
	
	public void setDataDevolucao(String dataDevolucao) {
		int[] dataInt = ValidaData.toInt(dataDevolucao);
	
		this.dataDevolucao = LocalDate.of(dataInt[2], dataInt[1], dataInt[0]);
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

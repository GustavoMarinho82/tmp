package lp2g13.biblioteca;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmprestPara implements Serializable {
	// ATRIBUTOS
	private long CPF;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao;

	// CONSTRUTORES
	public EmprestPara(long CPF, LocalDate dataEmprestimo) {
		setCPF(CPF);
		setDataEmprestimo(dataEmprestimo);
	}
	
	public EmprestPara(long CPF, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
		this(CPF, dataEmprestimo);
		setDataDevolucao(dataDevolucao);
	}
	
	// GETTERS
	public long getCPF() {
		return CPF;
	}
	
	public String getCPFFormatado() {
		return ValidaCPF.formataCPF(CPF);
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
	public void setCPF(long CPF) {
		if (!ValidaCPF.isCPF(Long.toString(CPF))) {
			throw new IllegalArgumentException("Formato ou valor do CPF eh invalido! Formatos aceitos: 12345678901, 123.456.789-01, 123.456.789/01.");
		}

		this.CPF = CPF;
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
		return String.format("CPF: %d \nData do Emprestimo: %s \nData de Devolucao: %s \n",
			getCPFFormatado(),
			getDataEmprestimoFormatada(),
			getDataDevolucaoFormatada()
		);
	}
}

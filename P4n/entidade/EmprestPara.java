package entidade;

import java.io.*;
import java.time.LocalDate;

import entidade.validacao.*;

public class EmprestPara implements Serializable {
	// ATRIBUTOS
	private static final long serialVersionUID = 50L;
	
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
		return ValidaData.formata(dataEmprestimo);
	}
	
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public String getDataDevolucaoFormatada() {
		return (dataDevolucao != null) ? ValidaData.formata(dataDevolucao) : "Pendente";
	}

	// SETTERS
	public void setCPF(long CPF) {
		if (!ValidaCPF.isCPF(CPF)) {
			throw new IllegalArgumentException("CPF invalido!");
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
		if (dataDevolucao != null) {
			if (!ValidaData.isAno(dataDevolucao.getYear())) {
				throw new IllegalArgumentException("Data de devolucao invalida!");
			}
			
			if (dataDevolucao.isBefore(dataEmprestimo)) {
				throw new IllegalArgumentException("A data de devolucao nao pode ser anterior a data de emprestimo!");
			}
		}
		
		this.dataDevolucao = dataDevolucao;
	}
	
	// IMPLEMENTACAO DA SERIALIZACAO
	// Isso foi necessario porque as datas nao estavam sendo salvas e lidas corretamente
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(getDataEmprestimo());
		oos.writeObject(getDataDevolucao());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		setDataEmprestimo((LocalDate) ois.readObject());
		setDataDevolucao((LocalDate) ois.readObject());
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

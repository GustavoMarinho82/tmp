package entidade;

import java.io.*;
import java.time.LocalDate;

import entidade.validacao.ValidaData;

public class Emprest implements Serializable {
	// ATRIBUTOS
	private static final long serialVersionUID = 40L;
	
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
		return ValidaData.formata(dataEmprestimo);
	}
	
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public String getDataDevolucaoFormatada() {
		return (dataDevolucao != null) ? ValidaData.formata(dataDevolucao) : "Pendente";
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
		return String.format("Codigo do Livro: %d \nData do Emprestimo: %s \nData de Devolucao: %s \n",
			getCodLivro(),
			getDataEmprestimoFormatada(),
			getDataDevolucaoFormatada()
		);
	}
}

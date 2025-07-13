package entidade;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;

import entidade.validacao.*;

public class Pessoa implements Serializable {	
	// ATRIBUTOS
	private static final long serialVersionUID = 10L;

	private String nome;
	private String sobreNome;
	private LocalDate dataNasc;
	private long numCPF;
	private float peso;
	private float altura;
		
	// CONSTRUTORES
	public Pessoa(String nome, String sobreNome, LocalDate dataNasc) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dataNasc);
	}
	
	public Pessoa(String nome, String sobreNome, LocalDate dataNasc, String CPF, float peso, float altura) {
		this(nome, sobreNome, dataNasc);
		setNumCPF(CPF);
		setPeso(peso);
		setAltura(altura);
	}

	public Pessoa(String nome, String sobreNome, LocalDate dataNasc, long numCPF, float peso, float altura) {
		this(nome, sobreNome, dataNasc, Long.toString(numCPF), peso, altura);
	}
	
	// GETTERS
	public String getNome() {
		return nome;
	}
	
	public String getSobreNome() {
		return sobreNome;
	}
	
	public LocalDate getDataNasc() {
		return dataNasc;
	}
	
	public String getDataNascFormatada() {
		return ValidaData.formata(dataNasc);
	}

	public long getNumCPF() {
		return numCPF;
	}
	
	public String getCPFFormatado() {
		try {
			return ValidaCPF.formataCPF(numCPF);
		
		} catch (IllegalArgumentException e) {
			return "Nao definido";
		}
	}
	
	public float getPeso() {
		return peso;
	}
	
	public float getAltura() {
		return altura;
	}
	
	// SETTERS
	public void setNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome nao pode ser vazio!");
		}

		if (!nome.matches("[a-zA-ZÀ-ÿ]+")) {
			throw new IllegalArgumentException("O nome deve conter somente letras!");
		}
	
		this.nome = nome.trim();
	}

	public void setSobreNome(String sobreNome) {
		if (sobreNome == null || sobreNome.trim().isEmpty()) {
			throw new IllegalArgumentException("O sobrenome nao pode ser vazio!");
		}

		if (!sobreNome.matches("[a-zA-ZÀ-ÿ ]+")) {
			throw new IllegalArgumentException("O sobrenome deve conter somente letras e espaços!");
		}

		this.sobreNome = sobreNome.trim();
	}

	public void setDataNasc(LocalDate dataNasc) {
		if (!ValidaData.isAno(dataNasc.getYear())) {
			throw new IllegalArgumentException("Data de nascimento invalida!");
		}

		this.dataNasc = dataNasc;
	}

	public void setNumCPF(long numCPF) {
		if (!ValidaCPF.isCPF(Long.toString(numCPF))) {
			throw new IllegalArgumentException("Formato ou valor do CPF eh invalido! Formatos aceitos: 12345678901, 123.456.789-01, 123.456.789/01.");
		}

		this.numCPF = numCPF;
	}

	public void setNumCPF(String CPF) {
		numCPF = ValidaCPF.toLong(CPF); //Se o formato ou o valor for invalido, joga um IllegalArgumentException
	}

	public void setPeso(float peso) {
		if (peso < 0.3 || peso > 650) {
			throw new IllegalArgumentException("Peso invalido!");
		}

		this.peso = peso;
	}

	public void setAltura(float altura) {
		if (altura < 0.5 || altura > 3) {
			throw new IllegalArgumentException("Altura invalida!");
		}

		this.altura = altura;
	}
	
	// IMPLEMENTACAO DA SERIALIZACAO
	// Isso foi necessario porque a dataNasc nao estava sendo salva e lida corretamente
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(getDataNasc());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		setDataNasc((LocalDate) ois.readObject());
	}

	// OUTROS METODOS
	public int getIdade() {
		LocalDate agora = LocalDate.now(ZoneId.systemDefault());
		int idade = agora.getYear() - dataNasc.getYear();
		
		if (agora.getDayOfYear() < dataNasc.getDayOfYear()) {
			idade--;
		}
				
		return idade;
	}

	@Override
	public String toString() {
		return String.format("Nome: %s %s \nData de Nascimento: %s \nIdade: %d anos \nCPF: %s \nPeso: %.2f kg\nAltura: %.2f m\n", 
			getNome(), 
			getSobreNome(), 
			getDataNascFormatada(), 
			getIdade(), 
			getCPFFormatado(), 
			getPeso(), 
			getAltura()
		);
	}
}

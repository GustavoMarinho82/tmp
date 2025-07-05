import java.time.LocalDate;
import java.time.ZoneId;
import java.time.DateTimeException;
import java.util.Random;

public class Pessoa {	
	// ATRIBUTOS
	private static int qtd_pessoas = 0;

	private String nome;
	private String sobreNome;
	private LocalDate dataNasc;
	private long numCPF;
	private float peso;
	private float altura;
		
	// CONSTRUTORES
	public Pessoa(String nome, String sobreNome, int dia, int mes, int ano) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dia, mes, ano);
		qtd_pessoas++;
	}
	
	public Pessoa(String nome, String sobreNome, int dia, int mes, int ano, long numCPF, float peso, float altura) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dia, mes, ano);
		setNumCPF(numCPF);
		setPeso(peso);
		setAltura(altura);
		qtd_pessoas++;
	}

	public Pessoa(String nome, String sobreNome, int dia, int mes, int ano, String CPF, float peso, float altura) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dia, mes, ano);
		setNumCPF(CPF);
		setPeso(peso);
		setAltura(altura);
		qtd_pessoas++;
	}
	
	public Pessoa() {
		qtd_pessoas++;
	}
		
	// GETTERS E SETTERS
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if ((nome == null) || (nome.trim().isEmpty()))
			throw new IllegalArgumentException("O nome nao pode ser vazio!");
		
		if (!nome.matches("[a-zA-ZÀ-ÿ]+"))
			throw new IllegalArgumentException("O nome deve conter somente letras!");
	
		this.nome = nome.trim();
	}
	
	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		if ((sobreNome == null) || (sobreNome.trim().isEmpty()))
			throw new IllegalArgumentException("O sobrenome nao pode ser vazio!");
		
		if (!sobreNome.matches("[a-zA-ZÀ-ÿ ]+"))
			throw new IllegalArgumentException("O sobrenome deve conter somente letras e espaços!");
	
		this.sobreNome = sobreNome.trim();
	}

	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(int dia, int mes, int ano) {
		if (!ValidaData.isDataValida(dia, mes, ano))
			throw new IllegalArgumentException("Data de nascimento invalida!");
			
		this.dataNasc = LocalDate.of(ano, mes, dia);
	}

	public long getNumCPF() {
		return numCPF;
	}

	public void setNumCPF(long numCPF) {
		if (!ValidaCPF.isCPF(Long.toString(numCPF)))
			throw new IllegalArgumentException("Formato ou valor do CPF eh invalido! Formatos aceitos: 12345678901, 123.456.789-01, 123.456.789/01.");

		this.numCPF = numCPF;
	}

	public void setNumCPF(String CPF) {
		numCPF = ValidaCPF.toLong(CPF); //Se o formato ou o valor for invalido, joga um IllegalArgumentException
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		if (peso < 0.3 || peso > 650)
			throw new IllegalArgumentException("Peso invalido!");

		this.peso = peso;
	}
	
	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		if (altura < 0.5 || altura > 3)
			throw new IllegalArgumentException("Altura invalida!");

		this.altura = altura;
	}

	// OUTROS METODOS
	public static int numPessoas() {
		return qtd_pessoas;
	}
	
	public static void diminuirNumPessoas() {
		qtd_pessoas--;
	}

	public int getIdade() {
		LocalDate agora = LocalDate.now(ZoneId.systemDefault());
		int idade = agora.getYear() - dataNasc.getYear();
		
		if (agora.getDayOfYear() < dataNasc.getDayOfYear())
			idade--;
				
		return idade;
	}

	public String status() {
		String[] acoes = {"Caminhando", "Trabalhando", "Limpando a casa", "Se exercitando", "Estudando", "Programando", "Viajando", "Dormindo", "Assistindo TV", "Lendo um livro", "Descansando", "Fazendo nada"};
		Random aleatorio = new Random();

		return acoes[aleatorio.nextInt(12)];
	}

	@Override
	public String toString() {
		return String.format("Nome: %s \nSobrenome: %s \nIdade: %d \nCPF: %s \nPeso: %.2f \nAltura: %.2f \n", nome, sobreNome, getIdade(), ValidaCPF.formataCPF(numCPF), peso, altura);
	}
}

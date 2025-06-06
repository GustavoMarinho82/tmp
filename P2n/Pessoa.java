import java.time.LocalDate;
import java.time.ZoneId;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

public class Pessoa {	
	// ATRIBUTOS
	private static int numPessoas = 0;

	private String nome;
	private String sobreNome;
	private LocalDate dataNasc;
	private long numCPF;
	
	// CONSTRUTORES
	public Pessoa(String nome, String sobreNome, int dia, int mes, int ano) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dia, mes, ano);
		aumentarNumPessoas();
	}
	
	public Pessoa(String nome, String sobreNome, int dia, int mes, int ano, long numCPF) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dia, mes, ano);
		setNumCPF(numCPF);
		aumentarNumPessoas();
	}

	public Pessoa(String nome, String sobreNome, int dia, int mes, int ano, String CPF) {
		setNome(nome);
		setSobreNome(sobreNome);
		setDataNasc(dia, mes, ano);
		setNumCPF(CPF);
		aumentarNumPessoas();
	}
	
	public Pessoa() {
		aumentarNumPessoas();
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

	// OUTROS METODOS
	public static int numPessoas() {
		return numPessoas;
	}
	
	public static void diminuirNumPessoas() {
		numPessoas--;
	}

	public static void aumentarNumPessoas() {
		numPessoas++;
	}

	public int getIdade() {
		LocalDate agora = LocalDate.now(ZoneId.systemDefault());
		int idade = agora.getYear() - dataNasc.getYear();
		
		if (agora.getDayOfYear() < dataNasc.getDayOfYear())
			idade--;
				
		return idade;
	}

	@Override
	public String toString() {
		String genero = (this instanceof Homem) ? "(Masculino)" : (this instanceof Mulher) ? "(Feminino)" : "";
		String dataFormatada = dataNasc.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		return String.format("Nome: %s %s %s \nData de Nascimento: %s \nIdade: %d \nCPF: %s \n", nome, sobreNome, genero, dataFormatada, getIdade(), ValidaCPF.formataCPF(numCPF));
	}
}

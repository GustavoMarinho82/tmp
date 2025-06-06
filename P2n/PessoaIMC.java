public abstract class PessoaIMC extends Pessoa {	
	// ATRIBUTOS
	protected float peso;
	protected float altura;
		
	// CONSTRUTORES
	public PessoaIMC(String nome, String sobreNome, int dia, int mes, int ano, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano);
		diminuirNumPessoas(); // Essa linha eh necessaria porque evita que o num de pessoas aumente caso haja um erro em algum dos sets seguintes  
		setPeso(peso);
		setAltura(altura);
		aumentarNumPessoas();
	}
	
	public PessoaIMC(String nome, String sobreNome, int dia, int mes, int ano, long numCPF, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, numCPF);
		diminuirNumPessoas(); 
		setPeso(peso);
		setAltura(altura);
		aumentarNumPessoas();
	}

	public PessoaIMC(String nome, String sobreNome, int dia, int mes, int ano, String CPF, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, CPF);
		diminuirNumPessoas(); 
		setPeso(peso);
		setAltura(altura);
		aumentarNumPessoas();
	}
	
	public PessoaIMC() {
		super();
	}
		
	// GETTERS E SETTERS
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
	public float calculaIMC() {
		return peso / (altura * altura);
	}

	public abstract String resultIMC();
	
	@Override
	public String toString() {
		return super.toString() + String.format("Peso: %.2f \nAltura: %.2f \n", peso, altura);
	}
}

public class Homem extends PessoaIMC {
	// CONSTRUTORES
	public Homem(String nome, String sobreNome, int dia, int mes, int ano, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, peso, altura);	
	}
	
	public Homem(String nome, String sobreNome, int dia, int mes, int ano, long numCPF, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, numCPF, peso, altura);
	}

	public Homem(String nome, String sobreNome, int dia, int mes, int ano, String CPF, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, CPF, peso, altura);
	}
	
	public Homem() {
		super();
	}

	// METODOS
	@Override
	public String resultIMC() {
		float imc = calculaIMC();
 
		if (imc < 20.7) 
			return "Abaixo do peso ideal";
		
		else if (imc <= 26.4)
			return "Peso ideal";

		else
			return "Acima do peso ideal";
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("IMC: %.2f (%s)\n", calculaIMC(), resultIMC());
	}
}

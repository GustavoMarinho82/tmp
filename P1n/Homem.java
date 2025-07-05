public class Homem extends Pessoa {
	// CONSTRUTORES
	public Homem(String nome, String sobreNome, int dia, int mes, int ano) {
		super(nome, sobreNome, dia, mes, ano);	
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
	public String toString() {
		return String.format("Nome: %s \nSobrenome: %s \nIdade: %d \nGenero: Masculino \nCPF: %s \nPeso: %.2f \nAltura: %.2f \n", getNome(), getSobreNome(), getIdade(), ValidaCPF.formataCPF(getNumCPF()), getPeso(), getAltura());
	}
}

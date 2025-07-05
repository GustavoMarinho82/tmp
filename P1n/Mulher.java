public class Mulher extends Pessoa {
	// CONSTRUTORES
	public Mulher(String nome, String sobreNome, int dia, int mes, int ano) {
		super(nome, sobreNome, dia, mes, ano);	
	}
	
	public Mulher(String nome, String sobreNome, int dia, int mes, int ano, long numCPF, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, numCPF, peso, altura);
	}

	public Mulher(String nome, String sobreNome, int dia, int mes, int ano, String CPF, float peso, float altura) {
		super(nome, sobreNome, dia, mes, ano, CPF, peso, altura);
	}
	
	public Mulher() {
		super();
	}

	// METODOS
	@Override
	public String toString() {
		return String.format("Nome: %s \nSobrenome: %s \nIdade: %d \nGenero: Feminino \nCPF: %s \nPeso: %.2f \nAltura: %.2f \n", getNome(), getSobreNome(), getIdade(), ValidaCPF.formataCPF(getNumCPF()), getPeso(), getAltura());
	}
}

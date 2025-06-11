import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.time.LocalDate;

public class MinhaListaOrdenavel {
	// ENUM
	public enum Criterio {
		NOME(1), NOME_R(2), DATA_NASC(3), DATA_NASC_R(4), IDADE(5), IDADE_R(6), CPF(7), CPF_R(8), 
		PESO(9), PESO_R(10), ALTURA(11), ALTURA_R(12), IMC(13), IMC_R(14), GENERO(15), GENERO_R(16);

		private final int num;

		Criterio(int num) {
			this.num = num;
		}

		private int getNum() {
			return num;
		}
		
		public static Criterio fromNum(int num) {
			for (Criterio criterio: Criterio.values()) {
				if (criterio.getNum() == num)
					return criterio;
			}

			throw new IllegalArgumentException("Numero de criterio inexistente! Numeros validos: 1 a 16.");
		}
	}

	// ATRIBUTOS
	private ArrayList<PessoaIMC> pessoas = new ArrayList<>();

	private static Comparator<PessoaIMC> nomeC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			String nome1 = p1.getNome() + " " + p1.getSobreNome();
			String nome2 = p2.getNome() + " " + p2.getSobreNome();

			return nome1.compareTo(nome2);
		}
	};
	
	private static Comparator<PessoaIMC> dataNascC = new Comparator<>() { // Serve para comparar idades tambem
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			LocalDate dataNasc1 = p1.getDataNasc();
			LocalDate dataNasc2 = p2.getDataNasc();

			return dataNasc1.compareTo(dataNasc2);
		}
	};

	private static Comparator<PessoaIMC> numCPFC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			long numCPF1 = p1.getNumCPF();
			long numCPF2 = p2.getNumCPF();

			return Long.compare(numCPF1, numCPF2);
		}
	};
	
	private static Comparator<PessoaIMC> pesoC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			float peso1 = p1.getPeso();
			float peso2 = p2.getPeso();

			return Float.compare(peso1, peso2);
		}
	};
	
	private static Comparator<PessoaIMC> alturaC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			float altura1 = p1.getAltura();
			float altura2 = p2.getAltura();

			return Float.compare(altura1, altura2);
		}
	};

	private static Comparator<PessoaIMC> IMCC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			float IMC1 = p1.calculaIMC();
			float IMC2 = p2.calculaIMC();

			return Float.compare(IMC1, IMC2);
		}
	};

	private static Comparator<PessoaIMC> generoC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			int genero1 = (p1 instanceof Mulher) ? 1 : 0;
			int genero2 = (p2 instanceof Mulher) ? 1 : 0;

			return genero1 - genero2;
		}
	};
	
	// METODOS
	public void add(PessoaIMC p) {
		pessoas.add(p);
	}
	
	public PessoaIMC get(int i) {
		return pessoas.get(i);
	}

	public ArrayList<PessoaIMC> ordena(int numCriterio) {
		Criterio criterio = Criterio.fromNum(numCriterio);
		ArrayList<PessoaIMC> pessoasOrd = new ArrayList<>(pessoas);

		switch (criterio) {
			case NOME: Collections.sort(pessoasOrd, nomeC);
			break;

			case NOME_R: Collections.sort(pessoasOrd, nomeC.reversed());
			break;

			case DATA_NASC: case IDADE_R: Collections.sort(pessoasOrd, dataNascC);
			break;

			case DATA_NASC_R: case IDADE: Collections.sort(pessoasOrd, dataNascC.reversed());
			break;

			case CPF: Collections.sort(pessoasOrd, numCPFC);
			break;

			case CPF_R: Collections.sort(pessoasOrd, numCPFC.reversed());
			break;

			case PESO: Collections.sort(pessoasOrd, pesoC);
			break;

			case PESO_R: Collections.sort(pessoasOrd, pesoC.reversed());
			break;

			case ALTURA: Collections.sort(pessoasOrd, alturaC);
			break;

			case ALTURA_R: Collections.sort(pessoasOrd, alturaC.reversed());
			break;

			case IMC: Collections.sort(pessoasOrd, IMCC);
			break;

			case IMC_R: Collections.sort(pessoasOrd, IMCC.reversed());
			break;

			case GENERO: Collections.sort(pessoasOrd, generoC);
			break;

			case GENERO_R: Collections.sort(pessoasOrd, generoC.reversed());
			break;
		}

		return pessoasOrd;
	}
}

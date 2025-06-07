import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.time.LocalDate;

public class MinhaListaOrdenavel {
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
	
	private static Comparator<PessoaIMC> dataNascC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			LocalDate dataNasc1 = p1.getDataNasc();
			LocalDate dataNasc2 = p2.getDataNasc();

			return dataNasc1.compareTo(dataNasc2);
		}
	};

	private static Comparator<PessoaIMC> idadeC = new Comparator<>() {
		@Override
		public int compare (PessoaIMC p1, PessoaIMC p2) {
			int idade1 = p1.getIdade();
			int idade2 = p2.getIdade();

			return idade1 - idade2;
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
			int genero1 = (p1 instanceof Homem) ? 1 : 0;
			int genero2 = (p2 instanceof Homem) ? 1 : 0;

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

	public ArrayList<PessoaIMC> ordena(int criterio) {
		ArrayList<PessoaIMC> pessoasOrd = new ArrayList<>(pessoas);
		
		switch (criterio) {
			case 1: Collections.sort(pessoasOrd, nomeC);
			break;
			case 2: Collections.sort(pessoasOrd, nomeC.reversed());
			break;
			case 3: Collections.sort(pessoasOrd, dataNascC);
			break;
			case 4: Collections.sort(pessoasOrd, dataNascC.reversed());
			break;
			case 5: Collections.sort(pessoasOrd, idadeC);
			break;
			case 6: Collections.sort(pessoasOrd, idadeC.reversed());
			break;
			case 7: Collections.sort(pessoasOrd, numCPFC);
			break;
			case 8: Collections.sort(pessoasOrd, numCPFC.reversed());
			break;
			case 9: Collections.sort(pessoasOrd, pesoC);
			break;
			case 10: Collections.sort(pessoasOrd, pesoC.reversed());
			break;
			case 11: Collections.sort(pessoasOrd, alturaC);
			break;
			case 12: Collections.sort(pessoasOrd, alturaC.reversed());
			break;
			case 13: Collections.sort(pessoasOrd, IMCC);
			break;
			case 14: Collections.sort(pessoasOrd, IMCC.reversed());
			break;
			case 15: Collections.sort(pessoasOrd, generoC);
			break;
			case 16: Collections.sort(pessoasOrd, generoC.reversed());
			break;
			default: throw new IllegalArgumentException("Ordenação inexistente! Digite um número de 1 a 16.");
		}

		return pessoasOrd;		
	}
}

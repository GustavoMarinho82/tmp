import java.util.ArrayList;

public class MinhaListaOrdenavel {
	ArrayList<PessoaIMC> pessoas = new ArrayList<>();

	public void add(PessoaIMC p) {
		pessoas.add(p);
	}
	
	public PessoaIMC get(index i) {
		return pessoas.get(i);
	}
}

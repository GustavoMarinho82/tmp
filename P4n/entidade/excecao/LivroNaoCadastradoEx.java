package entidade.excecao;

public class LivroNaoCadastradoEx extends Exception {
	// CONSTRUTORES
	public LivroNaoCadastradoEx() {
		super();
	}

	public LivroNaoCadastradoEx(String mensagem) {
		super(mensagem);
	}
}

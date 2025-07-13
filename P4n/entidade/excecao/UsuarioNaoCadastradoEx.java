package entidade.excecao;

public class UsuarioNaoCadastradoEx extends Exception {
	// CONSTRUTORES
	public UsuarioNaoCadastradoEx() {
		super();
	}

	public UsuarioNaoCadastradoEx(String mensagem) {
		super(mensagem);
	}
}

package controle;

public enum Acao {
	SELECIONAR_ARQ_USUARIOS,
	SELECIONAR_ARQ_LIVROS,
	INICIAR_BIBLIOTECA,
	SALVAR_CADASTROS,
	CARREGAR_CADASTROS,
	SELECIONAR_ARQUIVOS,
	CADASTRAR_USUARIO,
	CADASTRAR_LIVRO,
	FAZER_EMPRESTIMO,
	FAZER_DEVOLUCAO;
	
	// METODOS
	public static Acao fromString(String palavra) {
		for (Acao acao : Acao.values()) {
			if (acao.toString().equalsIgnoreCase(palavra)) {
				return acao;
			}
		}
		
		return null;
	}
	
	public static boolean isAcao(String palavra) {
		return (fromString(palavra) != null);
	}
} 

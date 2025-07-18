package gui.navegacao;

public enum Tela {
	INICIAL,
	PRINCIPAL,
	CADASTRO,
	CADASTRO_USUARIO,
	CADASTRO_LIVRO,
	MANUTENCAO,
	EMPRESTIMO_DEVOLUCAO,
	RELATORIO;
	
	// METODOS
	public static Tela fromString(String palavra) {
		for (Tela tela : Tela.values()) {
			if (tela.toString().equalsIgnoreCase(palavra)) {
				return tela;
			}
		}
		
		return null;
	}
	
	public static boolean isTela(String palavra) {
		return (fromString(palavra) != null);
	}
} 

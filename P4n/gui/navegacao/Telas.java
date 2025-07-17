package gui.navegacao;

public enum Telas {
	INICIAL,
	PRINCIPAL,
	CADASTRO,
	CAD_USUARIO,
	CAD_LIVRO,
	MANUTENCAO,
	EMPRESTIMO,
	RELATORIO;
	
	@Override
	public String toString() {
		return this.name();
	}
	
	public static boolean isTela(String nome) {
		for (Telas tela : Telas.values()) {
			if (tela.toString().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		
		return false;
	}
} 

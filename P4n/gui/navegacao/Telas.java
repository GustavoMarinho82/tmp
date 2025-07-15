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
} 

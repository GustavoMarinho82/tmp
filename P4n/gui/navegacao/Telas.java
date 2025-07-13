package gui.navegacao;

public enum Telas {
	INICIAL,
	PRINCIPAL,
	CADASTRO,
	MANUTENCAO,
	EMPRESTIMO,
	RELATORIO;
	
	@Override
	public String toString() {
		return this.name();
	}
} 

package lp2g13.biblioteca;

public class ValidaCPF {
	// METODOS
	public static boolean formatoValido(String CPF) {
		return CPF.matches("(\\d{11})|(\\d{3}(\\.\\d{3}){2}[-/]\\d{2})");
	}

	public static boolean isCPF(String CPF) {
		if (!formatoValido(CPF)) {
			return false;
		}

		//Verificando se o CPF é composto por apenas um numero
		for (int i = 0; i < 10; i++) {
			if (CPF.matches(String.format("(%1$d{11})|(%1$d{3}(.%1$d{3}){2}.%1$d{2})", i))) {
				return false;
			}
		}
		
		//Verificando o primeiro digito de verificacao
		int somatorio = 0;

		for (int i = 0, peso = 10; peso >= 2; i++) {
			if (Character.isDigit(CPF.charAt(i))) {
				somatorio += (CPF.charAt(i) - '0') * peso;
				peso--;
			}
		}
		
		int resto = (somatorio % 11);
		int dvEsperado = (resto > 1) ? (11 - resto) : 0;

		if (dvEsperado != (CPF.charAt(CPF.length() - 2) - '0')) {
			return false;
		}

		//Verificando o segundo digito de verificacao 
		somatorio = 0;

		for (int i = 0, peso = 11; peso >= 2; i++) {
			if (Character.isDigit(CPF.charAt(i))) {
				somatorio += (CPF.charAt(i) - '0') * peso;
				peso--;
			}
		}
		
		resto = (somatorio % 11);
		dvEsperado = (resto > 1) ? (11 - resto) : 0;

		if (dvEsperado != (CPF.charAt(CPF.length() - 1) - '0')) {
			return false;
        }

		return true;
	}
	
	public static long toLong(String CPF) {
		if (!isCPF(CPF)) {
			throw new IllegalArgumentException("Formato ou valor do CPF invalido! Formatos aceitos: 12345678901, 123.456.789-01, 123.456.789/01");
		}

		long cpfLong = 0;
		
		for (int i = 0; i < CPF.length(); i++) {
			if (Character.isDigit(CPF.charAt(i))) {
				cpfLong = cpfLong * 10 + (CPF.charAt(i) - '0');
			}
		}
		
		return cpfLong;
	}
	
	public static String formataCPF(long numCPF) {
		String CPF = String.format("%011d", numCPF);

		if (!isCPF(CPF)) {
			throw new IllegalArgumentException("Formato ou valor do CPF invalido! Formatos aceitos: 12345678901, 123.456.789-01, 123.456.789/01");
		}

		return CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
	}

	public static String formataCPF(String CPF) {
		return formataCPF(toLong(CPF));
	}
}

package lp2g13.biblioteca;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaData {
	// ATRIBUTOS
	private static final String PADRAO1 = "(\\d+)[-/\\\\](\\d+)[-/\\\\](\\d+)";
	private static final String PADRAO2 = "(\\d+) de ([A-Za-z]+) de (\\d+)";
	private static final DateTimeFormatter FORMATADOR = new DateTimeFormatterBuilder()
		.appendOptional(DateTimeFormatter.ofPattern("d-M-yyyy"))
		.appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
		.toFormatter();

	// ENUM
	private enum Mes {
		JANEIRO(1, 31), FEVEREIRO(2, 28), MARCO(3, 31), ABRIL(4, 30), MAIO(5, 31), JUNHO(6, 30), JULHO(7, 31), AGOSTO(8, 31), SETEMBRO(9, 30), OUTUBRO(10, 31), NOVEMBRO(11, 30), DEZEMBRO(12, 31);
		
		private final int numero;
		private final int dias;

		Mes(int numero, int dias) {
			this.numero = numero;
			this.dias = dias;
    	}
		
		public int numero() {
			return numero;
		}
		
		public int dias() {
			// Retorna o numero de dias de um mes, desconsiderando anos bissextos
			return dias;
		}
		
		public int dias(int ano) {
			// Retorna o numero de dias de um mes, considerando anos bissextos
			boolean anoBissexto = (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));
			return (anoBissexto && this == FEVEREIRO) ? 29 : dias();
		}
		
		public static Mes fromNome(String nome) {
			return Mes.valueOf(nome.toUpperCase());
		}

		public static Mes fromNumero(int numero) {
			for (Mes mes: Mes.values()) {
				if (mes.numero() == numero) {
					return mes;
				}
			}

			throw new IllegalArgumentException("Numero do mes inexistente! Pode ser um valor entre 1 e 12.");
		}
	}
	
	// METODOS
	public static boolean isDia(int dia) {
		return (dia >= 1 && dia <= 31);
	}
	
	public static boolean isDia(String dia) {
		try {
			return isDia(Integer.valueOf(dia));
		
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isMes(int mes) {
		return (mes >= 1 && mes <= 12);
	}

	public static boolean isMes(String mes) {
		try {
			Mes.fromNome(mes);
			return true;
			
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public static boolean isAno(int ano) {
		int anoAtual = LocalDate.now(ZoneId.systemDefault()).getYear();
		
		return (ano >= (anoAtual - 120) && ano <= anoAtual);
	}
	
	public static boolean isAno(String ano) {
		try {
			return isAno(Integer.valueOf(ano));
		
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isDataValida(int dia, int mes, int ano) {
		try {
			// Nao eh necessario conferir se o mes eh valido, porque, caso invalido, o Mes.fromNumero() vai gerar um erro
			return (dia >= 1 && dia <= Mes.fromNumero(mes).dias(ano) && isAno(ano));
		
		} catch (IllegalArgumentException e) {
			return false;
		}
	} 

	public static int[] toInt(String data) {
		// Converte uma string de data para um array de inteiros ([dia, mes, ano])
		// Formatos aceitos: '01-01-2000', '01/01/2000', '01\01\2000' e '01 de janeiro de 2000' 
		boolean ehPadrao1 = data.matches(PADRAO1);
		boolean ehPadrao2 = data.matches(PADRAO2);
		
		if (!ehPadrao1 && !ehPadrao2) {
			throw new IllegalArgumentException("Formato de data invalido! Formatos aceitos: '01-01-2000', '01/01/2000', '01\\01\\2000' e '01 de janeiro de 2000'");
		}

		int dia, mes, ano;
		Pattern padrao = Pattern.compile((ehPadrao1) ? PADRAO1 : PADRAO2);
		Matcher reconhecedor = padrao.matcher(data);

		if (reconhecedor.find()) {
	 		dia = Integer.valueOf(reconhecedor.group(1));
			mes = (ehPadrao1) ? Integer.valueOf(reconhecedor.group(2)) : Mes.fromNome(reconhecedor.group(2)).numero(); // Pode propagar um IllegalArgumentException de Mes.fromNome()
			ano = Integer.valueOf(reconhecedor.group(3));
		
		} else {
			throw new IllegalArgumentException("Data invalida!");
		}

		if (!isDataValida(dia, mes, ano)) {
			throw new IllegalArgumentException("Data invalida!");
		}
		
		return new int[] {dia, mes, ano};
	}

	public static boolean isDataValida(String data) {
		// Formatos aceitos: '01-01-2000', '01/01/2000', '01\01\2000' e '01 de janeiro de 2000'
		try {
			toInt(data);
			return true;

		} catch (Exception e) {
			return false;
		}
	}
	
	public static LocalDate toLocalDate(String data) {
		return LocalDate.parse(data, FORMATADOR);
	}
	
	public static String formata(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class P2nX {
	// ATRIBUTOS
	private static Scanner teclado;
	
	// METODO PRINCIPAL
	public static void main(String[] args) {
		MinhaListaOrdenavel lista = new MinhaListaOrdenavel();
		preencherLista(lista);
		
		teclado = new Scanner(System.in);
		
		realizarAcao(lista);
		
		teclado.close();
	}
	
	// OUTROS METODOS
	private static void preencherLista(MinhaListaOrdenavel lista) {
		lista.add(new Homem("Daniel", "Gomes", 15, 3, 1998, "390.533.447-05", 112.5f, 1.85f));
		lista.add(new Mulher("Julia", "Rocha", 12, 7, 2006, "548.739.670-15", 50.5f, 1.59f));
		lista.add(new Homem("Henrique", "Nunes", 2, 8, 2005, "732.151.490-01", 82.0f, 1.76f));
		lista.add(new Mulher("Ana", "Beatriz", 9, 12, 2003, "529.982.247-25", 65.0f, 1.60f));
		lista.add(new Homem("Igor", "Taveira", 27, 11, 1999, "524.765.790-09", 70.2f, 1.76f));
		lista.add(new Mulher("Giovana", "Lira", 30, 9, 2002, "423.792.760-13", 58.3f, 1.72f));
		lista.add(new Homem("Gabriel", "Torres", 6, 5, 1999, "225.517.520-78", 62.0f, 1.67f));
		lista.add(new Mulher("Claudia", "Rosa", 18, 4, 1997, "362.426.180-01", 64.7f, 1.68f));
		lista.add(new Homem("Caio", "Almeida", 20, 1, 1985, "036.469.270-70", 95.3f, 1.80f));
		lista.add(new Mulher("Nina", "Silva", 5, 10, 2007, "837.151.400-03", 42.4f, 1.62f));
	}
	
	private static void realizarAcao(MinhaListaOrdenavel lista) {
		while (true) {
			try {
				System.out.println("\nO que voce deseja fazer?");
				System.out.println("1 - Imprimir a lista");
				System.out.println("2 - Fechar o programa");
				System.out.print("Digite o numero da opcao escolhida: ");
				
				int opcao = teclado.nextInt();
				teclado.nextLine();
				
				switch (opcao) {
					case 1: imprimirLista(lista);
					break;	
					case 2: break;
					default: throw new IllegalArgumentException("Opcao invalida! Digite 1 para imprimir a lista ou 2 para fechar o programa.");
				}
				
				if (opcao == 2)
					break;
				
			} catch (InputMismatchException e) {
				System.err.println("Tipo invalido de opcao! Digite um numero.");
				teclado.nextLine();
				
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private static void imprimirLista(MinhaListaOrdenavel lista) {
		while (true) {
			try {
				System.out.println("\nQual o seu criterio para ordenação?");
				System.out.println("1 - Alfabetica (A-Z)");
				System.out.println("2 - Alfabetica (Z-A)");
				System.out.println("3 - Data de Nascimento (Crescente)");
				System.out.println("4 - Data de Nascimento (Decrescente)");
				System.out.println("5 - Idade (Crescente)");
				System.out.println("6 - Idade (Decrescente)");
				System.out.println("7 - CPF (Crescente)");
				System.out.println("8 - CPF (Decrescente)");
				System.out.println("9 - Peso (Crescente)");
				System.out.println("10 - Peso (Decrescente)");
				System.out.println("11 - Altura (Crescente)");
				System.out.println("12 - Altura (Decrescente)");
				System.out.println("13 - IMC (Crescente)");
				System.out.println("14 - IMC (Decrescente)");
				System.out.println("15 - Genero (Homens - Mulheres)");
				System.out.println("16 - Genero (Mulheres - Homens)");
				System.out.print("Digite o criterio escolhido: ");
				
				int criterio = teclado.nextInt();
				teclado.nextLine();
				
				ArrayList<PessoaIMC> listaOrd = lista.ordena(criterio);
				
				for (PessoaIMC pessoa: listaOrd) {
					System.out.println("=================================");
					System.out.print(pessoa);
				}
				
				System.out.println("=================================");
				System.out.print("Digite algo para continuar... ");
				teclado.nextLine();
				break;
				
			} catch (InputMismatchException e) {
				System.err.println("Tipo inválido de opcao! Digite um numero.");
				teclado.nextLine();
				
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}
		}
	}	
}

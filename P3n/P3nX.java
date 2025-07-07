import java.io.*;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import lp2g13.biblioteca.*;

public class P3nX {
	// ATRIBUTOS
	private static Scanner teclado;
	private static Biblioteca biblioteca;
	private static String nomeArqUsuarios;
	private static String nomeArqLivros;

	// METODO PRINCIPAL
	public static void main(String[] args) {
		teclado = new Scanner(System.in);
		
		biblioteca = iniciarBiblioteca();
		rotinaEscolhaModulos();
		
		teclado.close();
	}

	// METODOS AUXILIARES
	private static String lerLinha(String prompt) {
		try {
			System.out.print(prompt);
			return teclado.nextLine();
			
		} catch (NoSuchElementException e) {
			System.err.println("\nERRO: Comando de EOF enviado! Encerrando o programa.");
			System.exit(1);
			return "";
		}
	}
	
	private static int lerInteiro(String prompt) {
		while(true) {
			try {
				System.out.print(prompt);
				
				int i = teclado.nextInt();
				teclado.nextLine();
				
				return i;
				
			} catch (InputMismatchException e) {
				System.err.println("\nERRO: Nao foi inserido um inteiro.");
				teclado.nextLine();
				
			} catch (NoSuchElementException e) {
				System.err.println("\nERRO: Comando de EOF enviado! Encerrando o programa.");
				System.exit(1);
			}
		}
	}
	
	private static float lerFloat(String prompt) {
		while(true) {
			try {
				System.out.print(prompt);
				
				float f = teclado.nextFloat();
				teclado.nextLine();
				
				return f;
					
			} catch (InputMismatchException e) {
				System.err.println("\nERRO: Nao foi inserido um ponto flutuante.");
				teclado.nextLine();
			
			} catch (NoSuchElementException e) {
				System.err.println("\nERRO: Comando de EOF enviado! Encerrando o programa.");
				System.exit(1);
			}
		}
	}
	
	private static Biblioteca iniciarBiblioteca() {
		while (true) {
			String resposta = lerLinha("Voce deseja iniciar a biblioteca a partir de arquivos de cadastro? (s/n): ");
				
			switch (resposta.toLowerCase()) {
				case "n":
					return new Biblioteca();
						
				case "s":
					return carregarBibliotecaArquivos();
						
				default:
					System.out.println("Resposta invalida! Digite 's' ou 'n'. \n");
			}
		}
	}
	
	private static Biblioteca carregarBibliotecaArquivos() {
		while (true) {
			try {
				nomeArqUsuarios = lerLinha("Digite o nome do arquivo que contem os cadastros de usuarios: ");
				nomeArqLivros = lerLinha("Agora, digite o nome do arquivo que contem os cadastros de livros: ");

				return new Biblioteca(nomeArqUsuarios, nomeArqLivros);
			
			} catch (FileNotFoundException e) {
				System.err.println("ERRO: Um dos arquivos nao foi encontrado ou nao pode ser lido.");
			
			} catch (IOException e) {
				System.err.println("ERRO: Um problema de E/S ocorreu. Mais informacoes: \n" + e.getMessage());

			} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
				System.err.println("ERRO: Um dos arquivos informados nao contem o cadastro de usuarios/livros.");
			}
			
			nomeArqUsuarios = nomeArqLivros = null;
			
			String resposta = lerLinha("\nVocê deseja desistir de iniciar a partir de arquivos de cadastro, e iniciar zerado? (s / qualquer coisa): ");
			
			if (resposta.toLowerCase().equals("s")) {
				return new Biblioteca();
			}
		}
	}
	
	private static void rotinaEscolhaModulos() {
		loop: while (true) {
			System.out.println("\nO que voce deseja fazer?");
			System.out.println("1 - Manutencao");
			System.out.println("2 - Cadastro");
			System.out.println("3 - Emprestimo");
			System.out.println("4 - Finalizar o programa");
			
			int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
			
			switch (opcao) {
				case 1:
					manutencao();
					break;
				
				case 2:
//					cadastro();
					break;
					
				case 3:
//					emprestimo();
					break;
					
				case 4:
					break loop;
					
				default:
					System.out.println("Insira um numero de 1 a 4!");
			}
		}
	}
	
	private static void manutencao() {
		loop: while (true) {
			System.out.println("\nO que voce deseja fazer?");
			System.out.println(String.format("Arquivo de usuarios: %s | Arquivo de livros: %s", 
				(nomeArqUsuarios != null) ? nomeArqUsuarios : "Nao definido",
				(nomeArqLivros != null) ? nomeArqLivros : "Nao definido"
			));
			
			System.out.println("1 - Salvar os cadastros");
			System.out.println("2 - Salvar os cadastros em outros arquivos");
			System.out.println("3 - Carregar os cadastros");
			System.out.println("4 - Carregar os cadastros de outros arquivos");
			System.out.println("5 - Selecionar arquivos");
			System.out.println("6 - Voltar");
			
			int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
			
			try {
				switch (opcao) {
					case 1:
						if (nomeArqUsuarios != null && nomeArqLivros != null) {
							biblioteca.salvaArqUsu(nomeArqUsuarios);
							biblioteca.salvaArqLiv(nomeArqLivros);
								
						} else {
							System.err.println("ERRO: Algum dos arquivos nao esta definido.");
						}
						
						break;
						
					case 2:
						biblioteca.salvaArqUsu(lerLinha("Digite o nome do arquivo para salvar os cadastros de usuarios: "));
						biblioteca.salvaArqLiv(lerLinha("Agora, digite o nome do arquivo para salvar os cadastros de livros: "));
						
						break;
						
					case 3:
						if (nomeArqUsuarios != null && nomeArqLivros != null) {
							biblioteca.leArqUsu(nomeArqUsuarios);
							biblioteca.leArqLiv(nomeArqLivros);
								
						} else {
							System.err.println("ERRO: Algum dos arquivos nao esta definido.");
						}
						
						break;
					
					case 4:
						biblioteca.leArqUsu(lerLinha("Digite o nome do arquivo para carregar os cadastros de usuarios: "));
						biblioteca.leArqLiv(lerLinha("Agora, digite o nome do arquivo para carregar os cadastros de livros: "));
						
						break;
						
					case 5:
						nomeArqUsuarios = lerLinha("Digite o nome do arquivo de usuarios: ");
						nomeArqLivros = lerLinha("Agora, digite o nome do arquivo de livros: ");
						
						break;
						
					case 6:
						break loop;
						
					default:
						System.out.println("Insira um numero de 1 a 6!");
				}
			} catch (FileNotFoundException e) {
				System.err.println("ERRO: Um dos arquivos nao foi encontrado ou nao pode ser lido.");
			
			} catch (IOException e) {
				System.err.println("ERRO: Um problema de E/S ocorreu. Mais detalhes: \n" + e.getMessage());

			} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
				System.err.println("ERRO: Um dos arquivos informados nao contem o cadastro de usuarios/livros.");
			}
		}
	}
		
//	private void cadastro() {
	
//	}
	
//	private void emprestimo() {
	
//	}
}

import java.io.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import java.util.NoSuchElementException;
import java.util.Scanner;

import lp2g13.biblioteca.*;

public class P3nX {
	// ATRIBUTOS
	private Scanner teclado;
	private Biblioteca biblioteca;
	private String nomeArqUsuarios;
	private String nomeArqLivros;

	private static final DateTimeFormatter FORMATADOR_DATA = new DateTimeFormatterBuilder()
		.appendOptional(DateTimeFormatter.ofPattern("d-M-yyyy"))
		.appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
		.toFormatter();
		
	// METODO PRINCIPAL
	public static void main(String[] args) {
		P3nX programa = new P3nX();
		programa.executa();
	}

	private void executa() {
		teclado = new Scanner(System.in);
		
		biblioteca = iniciarBiblioteca();
		rotinaEscolhaModulos();
		
		teclado.close();
	}
	
	// METODOS AUXILIARES
	private Biblioteca iniciarBiblioteca() {
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
	
	private Biblioteca carregarBibliotecaArquivos() {
		while (true) {
			try {
				nomeArqUsuarios = lerLinha("Digite o nome do arquivo que contem os cadastros de usuarios: ");
				nomeArqLivros = lerLinha("Agora, digite o nome do arquivo que contem os cadastros de livros: ");

				return new Biblioteca(nomeArqUsuarios, nomeArqLivros);
			
			} catch (FileNotFoundException e) {
				System.err.println("ERRO: Um dos arquivos nao foi encontrado ou nao pode ser lido.");
			
			} catch (IOException e) {
				System.err.println("ERRO: Um problema de E/S ocorreu. Mais informacoes: \n" + e);

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
	
	private void rotinaEscolhaModulos() {
		loop: while (true) {
			System.out.println("\nO que voce deseja fazer?");
			System.out.println("1 - Manutencao");
			System.out.println("2 - Cadastro");
			System.out.println("3 - Emprestimo");
			System.out.println("4 - Relatorio");
			System.out.println("5 - Finalizar o programa");
			
			int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
			
			switch (opcao) {
				case 1:
					manutencao();
					break;
				
				case 2:
					cadastro();
					break;
					
				case 3:
//					emprestimo();
					break;
					
				case 4:
					relatorio();
					break;
					
				case 5:
					break loop;
					
				default:
					System.out.println("Insira um numero de 1 a 5!");
			}
		}
	}
	
	private void manutencao() {
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
			
			switch (opcao) {
				case 1:
					salvarDados(nomeArqUsuarios, nomeArqLivros);
					break;
					
				case 2:
					salvarDados(
						lerLinha("Digite o nome do arquivo para salvar os cadastros de usuarios: "),
						lerLinha("Agora, digite o nome do arquivo para salvar os cadastros de livros: ")
					);
					break;
					
				case 3:
					carregarDadosArqs(nomeArqUsuarios, nomeArqLivros);
					break;
				
				case 4:
					carregarDadosArqs(
						lerLinha("Digite o nome do arquivo para salvar os cadastros de usuarios: "), 
						lerLinha("Agora, digite o nome do arquivo para salvar os cadastros de livros: ")
					);
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
		}
	}
	private boolean arqDefinido(String arq) {
		return !(arq == null || arq.trim().isEmpty());
	}
	
	private boolean arqsDefinidos(String arq1, String arq2) {
		return (arqDefinido(arq1) && arqDefinido(arq2));
	}
	
	private void salvarDados(String nomeArqU, String nomeArqL) {
		try {
			if (arqsDefinidos(nomeArqU, nomeArqL)) {
				biblioteca.salvaArqUsu(nomeArqU);
				biblioteca.salvaArqLiv(nomeArqL);
				
				System.out.println("Dados salvos com sucesso!");
			
			} else {
				System.err.println("ERRO: Algum dos arquivos nao esta definido.");
			}
			
			digiteParaContinuar()
			
		} catch (FileNotFoundException e) {
			System.err.println("ERRO: Um dos arquivos nao pode ser escrito.");
			
		} catch (IOException e) {
			System.err.println("ERRO: Um problema de E/S ocorreu. Mais detalhes: \n" + e);
		}
	}
	
	private void salvarDadosUsu(String nomeArq) {
		try {
			if (arqDefinido(nomeArq)) {
				biblioteca.salvaArqUsu(nomeArq);
				
				System.out.println("Dados salvos com sucesso!");
			
			} else {
				System.err.println("ERRO: O arquivo nao esta definido.");
			}
			
			digiteParaContinuar()
			
		} catch (FileNotFoundException e) {
			System.err.println("ERRO: O arquivos nao pode ser escrito.");
			
		} catch (IOException e) {
			System.err.println("ERRO: Um problema de E/S ocorreu. Mais detalhes: \n" + e);
		}
	}
	
	private void salvarDadosLiv(String nomeArq) {
		try {
			if (arqDefinido(nomeArq)) {
				biblioteca.salvaArqLiv(nomeArq);
				
				System.out.println("Dados salvos com sucesso!");
			
			} else {
				System.err.println("ERRO: O arquivo nao esta definido.");
			}
			
			digiteParaContinuar()
			
		} catch (FileNotFoundException e) {
			System.err.println("ERRO: O arquivo nao pode ser escrito.");
			
		} catch (IOException e) {
			System.err.println("ERRO: Um problema de E/S ocorreu. Mais detalhes: \n" + e);
		}
	}
	
	private void carregarDadosArqs(String nomeArqU, String nomeArqL) {
		try {
			if (arqsDefinidos(nomeArqU, nomeArqL)) {
				biblioteca.leArqUsu(nomeArqU);
				biblioteca.leArqLiv(nomeArqL);
			
				System.out.println("Dados carregados com sucesso!");
				
			} else {
				System.err.println("ERRO: Algum dos arquivos nao esta definido.");
			}
			
			digiteParaContinuar()
		
		} catch (FileNotFoundException e) {
				System.err.println("ERRO: Um dos arquivos nao foi encontrado ou nao pode ser lido.");
			
		} catch (IOException e) {
				System.err.println("ERRO: Um problema de E/S ocorreu. Mais detalhes: \n" + e);

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
				System.err.println("ERRO: Um dos arquivos informados nao contem o cadastro de usuarios/livros.");
		}
	}
	
	private void cadastro() {
		loop: while (true) {
			System.out.println("\nO que voce deseja fazer?");
			System.out.println("1 - Cadastrar um usuario");
			System.out.println("2 - Cadastrar um livro");
			System.out.println("3 - Salvar cadastro");
			System.out.println("4 - Voltar");
			
			int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
			
			switch (opcao) {
				case 1:
					cadastrarUsuario();
					break;
					
				case 2:
					cadastrarLivro();
					break;
					
				case 3:
					salvarCadastro();
					break;
					
				case 4:
					break loop;
				
				default:
					System.out.println("Insira um numero de 1 a 4!");
			}
		}
	}

	private void cadastrarUsuario() {
		try {
			String nome = lerLinha("Digite o nome do usuario: ");
			String sobreNome = lerLinha("Digite o sobrenome do usuario: ");
			String dataNascStr = lerLinha("Digite a data de nascimento do usuario: ");
			String CPF = lerLinha("Digite o CPF do usuario: ");
			Float peso = lerFloat("Digite o peso do usuario: ");
			Float altura = lerFloat("Digite a altura do usuario: ");
			String endereco = lerLinha("Digite o endereco do usuario: ");
			
			LocalDate dataNasc = LocalDate.parse(dataNascStr, FORMATADOR_DATA);
			
			biblioteca.cadastraUsuario(new Usuario(nome, sobreNome, dataNasc, CPF, peso, altura, endereco));
			
			System.out.println("Usuario cadastrado com sucesso!");
			
		} catch (IllegalArgumentException e) {
			System.err.println("ERRO: " + e.getMessage());
			
		} catch (DateTimeParseException e) {
			System.err.println("ERRO: Formato ou valor de data de nascimento invalido! Formatos aceitos: 01-01-2000 e 01/01/2000.");
		}
		
		digiteParaContinuar()
	}
	
	private void cadastrarLivro() {
		try {
			String titulo = lerLinha("Digite o titulo do livro: ");
			int codigo = lerInteiro("Digite o codigo do livro: ");
			String categoria = lerLinha("Digite a categoria do livro: ");
			int disponiveis = lerInteiro("Quantas copias desse livro estao no acervo: ");
			int emprestados = lerInteiro("Quantas copias desse livro estao sendo emprestadas no momento: ");
			
			biblioteca.cadastraLivro(new Livro(codigo, titulo, categoria, disponiveis, emprestados));
			
			System.out.println("Livro cadastrado com sucesso!");
			
		} catch (IllegalArgumentException e) {
			System.err.println("ERRO: " + e.getMessage());
		}
		
		digiteParaContinuar()
	}
	
	private void salvarCadastro() {
		loop: while(true) {
			System.out.println("\nQual cadastro salvar?");			
			System.out.println("1 - Usuarios");
			System.out.println("2 - Livros");
			System.out.println("3 - Ambos");
			System.out.println("4 - Voltar");
			
			int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
			
			switch (opcao) {
				case 1:
					salvarUsuarios();
					break loop;
					
				case 2:
					salvarLivros();
					break loop;
					
				case 3:
					salvarUsuarios();
					salvarLivros();
					break loop;
					
				case 4:
					break loop;
				
				default:
					System.out.println("Insira um numero de 1 a 6!");
			}
		}
	}

	private void salvarUsuarios() {
		loop: while (true) {
			if (arqDefinido(nomeArqUsuarios)) {
				System.out.println("\nOnde salvar os dados de usuarios?");
				System.out.println(String.format("1 - Salvar em %s", nomeArqUsuarios));
				System.out.println("2 - Escolher outro arquivo");
				System.out.println("3 - Cancelar");
			
				int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
				
				switch (opcao) {
					case 1:
						salvarDadosUsu(nomeArqUsuarios);
						break loop;
						
					case 2:
						salvarDadosUsu(lerLinha("Digite o nome do arquivo para salvar os cadastros de usuarios: "));
						break loop;
						
					case 3:
						break loop;
					
					default:
						System.out.println("Insira um numero de 1 a 3!");
				}
			} else {
				salvarDadosUsu(lerLinha("Digite o nome do arquivo para salvar os cadastros de usuarios: "));
				break loop;
			}
		}
	}
	
	private void salvarLivros() {
		loop: while (true) {
			if (arqDefinido(nomeArqLivros)) {
				System.out.println("\nOnde salvar os dados de livros?");
				System.out.println(String.format("1 - Salvar em %s", nomeArqLivros));
				System.out.println("2 - Escolher outro arquivo");
				System.out.println("3 - Cancelar");
			
				int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
				
				switch (opcao) {
					case 1:
						salvarDadosLiv(nomeArqLivros);
						break loop;
						
					case 2:
						salvarDadosLiv(lerLinha("Digite o nome do arquivo para salvar os cadastros de livros: "));
						break loop;
						
					case 3:
						break loop;
					
					default:
						System.out.println("Insira um numero de 1 a 3!");
				}
			} else {
				salvarDadosLiv(lerLinha("Digite o nome do arquivo para salvar os cadastros de livros: "));
				break loop;
			}
		}
	}
//	private void emprestimo() {
	
//	}

	private void relatorio() {
		loop: while (true) {	
			System.out.println("\nListar as informacoes de:");
			System.out.println("1 - Usuarios");
			System.out.println("2 - Livros");
			System.out.println("3 - Usuario especifico");
			System.out.println("4 - Livro especifico");
			System.out.println("5 - Voltar");
			
			int opcao = lerInteiro("Digite o numero da opcao correspondente: ");
			
			switch (opcao) {
				case 1:
					System.out.println(biblioteca.imprimeUsuarios());
					break;
				
				case 2:
					System.out.println(biblioteca.imprimeLivros());
					break;
					
				case 3:
					relatorioUsuario();
					break;
					
				case 4:
					relatorioLivro();
					break;
					
				case 5:
					break loop;
					
				default:
					System.out.println("Insira um numero de 1 a 5!");
			}
			
			digiteParaContinuar()
		}
	}

	private void relatorioUsuario() {
		try {
			String CPF = lerLinha("Digite o CPF do usuario: ");
			
			Usuario usuario = biblioteca.getUsuario(ValidaCPF.toLong(CPF));
			System.out.println(usuario.toStringCompleto());
		
		} catch (UsuarioNaoCadastradoEx e) {
			System.err.println("CPF nao encontrado nos cadastros.");
			
		} catch (IllegalArgumentException e) {
			System.err.println("ERRO: " + e.getMessage());
		}
	}
	
	private void relatorioLivro() {
		try {
			int codigo = lerInteiro("Digite o codigo do livro: ");
			
			Livro livro = biblioteca.getLivro(codigo);
			System.out.println(livro.toStringCompleto());
		
		} catch (LivroNaoCadastradoEx e) {
			System.err.println("Codigo do livro nao encontrado nos cadastros.");
			
		}
	}
	
	// FUNCOES DE LEITURA DO TECLADO
	private String lerLinha(String mensagem) {
		try {
			System.out.print(mensagem);
			return teclado.nextLine();
			
		} catch (NoSuchElementException e) {
			System.err.println("\nERRO: Comando de EOF enviado! Encerrando o programa.");
			System.exit(1);
			return "";
		}
	}
	
	private int lerInteiro(String mensagem) {
		while(true) {
			try {
				return Integer.valueOf(lerLinha(mensagem));
				
			} catch (NumberFormatException e) {
				System.err.println("\nERRO: Nao foi inserido um inteiro.");	
			}
		}
	}
	
	private float lerFloat(String mensagem) {
		while(true) {
			try {
				return Float.valueOf(lerLinha(mensagem).replace(",", "."));
					
			} catch (NumberFormatException e) {
				System.err.println("\nERRO: Nao foi inserido um ponto flutuante.");
			}
		}
	}
	
	private void digiteParaContinuar() {
		lerLinha("Digite qualquer coisa para continuar... ");
	}
}

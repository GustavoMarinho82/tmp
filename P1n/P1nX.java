import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.DateTimeException;

public class P1nX {
	// ATRIBUTOS
	private static Scanner teclado;
	
	// METODO PRINCIPAL
	public static void main(String[] args) {
		teclado = new Scanner(System.in);
		
		lerPessoaInicial(args);
		int tamanho = lerTamanho();
		Pessoa[] pessoas = lerPessoas(tamanho);
		imprimirDados(pessoas);
		
		teclado.close();
	}	
	
	// OUTROS METODOS
	private static void lerPessoaInicial(String[] args) {
		try {
			if (args.length != 9)
				throw new IllegalArgumentException("Numero invalido de argumentos! \nO comando deve seguir esse formato: java P1nX <genero> <nome> <sobrenome> <dia de nasc.> <mes de nasc.> <ano de nasc.> <CPF> <peso> <altura>.");
			
			Pessoa pessoaInicial;

			if (args[0].equalsIgnoreCase("m"))
				pessoaInicial = new Homem();
			
			else if (args[0].equalsIgnoreCase("f"))
				pessoaInicial = new Mulher();
			
			else
				throw new IllegalArgumentException("Genero invalido! Valores aceitos: M, m, F e f.");
			
			Pessoa.diminuirNumPessoas(); // Metodo usado para desconsiderar a pessoaInicial na contagem de pessoas
			
			pessoaInicial.setNome(args[1]);
			pessoaInicial.setSobreNome(args[2]);	
			pessoaInicial.setDataNasc(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
			pessoaInicial.setNumCPF(args[6]);
			pessoaInicial.setPeso(Float.parseFloat(args[7].replace(',', '.')));
			pessoaInicial.setAltura(Float.parseFloat(args[8].replace(',', '.')));
			
			System.out.println("=============================================");
			System.out.println("Exibindo os dados da pessoa cadastrada: \n\n" + pessoaInicial);
		
		} catch (NumberFormatException e) {
					System.err.println("ERRO: Tipo do input incompativel com o que foi solicitado!");
					System.err.println("O comando deve seguir esse formato: java P1nX <genero> <nome> <sobrenome> <dia de nasc.> <mes de nasc.> <ano de nasc.> <CPF> <peso> <altura>.");
					
		} catch (IllegalArgumentException e) {
				System.err.println("ERRO: " + e.getMessage());
		}
	}
		
	private static int lerTamanho() {
		int tamanho;
		
		while (true) {
			try {
				System.out.println("=============================================");
				System.out.print("Quantas pessoas a mais deseja cadastrar? ");
				tamanho = teclado.nextInt();
				
				if (tamanho < 0)
					throw new InputMismatchException();
				
				teclado.nextLine();
				return tamanho;
			
			} catch (InputMismatchException e) {
				System.err.println("ERRO: Valor invalido!");
				teclado.nextLine();
			}
		}
	}

	private static void inputCancelavel(String input) {
		// Se o input for um input de cancelamento (apenas um ENTER), joga um erro que indica o cancelamento
		if (input.isEmpty())
			throw new LeituraCanceladaException();
	}
	
	private static Pessoa[] lerPessoas(int tamanho) {
		Pessoa[] pessoas = new Pessoa[tamanho];
		
		for (int i = 0; i < tamanho; i++) {
			System.out.println("=============================================");
			System.out.println("Lendo os dados da pessoa n° " + (i + 1) + ": \n");
			
			for (int j = 0; j < 7; ) {
				try {
					switch (j) {
						case 0:
							System.out.print("Digite o genero: ");
							String genero = teclado.nextLine();
							
							if (genero.isEmpty())
								throw new LeituraCanceladaException("Pessoa nao criada");
							
							if (genero.equalsIgnoreCase("m"))
								pessoas[i] = new Homem();
								
							else if (genero.equalsIgnoreCase("f"))
								pessoas[i] = new Mulher();
								
							else
								throw new IllegalArgumentException("Genero invalido. Valores aceitos: M, m, F ou f.");
							
							break;
							
						case 1:
							System.out.print("\nDigite o nome: ");
							String nome = teclado.nextLine();
							
							inputCancelavel(nome);
							
							pessoas[i].setNome(nome);
							break;
							
						case 2:
							System.out.print("\nDigite o sobrenome: ");
							String sobreNome = teclado.nextLine();
							
							inputCancelavel(sobreNome);
							
							pessoas[i].setSobreNome(sobreNome);
							break;
							
						case 3:
							System.out.print("\nDigite o dia de nascimento: ");
							String dia = teclado.nextLine();
							
							inputCancelavel(dia);
							int numDia = Integer.parseInt(dia);
							
							System.out.print("\nAgora, o mês: ");
							String mes = teclado.nextLine();
							
							inputCancelavel(mes);
							int numMes = Integer.parseInt(mes);

							System.out.print("\nE o ano: ");
							String ano = teclado.nextLine();
							
							inputCancelavel(ano);
							int numAno = Integer.parseInt(ano);
							
							pessoas[i].setDataNasc(numDia, numMes, numAno);
							break;

						case 4:
							System.out.print("\nDigite o CPF: ");
							String CPF = teclado.nextLine();
							
							inputCancelavel(CPF);
							
							pessoas[i].setNumCPF(CPF);
							break;
						
						case 5:
							System.out.print("\nDigite o peso: ");
							String peso = teclado.nextLine();
							
							inputCancelavel(peso);
							float numPeso = Float.parseFloat(peso.replace(',', '.'));
							
							pessoas[i].setPeso(numPeso);
							break;
						
						case 6:
							System.out.print("\nDigite a altura: ");
							String altura = teclado.nextLine();
							
							inputCancelavel(altura);
							float numAltura = Float.parseFloat(altura.replace(',', '.'));
							
							pessoas[i].setAltura(numAltura);
							break;
					}
						
					j++;
					
				} catch (NumberFormatException e) {
					System.err.println("ERRO: Tipo do input incompativel com o que foi solicitado!");
					
				} catch (IllegalArgumentException e) {
					System.err.println("ERRO: " + e.getMessage());
				
				} catch (LeituraCanceladaException e) {
					System.out.println("Leitura cancelada!");
						
					i = tamanho;
					j = 7;
					
					if (!(e.getMessage().equals("Pessoa nao criada")))
						Pessoa.diminuirNumPessoas();
				}
			}
		}
		
		return pessoas;
	}
		
	private static void imprimirDados(Pessoa[] pessoas) {
		if (Pessoa.numPessoas() > 0) {
			System.out.println("\n=============================================");
			System.out.println("Exibindo os dados das pessoas cadastradas: \n");
			
			int qtd_homens = 0;
			int qtd_mulheres = 0;
			
			for (int i = 0; i < Pessoa.numPessoas(); i++) {
				System.out.println(pessoas[i]);
				
				if (pessoas[i] instanceof Homem)
					qtd_homens++;
					
				else
					qtd_mulheres++;
			}
			
			System.out.println("=============================================");
			System.out.println("Quantidade de pessoas cadastradas: " + Pessoa.numPessoas());
			System.out.println("Quantidade de homens cadastrados: " + qtd_homens);
			System.out.println("Quantidade de mulheres cadastradas: " + qtd_mulheres);
			System.out.println("(Desconsiderando a pessoa cadastrada via linha de comando)");
		}
	}
}

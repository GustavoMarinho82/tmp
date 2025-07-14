package controle;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.Properties;

import entidade.Biblioteca;
import entidade.excecao.*;
import gui.*;
import gui.navegacao.Telas;

public class Controlador implements ActionListener {
	// ATRIBUTOS
	private JanelaPrincipal janela;
	private Biblioteca biblioteca;
	
	private File arqUsuarios;
	private File arqLivros;
	private JFileChooser fc = new JFileChooser(".");
	
	private int maxLivrosEmprestados;
	private int periodoDevolucao;
	
	// CONSTRUTOR
	public Controlador(JanelaPrincipal janela) {
		this.janela = janela;
	}
	
	// METODOS
	@Override
	public void actionPerformed(ActionEvent event) {
		String comando = event.getActionCommand();

		switch (comando) {
			case "SELECIONAR ARQ USUARIOS":
				selecionarArq(true);
				break;
				
			case "SELECIONAR ARQ LIVROS":
				selecionarArq(false);
				break;
				
			case "INICIAR BIBLIOTECA":
				iniciarBiblioteca();
				break;
				
			case "SALVAR CADASTROS":
				salvarCadastros();
				break;
			
			case "CARREGAR CADASTROS":
				carregarCadastros();
				break;
			
			case "SELECIONAR ARQUIVOS":
				selecionarArqs();
				break;
				
			default: 
				janela.trocarPainel(comando);
		}
	}
	
	private File escolherArq() {
		int resultado = fc.showOpenDialog(janela);
		
		return (resultado == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null;
	}
	
	private File salvarArq() {
		int resultado = fc.showSaveDialog(janela);
		
		return (resultado == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null;
	}
	
	private void selecionarArq(boolean deUsuarios) {
		File arq = escolherArq();
		
		if (deUsuarios) {
			arqUsuarios = arq;
			janela.getPainelInicial().alterarTextoArqUsuarios((arq != null) ? arq.getName() : "Nao definido");
			janela.getPainelManutencao().alterarTextoArqUsuarios((arq != null) ? arq.getName() : "Nao definido");
			
		} else {
			arqLivros = arq;
			janela.getPainelInicial().alterarTextoArqLivros((arq != null) ? arq.getName() : "Nao definido");
			janela.getPainelManutencao().alterarTextoArqLivros((arq != null) ? arq.getName() : "Nao definido");
		}
	}
	
	private void iniciarBiblioteca() {
		biblioteca = new Biblioteca();
		PainelInicial painelInicial = janela.getPainelInicial();
		
		if (painelInicial.ativadaPolPersonalizada()) {
			carregarPolitica();
		}
		
		if (arqUsuarios != null) {
			carregarArq(true);
		}
		
		if (arqLivros != null) {
			carregarArq(false);
		}
		
		janela.trocarPainel(Telas.PRINCIPAL.toString());
	}
	
	private void carregarPolitica() {
		Properties politica = new Properties();
		
		try (FileInputStream fis = new FileInputStream("config.properties")) {
			politica.load(fis);
			
			int maxLivTemp = Integer.valueOf(politica.getProperty("max.livros.emprestados"));
			int periodoTemp = Integer.valueOf(politica.getProperty("periodo.devolucao"));
			
			maxLivrosEmprestados = maxLivTemp;
			periodoDevolucao = periodoTemp;
				
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo config.properties nao foi encontrado ou nao pode ser lido. \nSeguindo sem a politica personalizada.");
		
		} catch (IOException e) {
			exibirErro("Nao foi possivel concluir a leitura corretamente. \nSeguindo sem a politica personalizada.");
		
		} catch (NumberFormatException e) {
			exibirErro("Nao foi possivel carregar os valores de config.properties. \nSeguindo sem a politica personalizada.");
		}
	}
	
	private void carregarArq(boolean deUsuarios) {
		String complementoErro = (deUsuarios) ? "usuarios" : "livros";
		
		try {
			if (deUsuarios) {
				biblioteca.leArqUsu(arqUsuarios.getAbsolutePath());
			
			} else {
				biblioteca.leArqUsu(arqUsuarios.getAbsolutePath());
			}
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de " + complementoErro + " nao foi encontrado ou nao pode ser lido.");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo de " + complementoErro + ". \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo de " + complementoErro + " informado nao contem o cadastro de " + complementoErro + ".");
		}
	}
	
	private void salvarCadastros() {
		Object[] opcoes = {"Usuarios", "Livros", "Ambos"};
		int escolha = exibirDialogo("Salvar", "Salvar os cadastros de: ", opcoes);

		switch (escolha) {
			case 0:
				salvarArqUsuarios();
				break;
				
			case 1:
				salvarArqLivros();
				break;
			
			case 2:
				salvarArqUsuarios();
				salvarArqLivros();
				break;
		}
	}
	
	private void salvarArqUsuarios() {
		try {
			if (arqUsuarios == null) {
				File arq = salvarArq();
				
				if (arq != null) {
					biblioteca.salvaArqUsu(arq.getAbsolutePath());
				}
				
			} else {
				Object[] opcoes = {arqUsuarios.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Salvar", "Salvar os dados de usuarios em: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.salvaArqUsu(arqUsuarios.getAbsolutePath());
						break;
					
					case 1:
						File arq = salvarArq();
						
						if (arq != null) {
							biblioteca.salvaArqUsu(arq.getAbsolutePath());
						}
						break;
				}
			}
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de salvamento nao foi encontrado ou nao pode ser escrito!");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu. \nMais informacoes: " + e.getMessage());
		}
	}

	private void salvarArqLivros() {
		try {
			if (arqLivros == null) {
				File arq = salvarArq();
				
				if (arq != null) {
					biblioteca.salvaArqLiv(arq.getAbsolutePath());
				}
				
			} else {
				Object[] opcoes = {arqUsuarios.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Salvar", "Salvar os dados de livros em: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.salvaArqLiv(arqLivros.getAbsolutePath());
						break;
					
					case 1:
						File arq = salvarArq();
						
						if (arq != null) {
							biblioteca.salvaArqLiv(arq.getAbsolutePath());
						}
						break;
				}
			}
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de salvamento nao foi encontrado ou nao pode ser escrito!");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu. \nMais informacoes: " + e.getMessage());
		}
	}
	
	private void carregarCadastros() {
		Object[] opcoes = {"Usuarios", "Livros", "Ambos"};
		int escolha = exibirDialogo("Carregar", "Carregar os cadastros de: ", opcoes);

		switch (escolha) {
			case 0:
				carregarArqUsuarios();
				break;
				
			case 1:
				carregarArqLivros();
				break;
			
			case 2:
				carregarArqUsuarios();
				carregarArqLivros();
				break;
		}
	}
	
	private void carregarArqUsuarios() {
		try {
			if (arqUsuarios == null) {
				File arq = escolherArq();
				
				if (arq != null) {
					biblioteca.leArqUsu(arq.getAbsolutePath());
				}
				
			} else {
				Object[] opcoes = {arqUsuarios.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Carregar", "Carregar os dados de usuarios em: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.leArqUsu(arqUsuarios.getAbsolutePath());
						break;
					
					case 1:
						File arq = escolherArq();
						
						if (arq != null) {
							biblioteca.leArqUsu(arq.getAbsolutePath());
						}
						break;
				}
			}
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo nao foi encontrado ou nao pode ser lido.");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo. \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo informado nao contem o cadastro de usuarios.");
		}
	}

	private void carregarArqLivros() {
		try {
			if (arqLivros == null) {
				File arq = escolherArq();
				
				if (arq != null) {
					biblioteca.leArqLiv(arq.getAbsolutePath());
				}
				
			} else {
				Object[] opcoes = {arqUsuarios.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Carregar", "Carregar os dados de livros em: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.leArqLiv(arqLivros.getAbsolutePath());
						break;
					
					case 1:
						File arq = escolherArq();
						
						if (arq != null) {
							biblioteca.leArqLiv(arq.getAbsolutePath());
						}
						break;
				}
			}
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo nao foi encontrado ou nao pode ser lido.");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo. \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo informado nao contem o cadastro de livros.");
		}
	}
	
	private void selecionarArqs() {
		Object[] opcoes = {"Usuarios", "Livros"};
		int escolha = exibirDialogo("Selecionar", "Selecionar o arquivo de: ", opcoes);
		
		switch (escolha) {
			case 0:
				selecionarArq(true);
				break;
				
			case 1:
				selecionarArq(false);
				break;
		}
	}
	
	private int exibirDialogo(String titulo, String mensagem, Object[] opcoes) {
		return JOptionPane.showOptionDialog(janela, mensagem, titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
	}
	
	private void exibirErro(String mensagem) {
		JOptionPane.showMessageDialog(janela, mensagem, "ERRO", JOptionPane.ERROR_MESSAGE); 
	}
}

package controle;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.time.format.DateTimeParseException;
import java.util.Properties;

import entidade.*;
import entidade.validacao.*;
import entidade.excecao.*;
import gui.*;
import gui.tabela.*;
import gui.navegacao.Tela;

public class Controlador implements ActionListener {
	// ATRIBUTOS
	private JanelaPrincipal janela;
	private ModeloTabelaUsuarios modeloTabelaUsuarios;
	private ModeloTabelaLivros modeloTabelaLivros;
	
	private Biblioteca biblioteca;
	
	private File arqUsuarios;
	private File arqLivros;
	private JFileChooser fc = new JFileChooser(".");
	
	private int maxLivrosEmprestados;
	private int periodoDevolucao;
	
	// CONSTRUTOR
	public Controlador(JanelaPrincipal janela, ModeloTabelaUsuarios modeloTabelaUsuarios, ModeloTabelaLivros modeloTabelaLivros) {
		this.janela = janela;
		this.modeloTabelaUsuarios = modeloTabelaUsuarios;
		this.modeloTabelaLivros = modeloTabelaLivros;
	}
	
	// METODOS
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();

		if (comando.startsWith("MOSTRAR HISTORICO USUARIO - ")) {
			String[] divisao = comando.split(" - ");
			int linha = Integer.valueOf(divisao[1]);
			Usuario usuario = modeloTabelaUsuarios.getUsuario(linha);

			JOptionPane.showMessageDialog(janela, "Historico: " + usuario.getHistFormatado());

		} else if (comando.startsWith("MOSTRAR HISTORICO LIVRO - ")) {
			String[] divisao = comando.split(" - ");
			int linha = Integer.valueOf(divisao[1]);
			Livro livro = modeloTabelaLivros.getLivro(linha);

			JOptionPane.showMessageDialog(janela, "Historico: " + livro.getHistFormatado());

		} else if (Tela.isTela(comando)) {
			Tela tela = Tela.fromString(comando);
			
			if (tela == Tela.RELATORIO) {
				modeloTabelaUsuarios.atualizarTabela(biblioteca.getUsuarios());
				modeloTabelaLivros.atualizarTabela(biblioteca.getLivros());
			}
						
			janela.trocarPainel(tela.toString());
		
		} else {
			Acao acao = Acao.fromString(comando);
			
			switch (acao) {
				case SELECIONAR_ARQ_USUARIOS:
					selecionarArqUsuarios();
					break;
					
				case SELECIONAR_ARQ_LIVROS:
					selecionarArqLivros();
					break;
					
				case INICIAR_BIBLIOTECA:
					iniciarBiblioteca();
					break;
					
				case SALVAR_CADASTROS:
					salvarCadastros();
					break;
				
				case CARREGAR_CADASTROS:
					carregarCadastros();
					break;
				
				case SELECIONAR_ARQUIVOS:
					selecionarArqs();
					break;
					
				case CADASTRAR_USUARIO:
					cadastrarUsuario();
					break;
					
				case CADASTRAR_LIVRO:
					cadastrarLivro();
					break;
					
				case FAZER_EMPRESTIMO:
					fazerEmprestimo();
					break;
					
				case FAZER_DEVOLUCAO:
					fazerDevolucao();
					break;
					
				default:
					exibirErro("Nao foi possivel realizar a acao!");
			}
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
	
	private void selecionarArqUsuarios() {
		File arq = escolherArq();
		
		arqUsuarios = arq;
		janela.getPainelInicial().alterarTextoArqUsuarios((arq != null) ? arq.getName() : "Nao definido");
		janela.getPainelManutencao().alterarTextoArqUsuarios((arq != null) ? arq.getName() : "Nao definido");
	}
	
	private void selecionarArqLivros() {
		File arq = escolherArq();

		arqLivros = arq;
		janela.getPainelInicial().alterarTextoArqLivros((arq != null) ? arq.getName() : "Nao definido");
		janela.getPainelManutencao().alterarTextoArqLivros((arq != null) ? arq.getName() : "Nao definido");
	}
	
	private void iniciarBiblioteca() {
		biblioteca = new Biblioteca();
		PainelInicial painelInicial = janela.getPainelInicial();
		
		if (painelInicial.ativadaPoliticaPersonalizada()) {
			carregarPolitica();
		}
		
		if (arqUsuarios != null) {
			carregarArqUsuariosPainelInicial();
		}
		
		if (arqLivros != null) {
			carregarArqLivrosPainelInicial();
		}
		
		janela.trocarPainel(Tela.PRINCIPAL.toString());
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
	
	private void carregarArqUsuariosPainelInicial() {
		boolean sucesso = false;
		
		try {
			biblioteca.leArqUsu(arqUsuarios.getAbsolutePath());
			sucesso = true;
			
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de usuarios nao foi encontrado ou nao pode ser lido.");
			
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo de usuarios. \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo de usuarios informado nao contem o cadastro de usuarios.");
		}
		
		if (!sucesso) {
			arqUsuarios = null;
			janela.getPainelInicial().alterarTextoArqUsuarios("Nao definido");
			janela.getPainelManutencao().alterarTextoArqUsuarios("Nao definido");
		}
	}
	
	private void carregarArqLivrosPainelInicial() {
		boolean sucesso = false;
		
		try {
			biblioteca.leArqLiv(arqLivros.getAbsolutePath());
			sucesso = true;
			
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de livros nao foi encontrado ou nao pode ser lido.");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo de livros. \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo de livros informado nao contem o cadastro de livros.");
		}
		
		if (!sucesso) {
			arqLivros = null;
			janela.getPainelInicial().alterarTextoArqLivros("Nao definido");
			janela.getPainelManutencao().alterarTextoArqLivros("Nao definido");
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
					exibirSucesso("Arquivo de usuarios salvo com sucesso!");
				}
				
			} else {
				Object[] opcoes = {arqUsuarios.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Salvar", "Salvar os dados de usuarios em: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.salvaArqUsu(arqUsuarios.getAbsolutePath());
						exibirSucesso("Arquivo de usuarios salvo com sucesso!");
						break;
					
					case 1:
						File arq = salvarArq();
						
						if (arq != null) {
							biblioteca.salvaArqUsu(arq.getAbsolutePath());
							exibirSucesso("Arquivo de usuarios salvo com sucesso!");
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
					exibirSucesso("Arquivo de livros salvo com sucesso!");
				}
				
			} else {
				Object[] opcoes = {arqLivros.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Salvar", "Salvar os dados de livros em: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.salvaArqLiv(arqLivros.getAbsolutePath());
						exibirSucesso("Arquivo de livros salvo com sucesso!");
						break;
					
					case 1:
						File arq = salvarArq();
						
						if (arq != null) {
							biblioteca.salvaArqLiv(arq.getAbsolutePath());
							exibirSucesso("Arquivo de livros salvo com sucesso!");
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
					exibirSucesso("Arquivo de usuarios carregado com sucesso!");
				}
				
			} else {
				Object[] opcoes = {arqUsuarios.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Carregar", "Carregar os dados de usuarios de: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.leArqUsu(arqUsuarios.getAbsolutePath());
						exibirSucesso("Arquivo de usuarios carregado com sucesso!");
						break;
					
					case 1:
						File arq = escolherArq();
						
						if (arq != null) {
							biblioteca.leArqUsu(arq.getAbsolutePath());
							exibirSucesso("Arquivo de usuarios carregado com sucesso!");
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
					exibirSucesso("Arquivo de livros carregado com sucesso!");
				}
				
			} else {
				Object[] opcoes = {arqLivros.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Carregar", "Carregar os dados de livros de: ", opcoes);
			
				switch (escolha) {
					case 0:
						biblioteca.leArqLiv(arqLivros.getAbsolutePath());
						exibirSucesso("Arquivo de livros carregado com sucesso!");
						break;
					
					case 1:
						File arq = escolherArq();
						
						if (arq != null) {
							biblioteca.leArqLiv(arq.getAbsolutePath());
							exibirSucesso("Arquivo de livros carregado com sucesso!");
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
				selecionarArqUsuarios();
				break;
				
			case 1:
				selecionarArqLivros();
				break;
		}
	}
	
	private void cadastrarUsuario() {
		PainelCadastroUsuario pcu = janela.getPainelCadastroUsuario();
		
		try {
			biblioteca.cadastraUsuario(new Usuario(
				pcu.getNome(), 
				pcu.getSobrenome(), 
				ValidaData.toLocalDate(pcu.getDataNasc()),
				pcu.getCPF(),
				pcu.getPeso(),
				pcu.getAltura(),
				pcu.getEndereco()
			));
			
			pcu.resetarCampos();
			exibirSucesso("Usuario cadastrado com sucesso!");
			
		} catch (IllegalArgumentException e) {
			exibirErro(e.getMessage());
		
		} catch (DateTimeParseException e) {
			exibirErro("Data de nascimento invalida!");
		}
	}
	
	private void cadastrarLivro() {
		PainelCadastroLivro pcl = janela.getPainelCadastroLivro();
		
		try {
			biblioteca.cadastraLivro(new Livro(
				pcl.getCodigo(), 
				pcl.getTitulo(), 
				pcl.getCategoria(),
				pcl.getDisponiveis()
			));
			
			pcl.resetarCampos();
			exibirSucesso("Livro cadastrado com sucesso!");
			
		} catch (IllegalArgumentException e) {
			exibirErro(e.getMessage());
		}
	}
	
	private void fazerEmprestimo() {
		PainelEmprestimoDevolucao ped = janela.getPainelEmprestimoDevolucao();
		
		try {
			Usuario usuario = biblioteca.getUsuario(ValidaCPF.toLong(ped.getCPF()));
			
			if (maxLivrosEmprestados > 0 && usuario.getNumLivrosEmprestados() >= maxLivrosEmprestados) {
				exibirErro("O usuario ja atingiu o limite de livros emprestados! Ele precisa devolver algum livro antes de fazer um outro emprestimo.");
			
			} else if (periodoDevolucao > 0 && usuario.possuiLivroAtraso(periodoDevolucao)) {
				exibirErro("O usuario ultrapassou o periodo de devolucao de um livro! Ele precisa devolver esse livro antes de fazer um outro emprestimo.");
				
			} else {
				Livro livro = biblioteca.getLivro(ped.getCodigo());
				
				biblioteca.emprestaLivro(usuario, livro);
				
				ped.resetarCampos();
				exibirSucesso("Emprestimo realizado com sucesso!");
			}
		} catch (IllegalArgumentException e) {
			exibirErro(e.getMessage());
		
		} catch (UsuarioNaoCadastradoEx e) {
			exibirErro("CPF nao encontrado nos cadastros de usuarios!");
		
		} catch (LivroNaoCadastradoEx e) {
			exibirErro("Codigo nao encontrado nos cadastros de livros!");
		
		} catch (CopiaNaoDisponivelEx e) {
			exibirErro("Nenhuma copia desse livro esta disponivel para emprestimo!");
		}
	}
	
	private void fazerDevolucao() {
		PainelEmprestimoDevolucao ped = janela.getPainelEmprestimoDevolucao();
		
		try {
			Usuario usuario = biblioteca.getUsuario(ValidaCPF.toLong(ped.getCPF()));
			Livro livro = biblioteca.getLivro(ped.getCodigo());
			
			boolean atraso = (periodoDevolucao > 0 && usuario.possuiLivroAtraso(ped.getCodigo(), periodoDevolucao));
			
			biblioteca.devolveLivro(usuario, livro);
			
			ped.resetarCampos();
			exibirSucesso("Devolucao realizada com sucesso!" + ((atraso) ? " Mas, o usuario devolveu o livro com atraso e devera pagar uma multa!" : ""));
			
		} catch (IllegalArgumentException e) {
			exibirErro(e.getMessage());
		
		} catch (UsuarioNaoCadastradoEx e) {
			exibirErro("CPF nao encontrado nos cadastros de usuarios!");
		
		} catch (LivroNaoCadastradoEx e) {
			exibirErro("Codigo nao encontrado nos cadastros de livros!");
		
		} catch (NenhumaCopiaEmprestadaEx e) {
			exibirErro("Nenhuma copia desse livro foi emprestada para esse usuario!");
		}
	}

	private int exibirDialogo(String titulo, String mensagem, Object[] opcoes) {
		return JOptionPane.showOptionDialog(janela, mensagem, titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
	}
	
	public void exibirSucesso(String mensagem) {
		JOptionPane.showMessageDialog(janela, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
    
	private void exibirErro(String mensagem) {
		JOptionPane.showMessageDialog(janela, mensagem, "ERRO", JOptionPane.ERROR_MESSAGE); 
	}
}

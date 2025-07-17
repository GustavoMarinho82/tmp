package controle;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.time.format.DateTimeParseException;
import java.util.Properties;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.awt.GridLayout;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import entidade.*;
import entidade.validacao.*;
import entidade.excecao.*;
import gui.*;
import gui.tabela.*;
import gui.navegacao.Telas;

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

		} else {
			switch (comando) {
				case "SELECIONAR ARQ USUARIOS":
					selecionarArqUsuarios();
					break;
					
				case "SELECIONAR ARQ LIVROS":
					selecionarArqLivros();
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
					
				case "CADASTRAR USUARIO":
					cadastrarUsuario();
					break;
					
				case "CADASTRAR LIVRO":
					cadastrarLivro();
					break;
				
				case "FAZER EMPRESTIMO/DEVOLUCAO":
					fazerEmprestimoDevolucao();
					break;
					
				default:
					if (Telas.isTela(comando)) {
						if (comando.equalsIgnoreCase(Telas.RELATORIO.toString())) {
							modeloTabelaUsuarios.atualizarTabela(biblioteca.getUsuarios());
							modeloTabelaLivros.atualizarTabela(biblioteca.getLivros());
						}
						
						janela.trocarPainel(comando);
					}
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
			carregarArqUsuariosDireto();
		}
		
		if (arqLivros != null) {
			carregarArqLivrosDireto();
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
	
	private void carregarArqUsuariosDireto() {
		boolean erro = true;
		
		try {
			biblioteca.leArqUsu(arqUsuarios.getAbsolutePath());
			erro = false;
			
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de usuarios nao foi encontrado ou nao pode ser lido.");
			
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo de usuarios. \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo de usuarios informado nao contem o cadastro de usuarios.");
		}
		
		if (erro) {
			arqUsuarios = null;
			janela.getPainelInicial().alterarTextoArqUsuarios("Nao definido");
			janela.getPainelManutencao().alterarTextoArqUsuarios("Nao definido");
		}
	}
	
	private void carregarArqLivrosDireto() {
		boolean erro = true;
		
		try {
			biblioteca.leArqLiv(arqLivros.getAbsolutePath());
			erro = false;
			
		} catch (FileNotFoundException e) {
			exibirErro("O arquivo de livros nao foi encontrado ou nao pode ser lido.");
		
		} catch (IOException e) {
			exibirErro("Um problema de E/S ocorreu ao tentar ler o arquivo de livros. \nMais informacoes: " + e.getMessage());

		} catch (ClassNotFoundException | ArquivoSerialInvalidoEx e) {
			exibirErro("O arquivo de livros informado nao contem o cadastro de livros.");
		}
		
		if (erro) {
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
				Object[] opcoes = {arqLivros.getName(), "Escolher outro arquivo"};
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
				Object[] opcoes = {arqLivros.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Carregar", "Carregar os dados de usuarios de: ", opcoes);
			
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
				Object[] opcoes = {arqLivros.getName(), "Escolher outro arquivo"};
				int escolha = exibirDialogo("Carregar", "Carregar os dados de livros de: ", opcoes);
			
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
				selecionarArqUsuarios();
				break;
				
			case 1:
				selecionarArqLivros();
				break;
		}
	}
	
	private void cadastrarUsuario() {
		PainelCadUsuario pcu = janela.getPainelCadUsuario();
		
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
		PainelCadLivro pcl = janela.getPainelCadLivro();
		
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
/////////////////////////////////////////////////////////////////////////////// TABS	
	private void fazerEmprestimoDevolucao() {
        // --- 1. CRIE O PAINEL COM OS CAMPOS DE ENTRADA ---
        
        // Campo para o CPF com máscara
        JFormattedTextField campoCpf = new JFormattedTextField();
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            campoCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCpf));
        } catch (ParseException e) {
            // Em uma máscara fixa, este erro é improvável.
            e.printStackTrace();
        }

        // Campo para o código do livro
        JTextField campoCodigoLivro = new JTextField(10);
        
        // Monta o painel do formulário
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 linhas, 2 colunas, 5px de espaçamento
        painel.add(new JLabel("CPF do Usuário:"));
        painel.add(campoCpf);
        painel.add(new JLabel("Código do Livro:"));
        painel.add(campoCodigoLivro);
        
        // --- 2. DEFINA OS BOTÕES CUSTOMIZADOS ---
        String[] opcoes = {"Fazer Empréstimo", "Fazer Devolução", "Cancelar"};

        // --- 3. EXIBA O JOPTIONPANE COM O PAINEL E OS BOTÕES ---
        int resultado = JOptionPane.showOptionDialog(
            janela,                              // Janela pai
            painel,                            // O seu painel customizado como mensagem
            "Operação de Empréstimo/Devolução", // Título da janela
            JOptionPane.DEFAULT_OPTION,        // Tipo de opção (não usado com botões customizados)
            JOptionPane.PLAIN_MESSAGE,         // Tipo de mensagem (sem ícone padrão)
            null,                              // Ícone customizado (nenhum)
            opcoes,                            // O array com os textos dos seus botões
            opcoes[0]                          // Botão padrão (o primeiro)
        );

        // --- 4. TRATE O RESULTADO ---
        if (resultado >= 0 && resultado < 2) { // Ignora o "Cancelar" e o fechar da janela
            String cpf = campoCpf.getText();
            String codigoLivro = campoCodigoLivro.getText();
            
            // Validação simples
            if (cpf.contains("_") || codigoLivro.trim().isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Por favor, preencha ambos os campos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (resultado == 0) { // Botão "Fazer Empréstimo"
                    // Chame a lógica de negócio no seu modelo
                    biblioteca.emprestaLivro(biblioteca.getUsuario(ValidaCPF.toLong(cpf)), biblioteca.getLivro(Integer.valueOf(codigoLivro)));
                    JOptionPane.showMessageDialog(janela, "Empréstimo realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                } else if (resultado == 1) { // Botão "Fazer Devolução"
                    // Chame a lógica de negócio no seu modelo
                    biblioteca.devolveLivro(biblioteca.getUsuario(ValidaCPF.toLong(cpf)), biblioteca.getLivro(Integer.valueOf(codigoLivro)));
                    JOptionPane.showMessageDialog(janela, "Devolução realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) { // Ex: UsuarioNaoEncontradoException, LivroNaoDisponivelException
                // Mostra a mensagem da sua exceção customizada para o usuário
                JOptionPane.showMessageDialog(janela, e.getMessage(), "Erro na Operação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
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

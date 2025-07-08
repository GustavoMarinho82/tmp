package lp2g13.biblioteca;

import java.io.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Biblioteca {
	// ATRIBUTOS
	private Hashtable<Long, Usuario> cadUsuarios;
	private Hashtable<Integer, Livro> cadLivros;

	// CONSTRUTORES
	public Biblioteca() {
		cadUsuarios = new Hashtable<>();
		cadLivros = new Hashtable<>();
	}

	public Biblioteca(String arqUsuarios, String arqLivros) throws FileNotFoundException, IOException, ClassNotFoundException, ArquivoSerialInvalidoEx {
		leArqUsu(arqUsuarios);
		leArqLiv(arqLivros);
	}

	// GETTERS
	public Hashtable<Long, Usuario> getCadUsuarios() {
		return cadUsuarios;
	}

	public Hashtable<Integer, Livro> getCadLivros() {
		return cadLivros;
	}

	public Usuario getUsuario(long CPF) throws UsuarioNaoCadastradoEx {
		Usuario usuario = cadUsuarios.get(CPF);

		if (usuario == null) {
			throw new UsuarioNaoCadastradoEx();
		}

		return usuario;
	}

	public Livro getLivro(int codigo) throws LivroNaoCadastradoEx {
		Livro livro = cadLivros.get(codigo);

		if (livro == null) {
			throw new LivroNaoCadastradoEx();
		}

		return livro;
	}	

	// OUTROS METODOS
	public void cadastraUsuario(Usuario usuario) {
		if (cadUsuarios.containsKey(usuario.getNumCPF())) {
			throw new IllegalArgumentException("CPF ja cadastrado!");
		}

		cadUsuarios.put(usuario.getNumCPF(), usuario);
	}
	
	public void cadastraLivro(Livro livro) {
		if (cadLivros.containsKey(livro.getCodigo())) {
			throw new IllegalArgumentException("Codigo ja cadastrado!");
		}

		cadLivros.put(livro.getCodigo(), livro);
	}

	public void salvaArqUsu(String nomeArq) throws FileNotFoundException, IOException {
		try (
			FileOutputStream fos = new FileOutputStream(nomeArq);
			ObjectOutputStream oos = new ObjectOutputStream(fos)
		) {
			oos.writeObject(cadUsuarios);
		}
	}

	public void salvaArqLiv(String nomeArq) throws FileNotFoundException, IOException {
		try (
			FileOutputStream fos = new FileOutputStream(nomeArq);
			ObjectOutputStream oos = new ObjectOutputStream(fos)
		) {
			oos.writeObject(cadLivros);
		}
	}

	@SuppressWarnings("unchecked") // Evita o alerta sobre o type casting. Caso o casting nao possa ser feito, o codigo propaga um ArquivoSerialInvalidoEx
	public void leArqUsu(String nomeArq) throws FileNotFoundException, IOException, ClassNotFoundException, ArquivoSerialInvalidoEx {
		try (
			FileInputStream fis = new FileInputStream(nomeArq);
			ObjectInputStream ois = new ObjectInputStream(fis)
		) {
			cadUsuarios = (Hashtable<Long, Usuario>) ois.readObject();
		
		} catch (ClassCastException e) {
			throw new ArquivoSerialInvalidoEx();
		}
	}	
	
	@SuppressWarnings("unchecked") // Evita o alerta sobre o type casting. Caso o casting nao possa ser feito, o codigo propaga um ArquivoSerialInvalidoEx
	public void leArqLiv(String nomeArq) throws FileNotFoundException, IOException, ClassNotFoundException, ArquivoSerialInvalidoEx {
		try (
			FileInputStream fis = new FileInputStream(nomeArq);
			ObjectInputStream ois = new ObjectInputStream(fis)
		) {
			cadLivros = (Hashtable<Integer, Livro>) ois.readObject();
		
		} catch (ClassCastException e) {
			throw new ArquivoSerialInvalidoEx();
		}
	}

	public void emprestaLivro(Usuario usuario, Livro livro) throws CopiaNaoDisponivelEx {
		LocalDate agora = LocalDate.now();
		livro.empresta();
		livro.addUsuarioHist(usuario.getNumCPF(), agora, null);
		usuario.addLivroHist(livro.getCodigo(), agora);
	}

	public void devolveLivro(Usuario usuario, Livro livro) throws NenhumaCopiaEmprestadaEx {
		LocalDate agora = LocalDate.now();
		livro.devolve();
		usuario.diminuirNumLivrosEmprestados();

		for (EmprestPara registro: livro.getHist()) {
			if (registro.getCPF() == usuario.getNumCPF() && registro.getDataDevolucao() == null) {
				registro.setDataDevolucao(agora);
				break;
			}
		}

		for (Emprest registro: usuario.getHist()) {
			if (registro.getCodLivro() == livro.getCodigo() && registro.getDataDevolucao() == null) {
				registro.setDataDevolucao(agora);
				break;
			}
		}
	}
	
	public String imprimeUsuarios() {
		StringBuilder usuariosStr = new StringBuilder();
		ArrayList<Long> CPFs = new ArrayList<>(cadUsuarios.keySet());
		Collections.sort(CPFs);

		for (long CPF: CPFs) {
			usuariosStr.append("\n" + cadUsuarios.get(CPF));
		}
		
		return usuariosStr.toString();
	}

	public String imprimeLivros() {
		StringBuilder livrosStr = new StringBuilder();
		ArrayList<Integer> codigos = new ArrayList<>(cadLivros.keySet());
		Collections.sort(codigos);

		for (int codigo: codigos) {
			livrosStr.append("\n" + cadLivros.get(codigo));
		}

		return livrosStr.toString();
	}
}

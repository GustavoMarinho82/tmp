package lp2g13.biblioteca;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	public Biblioteca(String arqUsuarios, String arqLivros) {
		cadUsuarios = null;
		cadLivros = null;
	}

	// METODOS
	public void cadastraUsuario(Usuario usuario) {
		if (cadUsuario.containsKey(usuario.getCPF())) {
			throw new IllegalArgumentException("CPF ja cadastrado!");
		}

		cadUsuarios.put(usuario.getCPF(), usuario);
	}
	
	public void cadastraLivro(Livro livro) {
		if (cadLivro.containsKey(livro.getCodigo())) {
			throw new IllegalArgumentException("Codigo ja cadastrado!");
		}

		cadLivros.put(livro.getCodigo(), livro);
	}

	public void salvaArquivo(Hashtable<K, V> tabCadastro, String nomeArq) {
		fos = new FileOutputStream(nomeArq);
		oos = new ObjectOutputStream(fos);

		oos.writeObject(tabCadastro);
	}

	public Hashtable leArquivo(String nomeArq) {

	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import bancodados.BancoDados;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Livro;

/**
 *
 * @author rodri
 */
public class Principal {
  public static void main(String[] args) {
    BancoDados bd = new BancoDados();
    bd.realizaConexao();
    Livro livro = new Livro(bd.getConexao());
    ResultSet listaLivros;
    try {
      listaLivros = livro.obterListaLivros();
      while(listaLivros.next()){
        System.out.println("Título "+listaLivros.getString("titulo"));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    /*
    livro.setAnoLancamento(2000);
    livro.setIdioma("Português");
    livro.setNumeroPaginas(100);
    livro.setPais("Brasil");
    livro.setResumo("Resumo");
    livro.setTituloLivro("Livro exemplo 3");
    try {
      livro.incluiLivro();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  */
    //Exemplo update
    /*
    livro.setAnoLancamento(2000);
    livro.setIdioma("Português");
    livro.setNumeroPaginas(100);
    livro.setPais("Brasil");
    livro.setResumo("Resumo");
    livro.setTituloLivro("Livro exemplo update");
    livro.setIdLivro(4);
    try {
      livro.atualizaLivro();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
*/
    try{
    livro.deletaLivro(4);
    }
    catch(SQLException ex){
      ex.printStackTrace();
    }
  }
}

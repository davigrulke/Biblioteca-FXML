/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rodri
 */
public class Livro {
    private int idLivro;
    private String tituloLivro;
    private int anoLancamento;
    private int numeroPaginas;
    private String resumo;
    private String pais;
    private String idioma;
    private Connection conexao;
    
    public Livro(Connection conexao){
        this.conexao = conexao;
    }
    public Livro(){
        
    }

    public int getIdLivro() {
        return idLivro;
    }
    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }
    public String getTituloLivro() {
        return tituloLivro;
    }
    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }
    public int getAnoLancamento() {
        return anoLancamento;
    }
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }
    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    public String getResumo() {
        return resumo;
    }
    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public void setConexao(Connection conexao){
        this.conexao = conexao;
    }
    
    public ResultSet obterListaLivros() throws SQLException{
        String sql = "select idLivro, "
                + "titulo, "
                + "anoLancamento,"
                + "numeroPaginas,"
                + "resumo,"
                + "idioma,"
                + "paisOrigem "
                + "from livro";
        PreparedStatement requisicao = this.conexao.prepareStatement(sql);
        return requisicao.executeQuery();  
    }
    public void incluiLivro() throws SQLException{
        String sql = "insert into livro(titulo, "
                + "anoLancamento,"
                + "numeroPaginas,"
                + "resumo,"
                + "idioma,"
                + "paisOrigem"
                + ") values (?, ?, ?, ?, ?, ?)";
        PreparedStatement requisicao = this.conexao.prepareStatement(sql);
        requisicao.setString(1, this.tituloLivro);
        requisicao.setInt(2, this.anoLancamento);
        requisicao.setInt(3, this.numeroPaginas);
        requisicao.setString(4, this.resumo);
        requisicao.setString(5, this.idioma);
        requisicao.setString(6, this.pais);
        requisicao.executeUpdate();
        requisicao.close();
    }
    public void atualizaLivro() throws SQLException{
        String sql = "update livro set titulo=?,"
                + "anoLancamento=?,"
                + "numeroPaginas=?,"
                + "idioma=?,"
                + "resumo=?,"
                + "paisOrigem=? where idLivro = ?";
        PreparedStatement requisicao = this.conexao.prepareStatement(sql);
        requisicao.setString(1, this.tituloLivro);
        requisicao.setInt(2, this.anoLancamento);
        requisicao.setInt(3, this.numeroPaginas);
        requisicao.setString(4, this.idioma);
        requisicao.setString(5, this.resumo);
        requisicao.setString(6, this.pais);
        requisicao.setInt(7, this.idLivro);
        requisicao.executeUpdate();
        requisicao.close();   
    }   
    public void deletaLivro(int id) throws SQLException{
        String sql = "delete from livro where idLivro = ?";
        PreparedStatement requisicao = this.conexao.prepareStatement(sql);
        requisicao.setInt(1, id);
        requisicao.executeUpdate();
        requisicao.close();
        
    }
}
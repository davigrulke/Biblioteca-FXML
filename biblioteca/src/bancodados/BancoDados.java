/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Rodrigo Henrich
 */
public class BancoDados {
    private boolean statusConexao = false;
    private Connection conexao;
    private String mensagemErro;
    private String nomeDriver = "com.mysql.jdbc.Driver";
    /*Dados de acesso ao SGBD*/
    private String nomeServidor = "localhost";
    private String portaServidor = "3306";
    private String nomeUsuario = "acervo";
    private String senha = "acervo123";
    private String nomeBanco = "acervo";
    private String url = "jdbc:mysql://"+this.nomeServidor+":"+this.portaServidor+"/"+this.nomeBanco+"?autoReconnect=true&useSSL=false";

    public boolean getStatusConexao() {
        return statusConexao;
    }

    public Connection getConexao() {
        return conexao;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
    public void realizaConexao(){
        try{
            Class.forName(this.nomeDriver);
            //Cria a conexão
            this.conexao = DriverManager.getConnection(this.url, this.nomeUsuario, this.senha);
            this.statusConexao = true;
        }
        catch(ClassNotFoundException | SQLException ex){
            this.mensagemErro = ex.toString();
            this.statusConexao = false;
        }
    }
    public void encerraConexao(){
        try{
            this.conexao.close();
        }
        catch(SQLException ex){
            this.mensagemErro = "Não foi possível encerrar a conexão "+ex;
        }
    }

}

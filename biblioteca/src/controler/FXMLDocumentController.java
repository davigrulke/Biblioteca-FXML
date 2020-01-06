/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import bancodados.BancoDados;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Livro;


/**
 *
 * @author rodri
 */
public class FXMLDocumentController implements Initializable {


    @FXML
    private TableView<Livro> tbListaLivro;    

    @FXML
    private TableColumn<Livro, Integer> clIdLivro;

    @FXML
    private TableColumn<Livro, String> clTituloLivro;

    @FXML
    private TableColumn<Livro, Integer> clAnoLancamento;

    @FXML
    private TableColumn<Livro, Integer> clNumeroPaginas;

    @FXML
    private TableColumn<Livro, String> clIdioma;

    @FXML
    private TableColumn<Livro, String> clPais;
    
    @FXML
    private Label lblId;
    
    @FXML
    private Button btnNovo;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnDeletar;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtNumeroPaginas;

    @FXML
    private TextField txtAnoLancamento;

    @FXML
    private TextField txtIdioma;

    @FXML
    private TextField txtPaisOrigem;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    
    
    public void mostrarMensagemErro(String titulo, String mensagem, String acao) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.setContentText("Selecione uma linha antes de "+acao+" um registro.");
        alert.showAndWait();
    }
    public void mostraMensagemInformacao(String titulo, String mensagem){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
    
    public boolean mostrarMensagemConfirmacao(String titulo, String mensagem){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> option = alert.showAndWait();

        return option.get() == ButtonType.YES; 
    }
    
    
    public void atualizaTabela(){
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList();
        BancoDados bd = new BancoDados();
        bd.realizaConexao();
        Livro livro = new Livro(bd.getConexao());
        try {
            ResultSet listaResult = livro.obterListaLivros();
            while(listaResult.next()){
                Livro livroAux = new Livro();
                livroAux.setIdLivro(listaResult.getInt("idLivro"));
                livroAux.setTituloLivro(listaResult.getString("titulo"));
                livroAux.setIdioma(listaResult.getString("idioma"));
                livroAux.setAnoLancamento(listaResult.getInt("anoLancamento"));
                livroAux.setNumeroPaginas(listaResult.getInt("numeroPaginas"));
                livroAux.setResumo(listaResult.getString("resumo"));
                livroAux.setPais(listaResult.getString("paisOrigem"));
                listaLivros.add(livroAux);
            }
            bd.encerraConexao();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        tbListaLivro.setItems(listaLivros);
        
    }
    public void ativaCamposEdicao(boolean ativa){
        txtTitulo.setEditable(ativa);
        txtPaisOrigem.setEditable(ativa);
        txtNumeroPaginas.setEditable(ativa);
        txtIdioma.setEditable(ativa);
        txtAnoLancamento.setEditable(ativa);
        btnSalvar.setDisable(!ativa);
        btnCancelar.setDisable(!ativa);
    }
    
    public void ativaBotoesAcao(boolean ativa){
        btnNovo.setDisable(!ativa);
        btnAlterar.setDisable(!ativa);
        btnDeletar.setDisable(!ativa);
    }
    
    public void atualizaCampos(Livro livro){
        lblId.setText(Integer.toString(livro.getIdLivro()));
        txtTitulo.setText(livro.getTituloLivro());
        txtPaisOrigem.setText(livro.getPais());
        txtNumeroPaginas.setText(Integer.toString(livro.getNumeroPaginas()));
        txtIdioma.setText(livro.getIdioma());
        txtAnoLancamento.setText(Integer.toString(livro.getAnoLancamento()));
    }
    
    public void limpaCampos(){
        lblId.setText("");
        txtTitulo.setText("");
        txtPaisOrigem.setText("");
        txtNumeroPaginas.setText("");
        txtIdioma.setText("");
        txtAnoLancamento.setText("");
    }
    
    @FXML
    void cliqueBotaoCancelar(MouseEvent event) {
        limpaCampos();
        ativaBotoesAcao(true);
        ativaCamposEdicao(false);
    }
    
    @FXML
    void cliqueBotaoDeletar(MouseEvent event) {
        if(tbListaLivro.getSelectionModel().getSelectedItem() != null){
            BancoDados bd = new BancoDados();
            bd.realizaConexao();
            Livro livroDeleta = tbListaLivro.getSelectionModel().getSelectedItem();
            livroDeleta.setConexao(bd.getConexao());
            if(mostrarMensagemConfirmacao("Apagar?", "Deseja mesmo apagar o livro "+livroDeleta.getTituloLivro()));
                try{
                    livroDeleta.deletaLivro(livroDeleta.getIdLivro());
                    atualizaTabela();
                }
                catch(SQLException ex){
                    mostrarMensagemErro("Erro ao deletar", "Não foi possivel deletar o registro", "");
                }
            bd.encerraConexao();
        }
        else{
            mostrarMensagemErro("Erro", "Selecione uma linha", "deletar");
        }
    }

    @FXML
    void cliqueBotaoEditar(MouseEvent event) {
        if(tbListaLivro.getSelectionModel().getSelectedItem() != null){
            Livro livroAux = tbListaLivro.getSelectionModel().getSelectedItem();
            atualizaCampos(livroAux);
            ativaCamposEdicao(true);
            ativaBotoesAcao(false);
        }
        else{
            mostrarMensagemErro("Erro", "Selecione uma linha", "alterar");
        }
    }

    @FXML
    void cliqueBotaoNovo(MouseEvent event) {
        limpaCampos();
        ativaCamposEdicao(true);
        ativaBotoesAcao(false);
    }

    @FXML
    void cliqueBotaoSalvar(MouseEvent event) {
        BancoDados bd = new BancoDados();
        bd.realizaConexao();
        Livro livroSalvar = new Livro(bd.getConexao());
        if(lblId.getText().equals("")){
            //Inclusão de novo livro
            livroSalvar.setTituloLivro(txtTitulo.getText());
            livroSalvar.setIdioma(txtIdioma.getText());
            livroSalvar.setAnoLancamento(Integer.parseInt(txtAnoLancamento.getText()));
            livroSalvar.setNumeroPaginas(Integer.parseInt(txtNumeroPaginas.getText()));
            livroSalvar.setPais(txtPaisOrigem.getText());
          try {
            livroSalvar.incluiLivro();
            ativaBotoesAcao(true);
            limpaCampos();
            ativaCamposEdicao(false);
            atualizaTabela();
          } catch (SQLException ex) {
            mostrarMensagemErro("Erro", "Não foi possível incluir o novo livro!","");
            //ex.printStackTrace();
          }
        }
        else{
            //Atualizar um livro já salvo
            livroSalvar.setIdLivro(Integer.parseInt(lblId.getText()));
            livroSalvar.setTituloLivro(txtTitulo.getText());
            livroSalvar.setIdioma(txtIdioma.getText());
            livroSalvar.setAnoLancamento(Integer.parseInt(txtAnoLancamento.getText()));
            livroSalvar.setNumeroPaginas(Integer.parseInt(txtNumeroPaginas.getText()));
            livroSalvar.setPais(txtPaisOrigem.getText());
          try {
            livroSalvar.atualizaLivro();
            ativaBotoesAcao(true);
            limpaCampos();
            ativaCamposEdicao(false);
            atualizaTabela();
          } catch (SQLException ex) {
            mostrarMensagemErro("Erro", "Não foi possível incluir o novo livro!","");
            //ex.printStackTrace();
          }
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregando as imagens dos botões
        Image imagemNovo = new Image(getClass().getResourceAsStream("/imagens/iconeNovo.png"));
        Image imagemAlterar = new Image(getClass().getResourceAsStream("/imagens/iconeAlterar.png"));
        Image imagemDeletar = new Image(getClass().getResourceAsStream("/imagens/iconeDeletar.png"));
        Image imagemSalvar = new Image(getClass().getResourceAsStream("/imagens/iconeSalvar.png"));
        Image imagemCancelar = new Image(getClass().getResourceAsStream("/imagens/iconeCancelar.png"));
        //Setando as imagens carregadas nos botões
        btnNovo.setGraphic(new ImageView(imagemNovo));    
        btnAlterar.setGraphic(new ImageView(imagemAlterar));     
        btnDeletar.setGraphic(new ImageView(imagemDeletar));       
        btnSalvar.setGraphic(new ImageView(imagemSalvar));
        btnCancelar.setGraphic(new ImageView(imagemCancelar));
        //Atribuindo as colunas aos atributos da classe livro
        clIdLivro.setCellValueFactory(new PropertyValueFactory("idLivro"));
        clTituloLivro.setCellValueFactory(new PropertyValueFactory("tituloLivro"));
        clAnoLancamento.setCellValueFactory(new PropertyValueFactory("anoLancamento"));
        clNumeroPaginas.setCellValueFactory(new PropertyValueFactory("numeroPaginas"));
        clIdioma.setCellValueFactory(new PropertyValueFactory("idioma"));
        clPais.setCellValueFactory(new PropertyValueFactory("pais"));
        //Desativando os campos para os registros...
        ativaCamposEdicao(false);
    
        atualizaTabela();
        //mostrarMensagemAlerta("Mensagem", "Selecione uma linha da tabela", "apagar");
        //System.out.println(mostrarMensagemConfirmacao("Titulo", "Mensagem"));
    }    
    
}

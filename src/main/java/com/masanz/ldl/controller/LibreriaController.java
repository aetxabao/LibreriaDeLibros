package com.masanz.ldl.controller;

import java.io.File;
import java.util.List;

import com.masanz.ldl.util.DbUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.masanz.ldl.dao.LibreriaDao;
import com.masanz.ldl.model.Libro;

import javafx.stage.FileChooser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LibreriaController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtAnyo;

    @FXML
    private TextField txtPaginas;

    @FXML
    private TextField txtAutorSearch;

    @FXML
    private TableView<Libro> tvwLibros;

    @FXML
    private TableColumn<Libro, Integer> colId;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, Integer> colAnyo;

    @FXML
    private TableColumn<Libro, Integer> colPaginas;

    private LibreriaDao libreriaDao;

    private static Logger log = Logger.getLogger(LibreriaController.class);
    public LibreriaController() {
        libreriaDao = new LibreriaDao();
    }

    @FXML
    public void initialize() {
        boolean b = true;
        if (!libreriaDao.testConnection()){
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            String path = selectedFile.getAbsolutePath();
            libreriaDao.setupConnection(path);
            if (!libreriaDao.testConnection()){
                b = false;
                avisarProblema("No se puede conectar a la base de datos indicada.\n" +
                        path + "\n" +
                        "El programa no puede funcionar sin base de datos.");
            }
        }
        if (b) {
            mostrarLibros();
        }
    }

    @FXML
    private void insert() {
        if (!verificarCampos()) return;
        Libro libro = new Libro(Integer.parseInt(txtId.getText()), txtTitulo.getText(), txtAutor.getText(),Integer.parseInt(txtAnyo.getText()),Integer.parseInt(txtPaginas.getText()));
        if (!libreriaDao.insertar(libro)){
            avisarProblema("Error en la inserción, verifica que el id no existe.");
        }

        mostrarLibros();
    }

    @FXML
    private void update() {
        if (!verificarCampos()) return;
        Libro libro = new Libro(Integer.parseInt(txtId.getText()), txtTitulo.getText(), txtAutor.getText(),Integer.parseInt(txtAnyo.getText()),Integer.parseInt(txtPaginas.getText()));
        if (!libreriaDao.actualizar(libro)){
            avisarProblema("Error en la actualización, verifica que el id existe.");
        }
        mostrarLibros();
    }

    @FXML
    private void delete() {
        if (!verificarId()) return;
        int id = Integer.parseInt(txtId.getText());
        if (!libreriaDao.borrar(id)){
            avisarProblema("No se ha borrado, verifica que el id existe.");
        }
        mostrarLibros();
    }

    public void mostrarLibros(List<Libro> lista) {
        ObservableList<Libro> list = FXCollections.observableArrayList(lista);

        colId.setCellValueFactory(new PropertyValueFactory<Libro,Integer>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro,String>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro,String>("autor"));
        colAnyo.setCellValueFactory(new PropertyValueFactory<Libro,Integer>("anyo"));
        colPaginas.setCellValueFactory(new PropertyValueFactory<Libro,Integer>("paginas"));

        tvwLibros.setItems(list);
    }

    public void mostrarLibros() {
        List<Libro> lista = libreriaDao.listaLibros();
        mostrarLibros(lista);
    }

    private boolean verificarId(){
        try {
            int id = Integer.parseInt(txtId.getText());
        }catch (NumberFormatException e){
            avisarProblema("El id debe ser numérico");
            return false;
        }
        return true;
    }

    private boolean verificarCampos(){
        int id, anyo, paginas;
        try {
            id = Integer.parseInt(txtId.getText());
        }catch (NumberFormatException e){
            avisarProblema("El id debe ser numérico");
            return false;
        }
        try {
            anyo = Integer.parseInt(txtAnyo.getText());
        }catch (NumberFormatException e){
            avisarProblema("El año debe ser numérico");
            return false;
        }
        try {
            paginas = Integer.parseInt(txtAnyo.getText());
        }catch (NumberFormatException e){
            avisarProblema("Las páginas deben ser numéricas");
            return false;
        }
        return true;
    }

    private void avisarProblema(String msg) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Se ha detectado un problema.");
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }

    @FXML
    void selectRow(MouseEvent event) {
        if (event.getClickCount() == 2) {
            txtId.setText(String.valueOf(tvwLibros.getSelectionModel().getSelectedItem().getId()));
            txtTitulo.setText(tvwLibros.getSelectionModel().getSelectedItem().getTitulo());
            txtAutor.setText(tvwLibros.getSelectionModel().getSelectedItem().getAutor());
            txtAnyo.setText(String.valueOf(tvwLibros.getSelectionModel().getSelectedItem().getAnyo()));
            txtPaginas.setText(String.valueOf(tvwLibros.getSelectionModel().getSelectedItem().getPaginas()));
        }
    }

    public void buscarAutor(ActionEvent actionEvent) {
        String autor = txtAutorSearch.getText();
        List<Libro> lista = libreriaDao.listaLibrosAutor(autor);
        mostrarLibros(lista);
    }

    public void resetear(ActionEvent actionEvent) {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnyo.setText("");
        txtPaginas.setText("");
        txtAutorSearch.setText("");
        mostrarLibros();
    }

}

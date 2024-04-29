package org.rplbo.nilaimhs.nilaimahasiswa;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;

public class NewWindowController {
    @FXML private Button btnClose;
    @FXML private TableView<Mahasiswa> tblView;
    @FXML private TableColumn<Mahasiswa,String> colNIM;
    @FXML private TableColumn<Mahasiswa,String> colNama;
    @FXML private TableColumn<Mahasiswa,Double> colIPK;

    @FXML private TextField txtNim;
    @FXML private TextField txtNama;
    @FXML private TextField txtNilai;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    private User user;
    @FXML Label lblWelcome;

    public ObservableList<Mahasiswa> getMahasiswa(){
        ObservableList<Mahasiswa> mhss = FXCollections.observableArrayList();
        mhss.add(new Mahasiswa("1","A",10));
        mhss.add(new Mahasiswa("2","B",20));
        mhss.add(new Mahasiswa("3","C",100));
        mhss.add(new Mahasiswa("4","D",40));
        mhss.add(new Mahasiswa("5","E",90));
        return mhss;
    }

    @FXML
    protected void onBtnCloseClick(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onBtnShowClick() throws SQLException, ClassNotFoundException {
        //tblView = new TableView<Mahasiswa>();
        tblView.setEditable(false);
        colNIM.setCellValueFactory(new PropertyValueFactory<Mahasiswa,String>("nim"));
        colNama.setCellValueFactory(new PropertyValueFactory<Mahasiswa,String>("nama"));
        colIPK.setCellValueFactory(new PropertyValueFactory<Mahasiswa,Double>("nilai"));
        tblView.setItems(getMahasiswa());

    }

    @FXML
    private void addData(){
        String nim, nama;
        double nilai;
        nim = txtNim.getText();
        nama = txtNama.getText();
        nilai = Double.parseDouble(txtNilai.getText());

        Mahasiswa mhs = new Mahasiswa(nim, nama, nilai);
        tblView.getItems().add(mhs);
        txtNim.clear();
        txtNama.clear();
        txtNilai.clear();
    }

    @FXML
    private void deleteData(){
        ObservableList<Mahasiswa> pilih, semua;
        semua = tblView.getItems();
        pilih = tblView.getSelectionModel().getSelectedItems();
        pilih.forEach(semua::remove);
    }

    @FXML
    private void updateData(){
        String nim, nama, nilai;
        ObservableList<Mahasiswa> pilih = tblView.getSelectionModel().getSelectedItems();
        nim = pilih.get(0).getNim();
        nama = pilih.get(0).getNama();
        nilai = String.valueOf(pilih.get(0).getNilai());
        txtNilai.setText(nim);
        txtNama.setText(nama);
        txtNilai.setText(nilai);
        addData();
        deleteData();
        txtNim.requestFocus();
    }

    @FXML
    protected void setUserLogin(User u){
        if(u != null) {
            this.user = u;
            lblWelcome.setText("Welcome, " + this.user.getUsername());
        }else{
            lblWelcome.setText("");
        }
    }
}

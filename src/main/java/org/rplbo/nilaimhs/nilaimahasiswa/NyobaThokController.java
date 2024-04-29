package org.rplbo.nilaimhs.nilaimahasiswa;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class NyobaThokController implements Initializable {
    @FXML private TableColumn c1, c2, c3;
    @FXML private TableView tabel;
    @FXML private TextField nim, nama, nilai;
    private static ObservableList<Mahasiswa> students =  FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        students.add(new Mahasiswa("71210689", "Gian", 90));
        students.add(new Mahasiswa("71210683", "Gian2", 91));
        c1.setCellValueFactory(new PropertyValueFactory<Mahasiswa, String>("nim"));
        c2.setCellValueFactory(new PropertyValueFactory<Mahasiswa, String>("nama"));
        c3.setCellValueFactory(new PropertyValueFactory<Mahasiswa, String>("nilai"));
        tabel.setItems(students);
    }

    @FXML
    public void addMahasiswa() {
        String nimBaru = nim.getText();
        String namaBaru = nama.getText();
        String nilaiBaru = nilai.getText();
        students.add(new Mahasiswa(nimBaru, namaBaru, Double.parseDouble(nilaiBaru)));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("berhasil tambah");
        alert.showAndWait();
    }

    @FXML
    public void delete() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("berhasil edit");
        alert.showAndWait();
        Object ob =  tabel.getSelectionModel().getSelectedItem();
        students.remove(ob);
    }

    @FXML
    public void edit() {
          Mahasiswa chosen = (Mahasiswa) tabel.getSelectionModel().getSelectedItem();
          String nimBaru = nim.getText();
          String namaBaru = nama.getText();
          String nilaiBaru = nilai.getText();
          Mahasiswa mhs = new Mahasiswa(nimBaru, namaBaru, Double.parseDouble(nilaiBaru));
          chosen.setNama(namaBaru);
          chosen.setNim(nimBaru);
          chosen.setNilai(Double.parseDouble(nilaiBaru));
          students.add(mhs);
          students.remove(chosen);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("berhasil edit");
        alert.showAndWait();

    }


}

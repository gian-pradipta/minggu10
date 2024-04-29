package org.rplbo.nilaimhs.nilaimahasiswa;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class NilaiMhsController implements Initializable {
    @FXML
    public TextField searchBox;
    @FXML
    private TextField txtNim;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtNilai;
    @FXML
    private Label lblInfo;
    @FXML
    public TableView<Mahasiswa> table;
    @FXML
    public TableColumn<Mahasiswa, String> nim;
    @FXML
    public TableColumn<Mahasiswa, String> nama;
    @FXML
    public TableColumn<Mahasiswa, Double> nilai;
    private final String DB_URL = "jdbc:sqlite:nilaimahasiswa.db";
    private Connection connection;
//    private ObservableList<Mahasiswa> mahasiswaObservableList;
    private FilteredList<Mahasiswa> mahasiswaFilteredList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        mahasiswaObservableList = FXCollections.observableArrayList();
//        table.setItems(mahasiswaObservableList);

        mahasiswaFilteredList = new FilteredList<>(FXCollections.observableList(FXCollections.observableArrayList()));
        table.setItems(mahasiswaFilteredList);

        nim.setCellValueFactory(new PropertyValueFactory<>("Nim"));
        nama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
        nilai.setCellValueFactory(new PropertyValueFactory<>("Nilai"));
        getConnection();
        createTable();
        getAllData();
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mahasiswa>() {
            @Override
            public void changed(ObservableValue<? extends Mahasiswa> observableValue, Mahasiswa course, Mahasiswa t1) {
                if (observableValue.getValue() != null) {
                    txtNama.setText(observableValue.getValue().getNama());
                    txtNim.setText(observableValue.getValue().getNim());
                    txtNilai.setText("" + observableValue.getValue().getNilai());
                }
            }
        });

        searchBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
//                table.setItems(filteredList(mahasiswaObservableList(), newVal));
                mahasiswaFilteredList.setPredicate(createPredicate(newVal));
            }
        });
    }

    private Predicate<Mahasiswa> createPredicate(String searchText) {
        return new Predicate<Mahasiswa>() {
            @Override
            public boolean test(Mahasiswa mahasiswa) {
                return searchMahasiswa(mahasiswa, searchText);
            }
        };
    }

    private ObservableList<Mahasiswa> mahasiswaObservableList() {
        return (ObservableList<Mahasiswa>) mahasiswaFilteredList.getSource();
    }

    private ObservableList<Mahasiswa> filteredList(List<Mahasiswa> mahasiswaList,
                                                   String searchText) {
        List<Mahasiswa> filteredList = new ArrayList<>();
        for (Mahasiswa mahasiswa : mahasiswaList) {
            if (searchMahasiswa(mahasiswa, searchText)) {
                filteredList.add(mahasiswa);
            }
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchMahasiswa(Mahasiswa mahasiswa, String searchText) {
        return mahasiswa.getNama().toLowerCase().contains(searchText.toLowerCase()) ||
                mahasiswa.getNim().toLowerCase().contains(searchText.toLowerCase());
    }


    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection error
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database connection closure error
            }
        }
    }

    public void createTable() {
        // Create database tables if they don't exist
        // Implement this method to create tables for users, courses, classes, and attendance records
        String mhsTableSql = "CREATE TABLE IF NOT EXISTS mahasiswa ("
                + "nim TEXT PRIMARY KEY,"
                + "nama TEXT NOT NULL,"
                + "nilai REAL NOT NULL"
                + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(mhsTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle table creation error
        }
    }

    private void getAllData() {
        List<Mahasiswa> courses = new ArrayList<>();
        String query = "SELECT * FROM mahasiswa";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nim = resultSet.getString("nim");
                String nama = resultSet.getString("nama");
                double nilai = resultSet.getDouble("nilai");
                Mahasiswa course = new Mahasiswa(nim, nama, nilai);
                courses.add(course);
            }
            mahasiswaObservableList().clear();
            mahasiswaObservableList().addAll(courses);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
    }

    private void bersihkan() {
        txtNim.clear();
        txtNama.clear();
        txtNilai.clear();
        txtNim.requestFocus();
        table.getSelectionModel().select(null);
    }

    private boolean isNilaiMahasiswaUpdated() {
        Mahasiswa selectedMahasiswa = table.getSelectionModel().getSelectedItem();
        if (selectedMahasiswa == null) {
            return false;
        }
        return !selectedMahasiswa.getNim().equalsIgnoreCase(txtNim.getText()) ||
                !selectedMahasiswa.getNama().equalsIgnoreCase(txtNama.getText()) ||
                selectedMahasiswa.getNilai() != Double.parseDouble(txtNilai.getText());
    }


    @FXML
    protected void onBtnAddClick() {
        if (isNilaiMahasiswaUpdated()) {
            if (updateNilaiMahasiswa(table.getSelectionModel().getSelectedItem(), new Mahasiswa(txtNim.getText(), txtNama.getText(), Double.parseDouble(txtNilai.getText())))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nilai Mahasiswa Dirubah!");
                alert.show();
            }
        } else {
            addNilaiMahasiswa(new Mahasiswa(txtNim.getText(), txtNama.getText(), Double.parseDouble(txtNilai.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data Added!");
            alert.show();
        }
        bersihkan();
    }

    @FXML
    protected void onBtnHapusClick() {
        if (deleteNilaiMahasiswa(table.getSelectionModel().getSelectedItem())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nilai Mahasiswa Dihapus!");
            alert.show();
            bersihkan();
        }
    }

    public boolean deleteNilaiMahasiswa(Mahasiswa mahasiswa) {
       /* String query = "DELETE FROM mahasiswa WHERE nim = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mahasiswa.getNim());
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                mahasiswaObservableList.remove(mahasiswa);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;*/

        return mahasiswaObservableList().remove(mahasiswa);
    }

    private boolean updateNilaiMahasiswa(Mahasiswa mahasiswaOld, Mahasiswa mahasiswaNew) {
        int iOldCatatan = mahasiswaObservableList().indexOf(mahasiswaOld);
        mahasiswaObservableList().set(iOldCatatan, mahasiswaNew);
        return true;
    }

    private boolean addNilaiMahasiswa(Mahasiswa mahasiswa) {
        String query = "INSERT INTO mahasiswa (nim, nama, nilai) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mahasiswa.getNim());
            preparedStatement.setString(2, mahasiswa.getNama());
            preparedStatement.setDouble(3, mahasiswa.getNilai());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return mahasiswaObservableList().add(mahasiswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
        return false;
    }


    @FXML
    protected void onBtnCloseClick() {
        Platform.exit();
    }

    private void simpan(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        for (Mahasiswa m : mahasiswaObservableList())
            pw.println(m.toString());
        pw.close();
    }

    @FXML
    protected void onBtnSaveFileClick() throws FileNotFoundException {
        simpan("mahasiswa.txt");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "File Saved!");
        alert.show();
    }

    @FXML
    protected void onOpenBtnClick() throws IOException {
        NilaiMhsApplication.openViewWithModal("new-window", false);
    }

    @FXML
    public void handleBersihkanButton(ActionEvent actionEvent) {
        searchBox.setText("");
        actionEvent.consume();
    }
}
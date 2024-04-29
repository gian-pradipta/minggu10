module org.rplbo.nilaimhs.nilaimahasiswa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.rplbo.nilaimhs.nilaimahasiswa to javafx.fxml;
    exports org.rplbo.nilaimhs.nilaimahasiswa;
}
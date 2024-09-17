module com.example.comp2522202430termprojectal16 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.comp2522202430termprojectal16 to javafx.fxml;
    exports com.example.comp2522202430termprojectal16;
}
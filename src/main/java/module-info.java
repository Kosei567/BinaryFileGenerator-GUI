module org.example.unko {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.example.unko to javafx.fxml;
    exports org.example.unko;
}
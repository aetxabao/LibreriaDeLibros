module com.masanz.ldl {

    requires javafx.controls;
    requires javafx.fxml;

    requires log4j;

    requires java.sql;

    opens com.masanz.ldl to javafx.fxml, javafx.base;
    opens com.masanz.ldl.app to javafx.fxml;
    opens com.masanz.ldl.controller to javafx.fxml;
    opens com.masanz.ldl.model to javafx.base;

    exports com.masanz.ldl;
    exports com.masanz.ldl.app;
    exports com.masanz.ldl.controller;

}
package com.masanz.ldl.app;

import java.io.File;
import java.net.URL;
import java.io.IOException;

import com.masanz.ldl.util.DbUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.masanz.ldl.util.Exec;

public class LibreriaApplication extends Application {

    public static final URL LOG4J_PROPERTIES = LibreriaApplication.class.getResource("/com/masanz/ldl/log/log4j.properties");
    public static final String SYSTEM_PROPERTY = "userApp.userName";
    private static Logger log = Logger.getLogger(LibreriaApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty(SYSTEM_PROPERTY, Exec.getGitUserName());
        PropertyConfigurator.configure(LOG4J_PROPERTIES);
        log.info("-".repeat(80));

        FXMLLoader fxmlLoader = new FXMLLoader(LibreriaApplication.class.getResource("/com/masanz/ldl/view/libreria.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 250);
        stage.setTitle("Librería");
        stage.setScene(scene);

        Image icon16 = new Image(LibreriaApplication.class.getResourceAsStream("/com/masanz/ldl/view/libreria16.png"));
        Image icon32 = new Image(LibreriaApplication.class.getResourceAsStream("/com/masanz/ldl/view/libreria32.png"));
        Image icon64 = new Image(LibreriaApplication.class.getResourceAsStream("/com/masanz/ldl/view/libreria64.png"));
        Image icon128 = new Image(LibreriaApplication.class.getResourceAsStream("/com/masanz/ldl/view/libreria128.png"));
        stage.getIcons().addAll(icon16, icon32, icon64, icon128);

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}
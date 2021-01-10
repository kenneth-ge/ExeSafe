package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sandbox.Sandbox;
import sandbox.UnconfiguredException;
import sandbox.permissions.Permission;

public class Main extends Application {

	public static void main(String[] args) throws IOException {
		//Commands.execute("./sandbox.wsb");
		Main.launch(args);
	}

	HashMap<Permission, JFXCheckBox> permissions = new HashMap<>();
	File executable;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ExeSafe");
        
		VBox titleBox = new VBox(20);
		titleBox.setAlignment(Pos.CENTER);
		
		Text title = new Text("ExeSafe");
		title.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 30));
		title.setFill(Paint.valueOf("#ffffff"));
		titleBox.getChildren().add(title);
		
		Text subtitle = new Text("Select your permissions, open your executable, and try it worry-free!");
		subtitle.setFont(Font.font("Calibri", FontWeight.NORMAL, 24));
		subtitle.setFill(Paint.valueOf("#ffffff"));
		titleBox.getChildren().add(subtitle);
		
		titleBox.setPadding(new Insets(20));
		titleBox.setStyle("-fx-background-color: #0f9d58");
		
		VBox checklist = new VBox(20);
		for(Permission p: Permission.perms) {
			JFXCheckBox box = new JFXCheckBox();
			
			permissions.put(p, box);
			
			if(p.value == Permission.Value.Enabled) {
				box.setSelected(true);
			}
			
			Label text = new Label(p.displayName);
			text.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
			
			box.setText(p.displayName);
			box.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
			
			//box.setPadding(new Insets(0, 50, 0, 50));
			HBox hbox = new HBox();
			hbox.getChildren().addAll(/* text, */box);
			//hbox.setPadding(new Insets(20, 50, 20, 50));
			hbox.setAlignment(Pos.CENTER);
			
			checklist.getChildren().add(hbox);
		}
		checklist.setAlignment(Pos.CENTER);
		
		JFXButton selectFile = new JFXButton("Select File");
		selectFile.setScaleX(1.5);
		selectFile.setScaleY(1.5);
		selectFile.setStyle("-fx-background-color: #0f9d58");
		
		Label l = new Label();
		l.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
		HBox select = new HBox();
		select.setAlignment(Pos.CENTER);
		select.getChildren().addAll(l, selectFile);
		
		checklist.getChildren().add(select);
		
		FileChooser f = new FileChooser();
		
		selectFile.setOnAction((e) -> {
			File f2 = f.showOpenDialog(primaryStage);
			if(f2 != null) {
				executable = f2;
			}
			l.setText(executable.getName());
			TranslateTransition tr = new TranslateTransition(Duration.millis(200), l);
			tr.setByX(-l.getText().length() * 5);
			tr.play();
		});
		
		JFXButton run = new JFXButton("Run Executable");
		run.setScaleX(1.75);
		run.setScaleY(1.75);
		run.setStyle("-fx-background-color: #0f9d58");
		run.setOnAction((e) -> {
			Sandbox s = new Sandbox();
			for(var x: permissions.entrySet()) {
				Permission p = x.getKey();
				if(x.getValue().isSelected()) {
					p.value = Permission.Value.Enabled;
				}else{
					p.value = Permission.Value.Disabled;
				}
				s.permissions.add(p);
			}
			s.executable = executable;
			try {
				s.start();
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("System Error Occurred");
				alert.setHeaderText("System Error Occurred");
				alert.setContentText("Please make sure you have Windows Sandbox enabled");

				alert.showAndWait();
			} catch (UnconfiguredException e1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("No Executable Selected");
				alert.setHeaderText("No Executable Selected");
				alert.setContentText("Please select an executable");

				alert.showAndWait();
				e1.printStackTrace();
			}
		});
		run.setAlignment(Pos.CENTER);
		HBox runBox = new HBox();
		runBox.getChildren().add(run);
		runBox.setAlignment(Pos.CENTER);
		runBox.setPadding(new Insets(50));
		runBox.setStyle("-fx-background-color: #a9a9a9");
		
        BorderPane root = new BorderPane();        
        root.setPadding(new Insets(0, 0, 0, 0));
        root.setTop(titleBox);
        root.setCenter(checklist);
        root.setBottom(runBox);
        primaryStage.setScene(new Scene(root, 800, 800));

        primaryStage.show();
	}
	
}

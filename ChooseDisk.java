package nikmikhailov13;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChooseDisk {

	public static void display() {
		
		Stage window = new Stage();
		window.setTitle("Choosing default disks");
		window.getIcons().add(new Image(FileManager.class.getResourceAsStream("ptencoff.png")));
		
		Label left = new Label("LEFT PANE");
		Label right = new Label("RIGHT PANE");
		Label info = new Label("Press save and restart the");
		Label info1 = new Label("programm.");
		
		// set ComboBox
		ComboBox<String> comboBox1 = new ComboBox<>();
		ComboBox<String> comboBox2 = new ComboBox<>();

		comboBox1.setEditable(true);
		comboBox2.setEditable(true);

		File[] filesList = File.listRoots();

		for (File file : filesList) {
			comboBox1.getItems().add(file.getPath());
			comboBox2.getItems().add(file.getPath());
		}

		comboBox1.setValue(comboBox1.getItems().get(0));
		comboBox2.setValue(comboBox2.getItems().get(0));
		
		// set button
		Button button = new Button("              Save              ");
		Button button1 = new Button("            Cancel             ");
		

		button.setOnAction(e -> {
			try {
				FileWriter writer = new FileWriter("path1.txt");
				BufferedWriter bw = new BufferedWriter(writer);
				bw.write(comboBox1.getValue());
				bw.close();

			} catch (Exception e2) {
			}

			try {
				FileWriter writer = new FileWriter("path2.txt");
				BufferedWriter bw = new BufferedWriter(writer);
				System.out.println(comboBox2.getValue());
				bw.write(comboBox2.getValue());;
				bw.close();

			} catch (Exception e2) {
			}
			window.close();	
		}
		);
		button1.setOnAction(e-> window.close());

		// addListener
		comboBox1.setOnAction(e -> System.out.println("User selected: " + comboBox1.getValue()));

		// set layout
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(15);
		layout.setHgap(5);
		GridPane.setConstraints(comboBox1, 1, 1);
		GridPane.setConstraints(comboBox2, 2, 1);
		GridPane.setConstraints(left, 1, 0);
		GridPane.setConstraints(right, 2, 0);
		GridPane.setConstraints(info1, 2, 2);
		GridPane.setConstraints(info, 1, 2);
		GridPane.setConstraints(button, 1, 3);
		GridPane.setConstraints(button1, 2, 3);
		

		layout.getChildren().addAll(comboBox1, comboBox2, button,button1, left, right, info, info1);

		// set Scene
		Scene scene = new Scene(layout, 300, 150);

		window.setScene(scene);
		window.show();

	}
}

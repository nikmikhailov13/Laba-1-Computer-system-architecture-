package nikmikhailov13;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Adding {
//FM
	static String answer = "";

	public static String display(String type, String editing) {

		// Stage settings
		Stage window = new Stage();
		window.setTitle(type);
		window.getIcons().add(new Image(FileManager.class.getResourceAsStream("ptencoff.png")));
		// Label
		Label label1 = new Label();
		label1.setText("Enter name");

		// Textfield
		TextField textfield1 = new TextField(editing);
		textfield1.setPromptText("Rootname");

		// Button
		Button button1 = new Button("Ok");
		button1.setOnAction(e -> {
			answer = textfield1.getText();
			window.close();
		});

		textfield1.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				answer = textfield1.getText();
				window.close();
			}
		});

		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setHgap(10);

		GridPane.setConstraints(label1, 0, 0);
		GridPane.setConstraints(textfield1, 1, 0);
		GridPane.setConstraints(button1, 2, 0);

		layout.getChildren().addAll(label1, textfield1, button1);
		// Scene
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		

		return answer;
	}

}

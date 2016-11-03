package nikmikhailov13;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

	static boolean answer = false;

	public static boolean display (String title, String message, String object) {
		
		//Stage settings
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(400);
		
		window.getIcons().add(new Image(FileManager.class.getResourceAsStream("ptencoff.png")));
		//Label
		Label label = new Label();
		label.setText(message + object + "?");

		//Buttons
		Button buttonYes = new Button("YES");
		Button buttonNo = new Button("NO");

		//Buttons - setting on action
		buttonYes.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				answer = true;
				window.close(); 
				}});
		
		buttonYes.setOnMouseClicked(e -> {
				answer = true;
				window.close(); });
				
		buttonNo.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				answer = false;
				window.close(); }});
		
		buttonNo.setOnMouseClicked(e -> {
				answer = false;
				window.close(); });

		//Layouts
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(label, buttonYes, buttonNo);
		layout.setAlignment(Pos.CENTER);

		//Scene
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		return answer;
	}

}

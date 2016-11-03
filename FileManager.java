package nikmikhailov13;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FileManager {

	private static String defaultPath1;
	private static String defaultPath2;
	private static ViewList listView1 = new ViewList();
	private static ViewList listView2 = new ViewList();

	public static void createManager(Stage stage) {

		BufferedReader br1 = null;
		BufferedReader br2 = null;

		try {

			br1 = new BufferedReader(new FileReader("path1.txt"));
			br2 = new BufferedReader(new FileReader("path2.txt"));

			defaultPath1 = br1.readLine();
			defaultPath2 = br2.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br1 != null)
					br1.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// stage Kleinigkeiten
		stage.setTitle("Ptenec FileManager");
		stage.getIcons().add(new Image(FileManager.class.getResourceAsStream("ptencoff.png")));

		/************* Menu - creating and setting *****************/
		// File
		Menu fileMenu = new Menu("File");
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem addDirectory = new MenuItem("Add Directory");
		MenuItem addFile = new MenuItem("Add File");
		MenuItem copy = new MenuItem("Copy");
		MenuItem move = new MenuItem("Move");

		exitItem.setOnAction(e -> stage.close());
		fileMenu.getItems().addAll(addDirectory, addFile, copy, move, new SeparatorMenuItem(), exitItem);

		Menu editMenu = new Menu("Edit");
		MenuItem editName = new MenuItem("Edit Name");
		MenuItem deleteItem = new MenuItem("Delete");
		editMenu.getItems().addAll(editName, new SeparatorMenuItem(), deleteItem);

		Menu helpMenu = new Menu("Help");
		MenuItem about = new MenuItem("About");
		MenuItem info = new MenuItem("How to use");
		info.setOnAction(e -> {

			String os = System.getProperty("os.name").toLowerCase();
			try {
				if (os.contains("nix") || os.contains("nux") || os.contains("mac") || os.indexOf("aix") > 0) {
					Runtime.getRuntime().exec(new String[] { "xdg-open", new File("information.txt").getAbsolutePath() });
				} else if (os.contains("win")) {
					Desktop.getDesktop().open(new File("information.txt"));
				}
			} catch (Exception ex) {
			}
		});

		helpMenu.getItems().addAll(info, about);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Copyright © 2016 : All rights reserved.");
		alert.setHeaderText("Created by Nikita Mykhailov");
		alert.setTitle("Information");
		alert.setGraphic(new ImageView(FileManager.class.getResource("pten1.png").toString()));
		Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
		stage1.getIcons().add(new Image(FileManager.class.getResourceAsStream("ptencoff.png")));

		Menu options = new Menu("Settings");
		MenuItem optItem = new MenuItem("Default disks");
		options.getItems().add(optItem);
		options.setOnAction(e -> {
			ChooseDisk.display();
		});

		about.setOnAction(e -> alert.show());
		// setting MenuItems

		// setting MenuBar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, editMenu, options, helpMenu);

		/*************** TextFields - creating *******************/
		TextField textfield1 = new TextField(defaultPath1);
		TextField textfield2 = new TextField(defaultPath2);

		/****************** ListViews - setting ******************/
		List.open(listView1, textfield1, defaultPath1);
		List.open(listView2, textfield2, defaultPath2);

		listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		addDirectory.setOnAction(e -> {
			if (listView1.isFocused()) {
				List.addDirectory(textfield1);
				List.open(listView1, textfield1, textfield1.getText());
			} else if (listView2.isFocused()) {
				List.addDirectory(textfield2);
				List.open(listView2, textfield2, textfield2.getText());

			}
		});
		addFile.setOnAction(e -> {
			if (listView1.isFocused()) {
				List.addFile(textfield1);
				List.open(listView1, textfield1, textfield1.getText());
			} else if (listView2.isFocused()) {
				List.addFile(textfield2);
				List.open(listView2, textfield2, textfield2.getText());

			}
		});
		copy.setOnAction(e -> {
			if (listView1.isFocused()) {
				List.copy(listView1, listView2, textfield1, textfield2);
				List.open(listView1, textfield1, textfield1.getText());
				List.open(listView2, textfield2, textfield2.getText());
			}

			else if (listView2.isFocused()) {
				List.copy(listView2, listView1, textfield2, textfield1);
				List.open(listView1, textfield1, textfield1.getText());
				List.open(listView2, textfield2, textfield2.getText());
			}
		});
		move.setOnAction(e -> {
			if (listView1.isFocused()) {
				List.move(listView1, listView2, textfield1, textfield2);
				List.open(listView1, textfield1, textfield1.getText());
				List.open(listView2, textfield2, textfield2.getText());
			}

			else if (listView2.isFocused()) {
				List.move(listView2, listView1, textfield2, textfield1);
				List.open(listView1, textfield1, textfield1.getText());
				List.open(listView2, textfield2, textfield2.getText());
			}
		});
		editName.setOnAction(e -> {
			if (listView1.isFocused()) {
				List.edit(listView1);
				List.open(listView1, textfield1, textfield1.getText());
			} else if (listView2.isFocused()) {
				List.edit(listView2);
				List.open(listView2, textfield2, textfield2.getText());
			}
		});
		deleteItem.setOnAction(e -> {
			if (listView1.isFocused()) {
				List.delete(listView1, textfield1);
				List.open(listView1, textfield1, textfield1.getText());
			} else if (listView2.isFocused()) {
				List.delete(listView2, textfield2);
				List.open(listView2, textfield2, textfield1.getText());
			}
		});

		/**************** TextFields - setting *******************/
		textfield1.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				List.open(listView1, textfield1, textfield1.getText());
		});

		textfield2.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				List.open(listView2, textfield2, textfield2.getText());
		});

		/************* Layouts - creating and setting ************/
		BorderPane layout = new BorderPane();

		VBox leftVbox = new VBox();
		VBox rightVbox = new VBox();
		HBox centerBox = new HBox();

		HBox.setHgrow(rightVbox, Priority.ALWAYS);
		HBox.setHgrow(leftVbox, Priority.ALWAYS);
		VBox.setVgrow(listView1, Priority.ALWAYS);
		VBox.setVgrow(listView2, Priority.ALWAYS);

		leftVbox.getChildren().addAll(textfield1, listView1);
		rightVbox.getChildren().addAll(textfield2, listView2);
		centerBox.getChildren().addAll(leftVbox, rightVbox);

		HBox buttomMenu = new HBox(20);
		buttomMenu.setPadding(new Insets(10, 10, 10, 10));

		/******************* Labels - creating *****************/

		Label f1 = new Label("F1 Add Directory");
		Label f2 = new Label("F2 Add File");
		Label f3 = new Label("F3 Copy");
		Label f4 = new Label("F4 Move");
		Label f5 = new Label("F5 Refresh");
		Label f6 = new Label("F6 Edit");
		Label f7 = new Label("F7 Info");

		Label delete = new Label("delete Delete");

		// separators
		Separator separator1 = new Separator(Orientation.VERTICAL);
		Separator separator2 = new Separator(Orientation.VERTICAL);
		Separator separator3 = new Separator(Orientation.VERTICAL);
		Separator separator4 = new Separator(Orientation.VERTICAL);
		Separator separator5 = new Separator(Orientation.VERTICAL);
		Separator separator6 = new Separator(Orientation.VERTICAL);
		Separator separator7 = new Separator(Orientation.VERTICAL);

		buttomMenu.getChildren().addAll(f1, separator1, f2, separator2, f3, separator3, f4, separator4, f5, separator5,
				f6, separator6, f7, separator7, delete);

		// positioning
		layout.setCenter(centerBox);
		layout.setBottom(buttomMenu);
		layout.setTop(menuBar);

		/******************** Scene - creating ******************/
		Scene scene = new Scene(layout, 800, 550);

		stage.setScene(scene);
		stage.show();
		listView1.requestFocus();

		List.events(listView1, textfield1, listView2, textfield2);
		List.events(listView2, textfield2, listView1, textfield1);
	}

}

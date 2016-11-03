package nikmikhailov13;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class List {

	public static void open(ListView<File> filesList, TextField textfield, String path) {
		if (path != null) {
			ObservableList<File> listViewData1 = FXCollections.observableArrayList();
			ObservableList<File> listViewData2 = FXCollections.observableArrayList();

			File curFolder = new File(path);
			if (!curFolder.isFile()) {
				if (curFolder.exists()) {

					for (File file : curFolder.listFiles()) {
						if (file.isDirectory() && (!(file.isHidden())) && (file.canRead()) && file.exists())
							listViewData1.add(file);
						else if (file.isFile() && (!(file.isHidden())) && (file.canRead()))
							listViewData2.add(file);
					}

					listViewData1.addAll(listViewData2);
					filesList.setItems(listViewData1);

					textfield.setText(path);
				}
			} else if (curFolder.isFile()) {
				String os = System.getProperty("os.name").toLowerCase();
				try {
					if (os.contains("nix") || os.contains("nux") || os.contains("mac") || os.indexOf("aix") > 0) {
						Runtime.getRuntime().exec(new String[] { "xdg-open", curFolder.getAbsolutePath() });
					} else if (os.contains("win")) {
						Desktop.getDesktop().open(curFolder);
					}
				} catch (Exception ex) {
				}
			}
			// try {
			// Desktop.getDesktop().open(curFolder);
			// try {
			// Desktop.getDesktop().wait();
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// } catch (IOException e) {
			// e.printStackTrace();
			//
			// }
		}
	}

	public static String back(ListView<File> filesList, TextField textfield, String pathParent) {
		if (pathParent != null) {
			pathParent = (new File(pathParent)).getParent();
			open(filesList, textfield, pathParent);

		}
		return pathParent;
	}

	public static void events(ViewList fileslist1, TextField textfield1, ViewList fileslist2, TextField textfield2) {
		fileslist1.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				String path = fileslist1.getSelectionModel().getSelectedItem().getAbsolutePath();

				List.open(fileslist1, textfield1, path);
				System.out.println(path);

				System.out.println("open" + path);
			}

			if (e.getCode() == KeyCode.BACK_SPACE) {
				String pathParent = textfield1.getText();
				if (pathParent == null)
					textfield1.setPromptText("Enter  E:\\");
				else {
					textfield1.setText(List.back(fileslist1, textfield1, pathParent));
					System.out.println(pathParent);
				}

			}

			if (e.getCode() == KeyCode.F5) {
				open(fileslist1, textfield1, textfield1.getText());
				System.out.println("refreshed");
			}

			if (e.getCode() == KeyCode.F1) {
				addDirectory(textfield1);
				open(fileslist1, textfield1, textfield1.getText());

			}

			if (e.getCode() == KeyCode.F2) {
				addFile(textfield1);
				open(fileslist1, textfield1, textfield1.getText());

			}

			if (e.getCode() == KeyCode.DELETE) {
				delete(fileslist1, textfield1);

			}

			if (e.getCode() == KeyCode.F3) {
				copy(fileslist1, fileslist2, textfield1, textfield2);
				open(fileslist2, textfield2, textfield2.getText());
			}

			if (e.getCode() == KeyCode.F4) {
				move(fileslist1, fileslist2, textfield1, textfield2);
				open(fileslist2, textfield2, textfield2.getText());
			}

			if (e.getCode() == KeyCode.F6) {
				edit(fileslist1);
				open(fileslist1, textfield1, textfield1.getText());

			}
			if (e.getCode() == KeyCode.F7) {

				String os = System.getProperty("os.name").toLowerCase();
				try {
					if (os.contains("nix") || os.contains("nux") || os.contains("mac") || os.indexOf("aix") > 0) {
						Runtime.getRuntime()
								.exec(new String[] { "xdg-open", new File("information.txt").getAbsolutePath() });
					} else if (os.contains("win")) {
						Desktop.getDesktop().open(new File("information.txt"));
					}
				} catch (Exception ex) {
				}
			}
			// try {
			// Desktop.getDesktop().open(new File("Read me.txt"));
			// } catch (IOException e1) {
			// e1.printStackTrace();
			// }

		});

		fileslist1.setOnMouseClicked(e -> {
			int clickCount = e.getClickCount();

			switch (e.getButton()) {
			case PRIMARY:
				if (clickCount == 2) {
					String path = fileslist1.getSelectionModel().getSelectedItem().getAbsolutePath();
					List.open(fileslist1, textfield1, path);
					System.out.println(path);
					System.out.println("open" + path);
				}
				break;

			case SECONDARY:
				String pathParent = textfield1.getText();
				if (pathParent == null)
					textfield1.setPromptText("Enter  E:\\");
				else {
					textfield1.setText(List.back(fileslist1, textfield1, pathParent));
					System.out.println(pathParent);
				}
				break;
			default:
				break;

			}
		});
	}

	public static void copy(ViewList fileslist1, ViewList fileslist2, TextField textfield1, TextField textfield2) {

		Object[] o = fileslist1.getSelectionModel().getSelectedItems().toArray();
		for (Object object : o) {
			File file = (File) object;
			if (file.isDirectory()) {
				try {
					FileUtils.copyDirectoryToDirectory(file, new File(textfield2.getText()));
					System.out.println("Directory copied");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {

				try {

					FileUtils.copyFileToDirectory(file, new File(textfield2.getText()));
					System.out.println("File copied");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void move(ViewList fileslist1, ViewList fileslist2, TextField textfield1, TextField textfield2) {

		Object[] o = fileslist1.getSelectionModel().getSelectedItems().toArray();
		for (Object object : o) {
			File file = (File) object;

			if (file.isDirectory()) {
				try {
					FileUtils.moveDirectoryToDirectory(file, new File(textfield2.getText()), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					FileUtils.moveFileToDirectory(file, new File(textfield2.getText()), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			open(fileslist1, textfield1, textfield1.getText());
		}
	}

	public static void delete(ViewList fileslist1, TextField textfield1) {

		Object[] files = fileslist1.getSelectionModel().getSelectedItems().toArray();
		for (Object obj : files) {
			File file = (File) obj;

			String type = "";
			if (file.isFile())
				type += "File";
			else if (file.isDirectory())
				type += "Directory";
			ConfirmBox.display("Deleting " + type, "Do you want to delete ", file.getName());
			if (ConfirmBox.answer == true) {
				if (file.isDirectory()) {
					try {
						FileUtils.deleteDirectory(file);
						open(fileslist1, textfield1, textfield1.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (file.isFile()) {
					file.delete();
					open(fileslist1, textfield1, textfield1.getText());
				}
			}
		}

	}

	public static void addDirectory(TextField textfield) {

		File file = new File(textfield.getText() + "\\" + Adding.display("Creating Directory", ""));
		file.mkdir();
	}

	public static void addFile(TextField textfield) {

		File file = new File(textfield.getText() + "\\" + Adding.display("Creating File", ""));
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void edit(ViewList fileslist) {

		File file = fileslist.getSelectionModel().getSelectedItem();
		String name = fileslist.getSelectionModel().getSelectedItem().getName();
		String fname = file.getParent();
		file.renameTo(new File(fname + "\\" + Adding.display("Editing", name)));
	}

}

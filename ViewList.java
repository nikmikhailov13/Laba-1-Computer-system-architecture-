package nikmikhailov13;

import java.io.File;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ViewList extends ListView<File> {
	
	public ViewList() {
		 setCellFactory(new Callback<javafx.scene.control.ListView<File>, ListCell<File>>() {
	        @Override
	        public ListCell<File> call(javafx.scene.control.ListView<File> list) {
	            return new IconAssist.AttachmentListCell(ViewList.this);
	        }
	    });
	}
}

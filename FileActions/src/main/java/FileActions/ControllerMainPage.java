package FileActions;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;

/**
 * Created by MyMac on 8/15/17.
 */
public class ControllerMainPage {
    @FXML
    public TreeView filesTree;

    ControllerMainPage(){
        filesTree = new TreeView<String> ();
    }

    public TreeView getTreeView(){
        return filesTree;
    }
}

package org.example.unko;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

//import JavaFX
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;

//import kotlin
import kotlin.Unit;
import org.example.util.RandomSize;
import org.example.util.FixationSize;

public class BFGController{
    private final Map<String, Node> nodeMap = new HashMap<>();
    final String[] nodeString = {"nameText","extensionText","fileSize","checkBox","slider","randomA","randomB","labelA","labelB","fileSize","progressBar"};
    boolean boxState = false;

    @FXML
    private Parent root;

    @FXML
    public void initialize() {
        for (String i : nodeString) nodeMap.put(i, root.lookup("#" + i));

        ((CheckBox) nodeMap.get("checkBox")).selectedProperty().addListener((_, _, newValue) -> {
            Set<Node> randObject = root.lookupAll(".random");
            for (Node node : randObject) node.setVisible(newValue);
            boxState = newValue;
            if (nodeMap.get("fileSize") instanceof TextField textField) textField.setDisable(newValue);
        });

        ((Slider) nodeMap.get("slider")).valueProperty().addListener((_, _, newValue) -> {
            final float fRandomA = Float.parseFloat(((TextField) nodeMap.get("randomA")).getText());
            final float fRandomB = Float.parseFloat(((TextField) nodeMap.get("randomB")).getText());
            float perValue = (float) Math.floor((Double) newValue*10)/1000;

            int iRandomA = (int)Math.floor(fRandomA*(perValue+1));
            int iRandomB = (int)Math.floor(fRandomB*(perValue+1));
            ((Label) nodeMap.get("labelA")).setText(String.valueOf(iRandomA));
            ((Label) nodeMap.get("labelB")).setText(String.valueOf(iRandomB));
        });

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*")? change: null;
        };
        Set<Node> numNode = root.lookupAll(".onlyNum");
        for(Node node: numNode){
            if(node instanceof TextField num){
                num.setTextFormatter(new TextFormatter<>(filter));
            }
        }
    }

    @FXML
    protected void onGenerate() {
        String fileName = ((TextField) nodeMap.get("nameText")).getText();
        String extension = ((TextField) nodeMap.get("extensionText")).getText();
        ProgressBar bar = ((ProgressBar) nodeMap.get("progressBar"));

        if(boxState){
            int randomA = Integer.parseInt(((Label) nodeMap.get("labelA")).getText());
            int randomB = Integer.parseInt(((Label) nodeMap.get("labelB")).getText());
            bar.setVisible(true);
            
            RandomSize random = new RandomSize(fileName, extension, randomA, randomB);
            random.generate(percent -> {
                bar.setProgress(percent);
                //if(percent >= 1) bar.setVisible(false);
                return Unit.INSTANCE;
            });
        }else {
            int size = Integer.parseInt(((TextField) nodeMap.get("fileSize")).getText());
            bar.setVisible(true);

            FixationSize fixation = new FixationSize(fileName, extension, size);
            fixation.generate(percent -> {
                bar.setProgress(percent);
                //if(percent >= 1) bar.setVisible(false);
                return Unit.INSTANCE;
            });
        }
    }
}
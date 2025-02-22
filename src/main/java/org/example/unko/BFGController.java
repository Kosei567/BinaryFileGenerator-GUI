package org.example.unko;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import kotlin.Unit;
import org.example.util.Random;
import org.example.util.Fixation;

public class BFGController{

    private final Map<String, Node> nodeMap = new HashMap<>();
    final String[] nodeString = {"nameText","extensionText","fileSize","checkBox","slider","randomA","randomB","ALabel","BLabel","fileSize","progressBar"};
    boolean state = false;

    @FXML
    private Parent root;

    @FXML
    public void initialize() {
        for (String i : nodeString) nodeMap.put(i, root.lookup("#" + i));

        ((javafx.scene.control.CheckBox) nodeMap.get("checkBox")).selectedProperty().addListener((obs, oldValue, newValue) -> {
            Set<Node> randObject = root.lookupAll(".random");
            for (Node node : randObject) node.setVisible(newValue);
            state = newValue;
            if (nodeMap.get("fileSize") instanceof javafx.scene.control.TextField textField) textField.setDisable(newValue);
        });

        ((javafx.scene.control.Slider) nodeMap.get("slider")).valueProperty().addListener((obs,oldValue,newValue) -> {
            TextField randomA = ((javafx.scene.control.TextField) nodeMap.get("randomA"));
            TextField randomB = ((javafx.scene.control.TextField) nodeMap.get("randomB"));
            final float fRandomA = Float.parseFloat(randomA.getText());
            final float fRandomB = Float.parseFloat(randomB.getText());
            float perValue = (float) Math.floor((Double) newValue*10)/1000;

            int iRandomA = (int)Math.floor(fRandomA*(perValue+1));
            int iRandomB = (int)Math.floor(fRandomB*(perValue+1));
            ((javafx.scene.control.Label) nodeMap.get("ALabel")).setText(String.valueOf(iRandomA));
            ((javafx.scene.control.Label) nodeMap.get("BLabel")).setText(String.valueOf(iRandomB));

            System.out.println(fRandomA);
        });

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*")? change: null;
        };
        Set<Node> numNode = root.lookupAll(".onlyNum");
        for(Node node: numNode){
            if(node instanceof javafx.scene.control.TextField num){
                num.setTextFormatter(new TextFormatter<>(filter));
            }
        }

    }

    @FXML
    protected void onGenerate() {
        String fileName = ((javafx.scene.control.TextField) nodeMap.get("nameText")).getText();
        String extension = ((javafx.scene.control.TextField) nodeMap.get("extensionText")).getText();
        var random = new Random(fileName, extension,100, 150);
        random.generate(percent -> {
            System.out.println(percent);
            return Unit.INSTANCE;
        });
    }
}
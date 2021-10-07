package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IndiceMasaCorporal extends Application {
	
	Label pesoLabel, alturaLabel, kgLabel, cmLabel, imcLabel, calificacionLabel;
	TextField pesoText, alturaText;
	HBox arribaHBox, centroHBox, abajoHBox;
	VBox root;
	
	//CREAMOS LAS PROPERTIES PARA LOS BINDEOS DE PESO Y ALTURA
	DoubleProperty pesoProperty;
	DoubleProperty alturaProperty;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		pesoProperty = new SimpleDoubleProperty();
		alturaProperty = new SimpleDoubleProperty();
		
		
		pesoLabel = new Label();
		pesoLabel.setText("Peso:");
		
		alturaLabel = new Label();
		alturaLabel.setText("Altura:");
		
		pesoText = new TextField();
		pesoText.setPromptText("Introduzca su peso...");
		pesoText.setPrefWidth(125);
		
		alturaText = new TextField();
		alturaText.setPromptText("Introduzca su altura...");
		alturaText.setPrefWidth(125);
		
		kgLabel = new Label();
		kgLabel.setText("kg");
		
		cmLabel = new Label();
		cmLabel.setText("cm");
		
		imcLabel = new Label();
		imcLabel.setText("IMC -> ");
		
		calificacionLabel = new Label();
		calificacionLabel.setText("Bajo Peso | Normal | Sobrepeso | Obeso");
		
		arribaHBox = new HBox(5);
		centroHBox = new HBox(5);
		abajoHBox = new HBox(5);
		root = new VBox();
		
		arribaHBox.getChildren().addAll(pesoLabel,pesoText,kgLabel);
		arribaHBox.setAlignment(Pos.CENTER);
		
		
		centroHBox.getChildren().addAll(alturaLabel,alturaText,cmLabel);
		centroHBox.setAlignment(Pos.CENTER);
		
		abajoHBox.getChildren().addAll(imcLabel);
		abajoHBox.setAlignment(Pos.CENTER);
		
		root.getChildren().addAll(arribaHBox, centroHBox, abajoHBox, calificacionLabel);
		root.setPadding(new Insets(5,5,5,5));
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//BINDEOS
		
		Bindings.bindBidirectional(alturaText.textProperty(), alturaProperty, new NumberStringConverter());
		
		Bindings.bindBidirectional(pesoText.textProperty(), pesoProperty, new NumberStringConverter());
		
		DoubleBinding calculo;
		calculo = (pesoProperty.multiply(alturaProperty.multiply(alturaProperty)).divide(100000));
		
		DoubleProperty calculo2 = new SimpleDoubleProperty();
		calculo2.bind(calculo);
		
		imcLabel.textProperty().bind(Bindings.concat("IMC -> ").
				concat(Bindings.when(alturaProperty.isEqualTo(0)).then("").otherwise(calculo2.asString())));
		
		calculo2.addListener((o, ov, nv)->{
			double imc=nv.doubleValue();
			
			if (imc < 18.5)
				calificacionLabel.setText("Bajo Peso");
			else if (imc >= 18.5 && imc < 25)
				calificacionLabel.setText("Normal");
			else if (imc >=25 && imc < 30)
				calificacionLabel.setText("Sobrepeso");
			else
				calificacionLabel.setText("Obeso");
			
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}

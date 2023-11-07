package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 * JavaFX App
 */
public class App extends Application {

    private final static int SCENE_HEIGHT = 500;
    private final static int SCENE_WIDTH = 500;

    @Override
    public void start(Stage stage) {

        Pane pane = new Pane();
        Scene scene = new Scene(pane, SCENE_HEIGHT, SCENE_WIDTH);
        Circle circle = new Circle(20);
        circle.relocate(10,10);



        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Purple", "Pink", "Green"));
        cb.relocate(0,20);

        Label l = new Label("Select ball color");
        l.relocate(0,0);

        Button b = new Button("Hide Ball");
        b.relocate(0,50);

        Button hide = new Button("Hide");
        hide.relocate(455,455);

        pane.getChildren().add(circle);
        pane.getChildren().add(cb);
        pane.getChildren().add(b);
        pane.getChildren().add(l);
        pane.getChildren().add(hide);

        pane.setStyle("-fx-background-color: teal;");

        stage.setScene(scene);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
            double dx = 2;
            double dy = 5;
            @Override
            public void handle(ActionEvent actionEvent) {
                cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        switch (t1.intValue()) {
                            case 0:
                                circle.setFill(Color.PURPLE);
                                break;
                            case 1:
                                circle.setFill(Color.PINK);
                                break;
                            case 2:
                                circle.setFill(Color.GREEN);
                        }
                    }
                });

                EventHandler<ActionEvent> eventHide = actionEvent1 -> {
                    if (circle.isVisible()) {
                        circle.setVisible(false);
                        b.setText("Show ball");
                    } else {
                        circle.setVisible(true);
                        b.setText("Hide ball");
                    }
                };

                b.setOnAction(eventHide);

                EventHandler<ActionEvent> eventHideAll = actionEvent1 -> {
                    if (cb.isVisible()) {
                        cb.setVisible(false);
                        b.setVisible(false);
                        l.setVisible(false);
                        hide.setText("Show");
                    } else {
                        cb.setVisible(true);
                        b.setVisible(true);
                        l.setVisible(true);
                        hide.setText("Hide");
                    }
                };

                hide.setOnAction(eventHideAll);



                circle.setLayoutX(circle.getLayoutX() + dx);
                circle.setLayoutY(circle.getLayoutY() + dy);

                Bounds bounds = pane.getBoundsInLocal();

                if (circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius()) ||
                        circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius())) {
                    System.out.println("Out of bounds X " + circle.getLayoutX());

                    dx = -dx;
                }

                if (circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius()) ||
                        circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius())) {
                    dy = -dy;
                }

            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }

}
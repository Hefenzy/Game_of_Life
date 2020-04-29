package com.adomtitlla;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Button stepButton;
    private Canvas canvas;
    private Simulation simulation;
    private Affine affine;

    public MainView() {
        this.stepButton = new Button("step");
        this.stepButton.setOnAction(actionEvent -> {    // Setting action on step button
            simulation.step();  // Every click make step
            draw();    // and redraw
        });

        this.canvas = new Canvas(800, 800);
        this.canvas.setOnMousePressed(this::handleDraw);

        this.getChildren().addAll(this.stepButton, this.canvas);

        this.simulation = new Simulation(40, 40);   // Field 40x40

        this.affine = new Affine();
        this.affine.appendScale(800 / 40f, 800 / 40f);  // Append cells
    }

    private void handleDraw(MouseEvent mouseEvent) {    // Read mouse and draw cells
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        System.out.println(mouseX + ", " + mouseY);    // Print click coordinates in terminal (Debug)
        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);

            int simX = (int) simCoord.getX();   // get x cell coordinate
            int simY = (int) simCoord.getY();   // get y cell coordinate

            System.out.println(simX + ", " + simY);    // Print cells coordinates in terminal (Debug)

            if (!this.simulation.getState(simX, simY))    // If cell is dead, make it alive
                this.simulation.setAlive(simX, simY);
            else this.simulation.setDead(simX, simY);     // If cell is alive, kill it
            draw();
        } catch (NonInvertibleTransformException e) {    // If can't read coordinates (Debug)
            System.out.println("Could not invert transform");
        }
    }

    public void draw() {
        GraphicsContext gC2D = this.canvas.getGraphicsContext2D();  // 2D graphics
        gC2D.setTransform(this.affine);    // On affine
        gC2D.setFill(Color.LIGHTGRAY);     // Color
        gC2D.fillRect(0, 0, 800, 800);  // Draw background

        // Draw live cells
        gC2D.setFill(Color.BLACK);
        for (int x = 0; x < this.simulation.WIDTH; x++) {   // Loop in loop - run through all cells
            for (int y = 0; y < this.simulation.HEIGHT; y++) {
                if (this.simulation.getState(x, y))    // If cell is alive - paint it black like in Rolling Stones song
                    gC2D.fillRect(x,y,1,1);
            }
        }

        // Draw lines
        gC2D.setStroke(Color.GREEN);
        gC2D.setLineWidth(0.05);
        for (int x = 0; x <= this.simulation.WIDTH; x++) {  // Horizontal lines
            gC2D.strokeLine(x, 0, x, 40);
        }

        for (int y = 0; y <= this.simulation.HEIGHT; y++) { // Vertical lines
            gC2D.strokeLine(0, y, 40, y);
        }
    }
}
// Bookmark 10:26
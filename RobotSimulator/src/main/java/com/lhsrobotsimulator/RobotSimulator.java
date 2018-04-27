package com.lhsrobotsimulator;

import static java.lang.Math.*;
import autocode.SampleAutoCode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lhsrobotapi.autocode.AutoCode;

public class RobotSimulator extends Application
{
    @SuppressWarnings("SuspiciousNameCombination")
    private static final int MARGIN = 16, WIDTH = 512, HEIGHT = WIDTH;
    private Canvas canvas;
    private Scene scene;

    // Starting point for the application
    public static void main(String[] args)
    {
        launch(args);
    }

    // Starting point for JavaFX
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Robot Simulator");
        VBox root = new VBox();

        // The rendering area
        canvas = new Canvas(WIDTH + 2 * MARGIN, HEIGHT+ 2 * MARGIN);

        root.getChildren().addAll(canvas);
        scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                AutoCode code = new SampleAutoCode();
                LincolnRobot robot = new LincolnRobot();
                robot.simulator = RobotSimulator.this;
                code.drivetrain = new SimulatedRobotDrivetrain(robot);
                code.run();
            }
        });
        thread.start();
    }


    public void update(LincolnRobot robot)
    {
        // TODO update canvas on javafx thread
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.TRANSPARENT);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(MARGIN, MARGIN, WIDTH, HEIGHT);

        Rectangle robotRectangle = new Rectangle(MARGIN + robot.x, MARGIN + robot.y, robot.width * .75, robot.height);
        Rectangle robotFrontRectangle = new Rectangle(robotRectangle.trX, robotRectangle.trY, robot.width * .25, robot.height);
        robot.rotation = 0;
        robotRectangle.rotate(robot.rotation);
        robotFrontRectangle.rotate(robot.rotation);

        gc.setFill(Color.BLUE);

        gc.beginPath();
        gc.moveTo(robotRectangle.tlX, robotRectangle.tlY);
        gc.lineTo(robotRectangle.trX, robotRectangle.trY);
        gc.lineTo(robotRectangle.brX, robotRectangle.brY);
        gc.lineTo(robotRectangle.blX, robotRectangle.blY);
        gc.fill();

        gc.setFill(Color.RED);
        gc.beginPath();
        gc.moveTo(robotFrontRectangle.tlX, robotFrontRectangle.tlY);
        gc.lineTo(robotFrontRectangle.trX, robotFrontRectangle.trY);
        gc.lineTo(robotFrontRectangle.brX, robotFrontRectangle.brY);
        gc.lineTo(robotFrontRectangle.blX, robotFrontRectangle.blY);
        gc.fill();
    }

    private class Rectangle
    {
        double tlX, tlY, trX, trY, brX, brY, blX, blY;

        public Rectangle(double left, double top, double width, double height)
        {
            double right = left + width;
            double bottom = top + height;

            this.tlX = left;
            this.tlY = top;

            this.trX = right;
            this.trY = top;

            this.brX = right;
            this.brY = bottom;

            this.blX = left;
            this.blY = bottom;
        }

        void rotate(double angle)
        {
            angle = toRadians(90 - angle);

            double centerX = (tlX + trX + brX + blX) / 4.0;
            double centerY = (tlY + trY + brY + blY) / 4.0;

            tlX -= centerX;
            trX -= centerX;
            brX -= centerX;
            blX -= centerX;

            tlY -= centerY;
            trY -= centerY;
            brY -= centerY;
            blY -= centerY;

            tlX = tlX * cos(angle) - tlY * sin(angle);
            tlY = tlX * sin(angle) + tlY * cos(angle);

            trX = trX * cos(angle) - trY * sin(angle);
            trY = trX * sin(angle) + trY * cos(angle);

            brX = brX * cos(angle) - brY * sin(angle);
            brY = brX * sin(angle) + brY * cos(angle);

            blX = blX * cos(angle) - blY * sin(angle);
            blY = blX * sin(angle) + blY * cos(angle);

            System.out.println(tlX + " " + tlY);
            System.out.println(trX + " " + trY);
            System.out.println(brX + " " + brY);
            System.out.println(blX + " " + blY);
            System.out.println();

            tlX += centerX;
            trX += centerX;
            brX += centerX;
            blX += centerX;

            tlY += centerY;
            trY += centerY;
            brY += centerY;
            blY += centerY;
        }
    }

    @Override
    public void stop()
    {
        System.exit(0);
    }
}
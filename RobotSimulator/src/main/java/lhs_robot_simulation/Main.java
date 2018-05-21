package lhs_robot_simulation;

import java.io.File;

import com.lincolnrobotics.SampleRobotAutonomousControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The JavaFx application main routine.
 * JavaFx was chosen to minimize the impact on the Java code
 * in the lincolnControl package.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //  load the sim user interface
        FXMLLoader loader = new FXMLLoader(new File("RobotSimulator/build/resources/main/lhs_robot_simulation/sim.fxml").toURI().toURL());
        Parent root = loader.load();
        primaryStage.setTitle("Lincoln Robot Simulation");
        primaryStage.setScene(new Scene(root, 850, 900));
        primaryStage.show();

        //  connect the autonomous controller to the simulation sequencer
        {
            RobotSimulationJavaFxController robotSimulationJavaFxController = loader.getController();
            RobotModel m = robotSimulationJavaFxController.getRobotModel();

            //  start the simulation
            robotSimulationControlSequencer =
                    new RobotSimulationControlSequencer(new SampleRobotAutonomousControl(), m);
            robotSimulationControlSequencer.start();

            //  register the restart event handler
            robotSimulationJavaFxController.registerRestartEventHandler(robotSimulationControlSequencer);
        }
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     * <p>
     * <p>
     * The implementation of this method provided by the Application class does nothing.
     * </p>
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     */
    @Override
    public void stop() throws Exception {
        if (robotSimulationControlSequencer != null)
            robotSimulationControlSequencer.stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private RobotSimulationControlSequencer robotSimulationControlSequencer;
}

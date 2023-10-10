package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.util.Duration;

import java.util.ResourceBundle;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class Controller  implements Initializable  {


    MediaPlayer player;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button prevBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Slider timeSlider;

    @FXML
    private MenuItem exit;

    @FXML
    void openSongMenu(ActionEvent event) {

       try {

            System.out.println("Open song Clicked");

            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);

            Media m = new Media(file.toURI().toString());

            if(player!= null) {

                player.dispose();
            }

            player = new MediaPlayer(m);

            mediaView.setMediaPlayer(player);

            player.setOnReady(() -> {
                //when player gets ready..
                timeSlider.setMin(0);
                timeSlider.setMax(player.getMedia().getDuration().toMinutes());

                timeSlider.setValue(0);
                try {
                    playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });


            // Sliding the time line 

             //listener on player...

             player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    //coding...
                    Duration d = player.getCurrentTime();

                    timeSlider.setValue(d.toSeconds());
                }
            });

//            time slider

            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (timeSlider.isPressed()) {
                        double val = timeSlider.getValue();
                        player.seek(new Duration(val * 1000));
                    }
                }
            });
        }catch(Exception e) {

            e.printStackTrace();
       }
    } 
    
    @FXML
    void play(ActionEvent event) {

        MediaPlayer.Status status = player.getStatus();
        try {
                 if(status == MediaPlayer.Status.PLAYING) {

                    player.pause();
                    playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
                }
                else {

                    player.play();
                    playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pause.png"))));
                }

         }catch(Exception e) {

            e.printStackTrace();
         }
    }

    @FXML
    void editPause(ActionEvent event) {

        MediaPlayer.Status status = player.getStatus();
        try {

            if(status == MediaPlayer.Status.PLAYING) {

               player.pause();
               playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
           }
    }catch(Exception e) {

       e.printStackTrace();
    }
    }

    @FXML
    void editPlay(ActionEvent event) {

        MediaPlayer.Status status = player.getStatus();

        try {
            if(status != MediaPlayer.Status.PLAYING) {

                player.play();
               playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/pause.png"))));
           }
           
            }catch(Exception e) {

            e.printStackTrace();
        }
        

    }

    @FXML
    void exitPlayer(ActionEvent event) {

        // Platform.exit();
        System.exit(0);
    }   

    @FXML
    void preBtnClick(ActionEvent event) {

        double d = player.getCurrentTime().toSeconds();
        d = d - 10;
        player.seek(new Duration(d * 1000));
        
    }
    @FXML
    void nextBtnClick(ActionEvent event) {

        double d = player.getCurrentTime().toSeconds();
        d = d + 10;
        player.seek(new Duration(d * 1000));
    }

    @FXML
    void visitUsBtn(ActionEvent event) {
        openWebPage("https://www.core2web.in/");
    }


    private void openWebPage(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (java.io.IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
//     @FXML
//     void aboutUsBtn(ActionEvent event) {
//     try {
//         // Load the about.fxml file
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
//         Parent root = loader.load();

//         // Create a new Scene with the loaded FXML content
//         Scene scene = new Scene(root);

//         // Get the current stage (assuming the button is in a JavaFX stage)
//         Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

//         // Set the new scene on the stage
//         currentStage.setScene(scene);
//     } catch (IOException e) {
//         // Handle any potential exceptions, such as FXML file not found
//         e.printStackTrace();
//     }
// }

    @FXML
    private AnchorPane rootPane;

    Stage primaryStage;
    Parent root;

    @FXML
    void loadSecond(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    // Function to open a web page in the default browser
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
                    
            playBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/play.png"))));
            prevBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/prev.png"))));
            nextBtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/next.png"))));

        }catch(Exception e) {

            e.printStackTrace();
        }
    }



}

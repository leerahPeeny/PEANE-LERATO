
package myMedia;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter
                ("select a file(folder/video.mp4)", "folder/video2.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        
        if(filePath!=null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener(){
                @Override
                public void invalidated(javafx.beans.Observable observable) {
                    mediaPlayer.setVolume(slider.getValue()/100);
                    
                }
            });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    seekSlider.setValue(newValue.toSeconds());
                }
                
            });
            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
                
            });
            mediaPlayer.play();
        }
    }
    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }
    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    }
    @FXML
    private void fastVideo(ActionEvent event){
        mediaPlayer.setRate(1.5);
    }
    @FXML
    private void fasterVideo(ActionEvent event){
        mediaPlayer.setRate(2);
    }
    @FXML
    private void slowVideo(ActionEvent event){
        mediaPlayer.setRate(.75);
    }
    @FXML
    private void slowerVideo(ActionEvent event){
        mediaPlayer.setRate(.5);
    }
    @FXML
    private void exitVideo(ActionEvent event){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

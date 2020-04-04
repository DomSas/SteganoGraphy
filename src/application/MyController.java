package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyController {

    @FXML
    private Label decodedTextLabel;

    @FXML
    private Label instructionLabel;

    @FXML
    private Button showOrigPicButton;
    @FXML
    private Button showEditedPicButton;
    @FXML
    private Button encodeButton;

    @FXML
    private TextField encodeTextField;

    private BufferedImage originalImage;

    // ----------------opening new window when pressed About---------------------
    @FXML
    void aboutInfoShow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutApp.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("About");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();
    }

    @FXML
    void loadNewImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage stage2 = new Stage();
        fileChooser.setInitialDirectory(new File("/home/dom/Desktop/JAVA")); // TODO potom vymazat
        fileChooser.setTitle("Open a JPEG picture to encode");
        File file = fileChooser.showOpenDialog(stage2);
        if (file != null) {
            String fileAsString = file.toString();
//			System.out.println(fileAsString); // vypis PATH ku vybranemu suboru

            if (fileAsString.contains(".jpg") || fileAsString.contains(".jpeg")) { // kontrola ci je vybraty subor JPG
                originalImage = ImageIO.read(new File(fileAsString));
                instructionLabel.setText("Picture was loaded!");
                showOrigPicButton.setDisable(false);
                encodeButton.setDisable(false);
            } else {
                instructionLabel.setText("Wrong file type was chosen!");
                showOrigPicButton.setDisable(true);
                encodeButton.setDisable(true);

            }

        } else {
            instructionLabel.setText("No file was chosen!");
            showOrigPicButton.setDisable(true);
            encodeButton.setDisable(true);
        }

    }

    @FXML
    void decodeButtonClick() {

    }

    @FXML
    void encodeButtonClick() {

        TxTProcessing processedTxt = new TxTProcessing(originalImage);
        if (processedTxt.controlOfPictureLength(encodeTextField.getText())) {        // check if the picture is big enough for message

            String msgToEncode = processedTxt.changeTextToBinary(encodeTextField.getText());
            ImageProcessing imageProcessing = new ImageProcessing(originalImage);

            imageProcessing.showRedValues();

            imageProcessing.outputChangedPixel(0, 0, msgToEncode);
            imageProcessing.outputChangedPixel(0, 0, msgToEncode);

//            imageProcessing.showRedValues();

            //   imageProcessing.setRedValuesOfPicture();


            showEditedPicButton.setDisable(false);
            instructionLabel.setText("Encoding was successful!");
        } else {
            instructionLabel.setText("Message is too long for picture!");
        }


    }

    @FXML
    void showOrigPicButtonClick() {
        showImg(originalImage);
    }

    private void showImg(BufferedImage imgToShow) {
        Image card = SwingFXUtils.toFXImage(imgToShow, null);
        ImageView imageView = new ImageView(card);
        Stage stage = new Stage();
        Group root = new Group(imageView);
        Scene scene = new Scene(root);

        stage.setTitle("Loaded image");
        stage.setScene(scene);
        stage.show();
    }       // method to show BufferedImage object

    public void showEditedPicButtonClick(ActionEvent actionEvent) {
        ImageProcessing imageProcessing = new ImageProcessing(originalImage);
        showImg(imageProcessing.setRedValuesOfPicture());
    }
}
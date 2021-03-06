package application;

import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.DirectoryChooser;
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
    private Button decodeButton;
    @FXML
    private Button saveEditedPicButton;

    @FXML
    private TextField encodeTextField;

    private BufferedImage imageToEdit;
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
        stage.setScene(new Scene(root, 320, 370));
        stage.showAndWait();
    }

    @FXML
    void loadNewImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Stage stage2 = new Stage();
   //     fileChooser.setInitialDirectory(new File("/home/dom/Desktop/JAVA")); // TODO potom vymazat
        fileChooser.setTitle("Open a JPEG picture to encode");
        File file = fileChooser.showOpenDialog(stage2);
        if (file != null) {
            String fileAsString = file.toString();

            if (fileAsString.contains(".png")) { // checks if the chosen file is .PNG
                imageToEdit = ImageIO.read(new File(fileAsString));
                originalImage = ImageIO.read(new File(fileAsString));
                instructionLabel.setText("Picture was loaded!");
                showOrigPicButton.setDisable(false);
                encodeButton.setDisable(false);
                decodeButton.setDisable(false);
            } else {
                instructionLabel.setText("Wrong file type was chosen!");
                showOrigPicButton.setDisable(true);
                encodeButton.setDisable(true);
                decodeButton.setDisable(true);

            }

        } else {
            instructionLabel.setText("No file was chosen!");
            showOrigPicButton.setDisable(true);
            encodeButton.setDisable(true);
            decodeButton.setDisable(true);
        }

    }

    @FXML
    void encodeButtonClick() {

        TxTProcessing processedTxt = new TxTProcessing(imageToEdit);
        if (encodeTextField.getText().isEmpty()) {
            instructionLabel.setText("No message insterted!");
        } else if (processedTxt.controlOfPictureLength(encodeTextField.getText())) {        // check if the picture is big enough for message

            String msgToEncode = processedTxt.changeTextToBinary(encodeTextField.getText());
            ImageProcessing imageProcessing = new ImageProcessing(imageToEdit);

            imageProcessing.showRedValues();
            imageProcessing.showRedValues();

            this.imageToEdit = imageProcessing.setRedValuesOfPicture(processedTxt.changeTextToBinary(encodeTextField.getText()));

            imageProcessing.showRedValues();

            showEditedPicButton.setDisable(false);
            instructionLabel.setText("Encoding was successful!");
            saveEditedPicButton.setDisable(false);
        } else {
            instructionLabel.setText("Message is too long!");
        }

    }

    @FXML
    void showOrigPicButtonClick() {
        showImg(originalImage);
        ImageProcessing imageProcessing = new ImageProcessing(originalImage);

        imageProcessing.showRedValues();

    }

    @FXML
    public void showEditedPicButtonClick() {
        ImageProcessing imageProcessing = new ImageProcessing(imageToEdit);
        TxTProcessing processedTxt = new TxTProcessing(imageToEdit);

        showImg(imageProcessing.setRedValuesOfPicture(processedTxt.changeTextToBinary(encodeTextField.getText())));
    }

    @FXML
    public void saveEditedPictureButtonClick() {

        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            instructionLabel.setText("Directory was not chosen!");
        } else {

            BufferedImage bi = imageToEdit;
            File outputFile = new File(selectedDirectory.getAbsolutePath() + "/encoded.png");
//            System.out.println(outputFile);
//            System.out.println(selectedDirectory.getAbsolutePath());
            try {
                ImageIO.write(bi, "png", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            instructionLabel.setText("File was saved!");
        }


    }

    @FXML
    public void decodeButtonClick() {
        ImageProcessing imageProcessing = new ImageProcessing(originalImage);
        imageProcessing.showRedValues();
        imageProcessing.showRedValuesAsString();

        if (imageProcessing.containsMessage() == 0) {
            instructionLabel.setText("No message in picture!");
        } else {
            decodedTextLabel.setText(imageProcessing.decodeText(imageProcessing.containsMessage()));
            instructionLabel.setText("Decoding successful!");
        }

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

}

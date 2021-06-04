package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import service.ProfilDAO;
import service.UserDAO;
import model.Profil;
import model.User;
import main.Main;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {
    @FXML
    private ImageView photoImgV;


    @FXML
    private TableColumn<User, String> LoginTbclum;

    @FXML
    private TableColumn<User , String> PhotoTbclum;

    @FXML
    private TableColumn<User , String> ProfilTclum;

    @FXML
    private TableView<User> AjouterUserTbview;

    @FXML
    private TableColumn<User , Integer> IdTbclum;

    @FXML
    private TableColumn<User , String> NomCompletTclum;

    @FXML
    private TextField NomPrenomsTxfld;

    @FXML
    private TextField LoginTxfld;

    @FXML
    private ComboBox<Profil> ProfilCbox;

    @FXML
    private TextField PasswordTxfld;

    @FXML
    private TextField PhotoTxfld;

    @FXML
    void NouveauHandle(ActionEvent event) {

    }

    @FXML
    void EnregistrerHandle(ActionEvent event) {
        User user = new User();
        user.setLogin(LoginTxfld.getText());
        user.setPassword(PasswordTxfld.getText());
        user.setNomComplet(NomCompletTclum.getText());
        user.setPhoto(PhotoTxfld.getText());
        user.setProfil(ProfilCbox.getValue());
        try{
            userDAO.addUser(user);
            File out = new File(dirName+file.getName());
            ImageIO.write(bim,"png",out);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion des utilisaturs");
            alert.setHeaderText("Ajout d'un utilisateur");
            alert.setContentText("Utilsateur ajouter" );
            alert.showAndWait();
            users.add(user);

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Gestion des utilisaturs");
            alert.setHeaderText("Ajout d'un utilisateur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }

    @FXML
    void ModifierHandle(ActionEvent event) {

    }

    @FXML
    void SupprimerHandle(ActionEvent event) {

    }
    File file;
    BufferedImage bim;
    String dirName = "D:\\MesProjetJava\\DossiersImageFx\\";

    @FXML
    void ParcourirHandle(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext= new FileChooser.ExtensionFilter("Image files","*.jpg", "*.png" ,"*.jpeg ");
        fc.getExtensionFilters().add(ext);
        file=fc.showOpenDialog(Main.stage);
        if (file!=null){
            bim = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bim, null);
            photoImgV.setImage(image);
            PhotoTxfld.setText(file.getName());
        }




    }
    ObservableList<User> users;
    UserDAO userDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        LoginTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getLogin()));
        NomCompletTclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getNomComplet()));
        PhotoTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getPhoto()));
        ProfilTclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getProfil().getLibelle()));
        userDAO = new UserDAO();
        try {
            users = FXCollections.observableArrayList(userDAO.findAll());
            ProfilDAO profilDAO = new ProfilDAO() ;
            ProfilCbox.setItems(FXCollections.observableArrayList(profilDAO.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AjouterUserTbview.setItems(users);



    }
}

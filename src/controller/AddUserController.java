package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
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
    private Button deleteBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button updateBtn;

    @FXML
    private ImageView photoImgV;


    @FXML
    private TableColumn<User, String> LoginTbclum;

    @FXML
    private TableColumn<User , ImageView> PhotoTbclum;

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
    private TableColumn<User , String> PasswordTclum;

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
        user.setNomComplet(NomPrenomsTxfld.getText());
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
        PasswordTclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getPassword()));
        NomCompletTclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getNomComplet()));
        PhotoTbclum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User,
                ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<User,
                    ImageView> uiv) {
                File f = new File(dirName+uiv.getValue().getPhoto());
                if (uiv.getValue().getPhoto().equals("")){
                    f = new File(dirName+"default.png");
                }
//                System.out.println(dirName+f.getName());
                ImageView imgV = null;
                try {
                    BufferedImage bimg = ImageIO.read(f);
                    Image image = SwingFXUtils.toFXImage(bimg, null);
                    imgV=new ImageView(image);
                    imgV.setFitHeight(50);
                    imgV.setFitWidth(50);

                }catch (Exception e){
                    e.printStackTrace();
                }
                return new SimpleObjectProperty<>(imgV);

            }
        });
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
        activerDesactiverBtn(false );
        AjouterUserTbview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                displayUser(AjouterUserTbview.getSelectionModel().getSelectedItem());

            }
        });
    }
    private void displayUser(User u)   {
        LoginTxfld.setText(u.getLogin());
        PasswordTxfld.setText(u.getPassword());
        NomPrenomsTxfld.setText(u.getNomComplet());
        if (u.getPhoto().equals("")){
            file=new File(dirName+"default.png");
        }else {
            file=new File(dirName+u.getPhoto());
        }


        try {
            bim = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bim, null);
            photoImgV.setImage(image);
            PhotoTxfld.setText(file.getName());
            ProfilCbox.setValue(u.getProfil());
            activerDesactiverBtn(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void activerDesactiverBtn(boolean active){
        saveBtn.setDisable(active);
        updateBtn.setDisable(!active);
        deleteBtn.setDisable(!active);

    }
}

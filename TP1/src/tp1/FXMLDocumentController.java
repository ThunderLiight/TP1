package tp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class FXMLDocumentController implements Initializable { 

    @FXML
    private ListView<Integer> lsvSource;
    @FXML
    private TextField txfDA;
    @FXML
    private TextField txfExamen1;
    @FXML
    private TextField txfExamen2;
    @FXML
    private TextField txfTP1;
    @FXML
    private TextField txfTP2;
    @FXML
    private ImageView imgCible;
    @FXML
    private GridPane gdpNotes;
    @FXML
    private ComboBox<String> cmbTrier;
    @FXML
    private GridPane gdpStats;

    int [][] tabSource;
    int COL_DA      = 0;
    int COL_EXAMEN1 = 1;
    int COL_EXAMEN2 = 2;
    int COL_TP1     = 3;
    int COL_TP2     = 4;
    int COL_TOTAL   = 5;
    int NB_LIG      = 25;
    int NB_COL      = 6;
    boolean modif   = false;
    boolean ajout   = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
        tabSource = lireFichier("notes.txt");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        garnirLsvSource(tabSource);
        
    }    

    @FXML
    private void lsvDragDetected(MouseEvent event) {
    	
        Dragboard db =lsvSource.startDragAndDrop(TransferMode.COPY);
	ClipboardContent cb = new ClipboardContent();
	cb.putString(lsvSource.getSelectionModel().getSelectedItem().toString());
	db.setContent(cb);

    }

    @FXML
    private void btnOKAction(ActionEvent event) {
        
        if(ajout){
            tabSource = Utilitaire.ajouter(tabSource, txfDA.getText(), txfExamen1.getText(),
                    txfExamen2.getText(), txfTP1.getText(), txfTP2.getText());
            
        }
        if(modif){
            tabSource = Utilitaire.modifier(tabSource, lsvSource.getSelectionModel().getSelectedIndex() , txfDA.getText(), txfExamen1.getText(),
                    txfExamen2.getText(), txfTP1.getText(), txfTP2.getText());
        }
        lsvSource.getItems().clear();
        garnirLsvSource(tabSource);
        viderTxf();
        txfEditable(false);
        ajout = false;
        modif=false;
    }

    @FXML
    private void btnAnnulerAction(ActionEvent event) {
    
        txfEditable(false);
        viderTxf();
        ajout = false;
        modif = false;
    }

    @FXML
    private void btnAjouterAction(ActionEvent event) {
        ajout = true;
        viderTxf();
        txfEditable(true);
    }

    @FXML
    private void btnSupprimerAction(ActionEvent event) {
        
        int indLig = lsvSource.getSelectionModel().getSelectedIndex();
        tabSource = Utilitaire.supprimer(indLig, tabSource);
        lsvSource.getItems().clear();
        garnirLsvSource(tabSource);
        viderTxf();
    }

    @FXML
    private void btnModifierAction(ActionEvent event) {
        modif = true;
        transformePromptEnTexte();
        txfEditable(true);
    }

    @FXML
    private void imgDragOver(DragEvent event) {
    
        event.acceptTransferModes(TransferMode.ANY);
        
    }

    @FXML
    private void imgDragDropped(DragEvent event) {
    
        int indLig = lsvSource.getSelectionModel().getSelectedIndex();
        tabSource = Utilitaire.supprimer(indLig, tabSource);
        lsvSource.getItems().clear();
        garnirLsvSource(tabSource);
        viderTxf();
    
    }

    @FXML
    private void cmbTrierAction(ActionEvent event) {
    }

    @FXML
    private void btnQuitterAction(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Sauvegarde");
        alert.setHeaderText("Désirez-vous sauvegarder (OK) ou quitter (Annuler) ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
        } 
        else {
            //platform.exit();
        }

    }

    public int[][] lireFichier(String nomFichier) throws FileNotFoundException, IOException {
        
        String ligne;
        BufferedReader objEntree = new BufferedReader(new FileReader(nomFichier));
        
        String[][] tabNotes = new String[NB_LIG][NB_COL];
        int pointeurLig = 0;
        int pointeurCol = 0;
        String chaine = "";
        
        do {
            
            ligne = objEntree.readLine();
            
            if (ligne != null) {
                
                //System.out.println(ligne);
                
                for(int i = 0; i < ligne.length(); i++){
                    
                    if(ligne.charAt(i) != ' '){
                        chaine += ligne.charAt(i);
                    }
                    
                    else if(ligne.charAt(i) == ' '){
                        tabNotes[pointeurLig][pointeurCol] = chaine;
                        chaine="";
                        pointeurCol ++;
                    }
                    
                    if (i == ligne.length()-1  || pointeurCol ==6){
                        
                        if(pointeurCol !=6)
                            tabNotes[pointeurLig][pointeurCol] = chaine;
                        
                        chaine = "";
                        pointeurLig ++;
                        pointeurCol = 0;
                    }
                }
                 
            }
            
        } while (ligne != null);
        
        //affTab(tabNotes);
        objEntree.close();
        
        return(convTabInt(tabNotes));
    }
     
    public static void affTab(String[][] tab2D) {

        int nbLig = tab2D.length;
        int nbCol = tab2D[0].length;

        for (int  i = 0; i < nbLig; i++){

            for (int j = 0; j < nbCol; j++) {

                    System.out.println(tab2D[i][j]);

            }

        }

    }

    @FXML
    private void lvsMouseClicked(MouseEvent event) {
        
        int indLig = lsvSource.getSelectionModel().getSelectedIndex();
        txfDA.setPromptText(String.valueOf(tabSource[indLig][COL_DA]));
        txfExamen1.setPromptText(String.valueOf(tabSource[indLig][COL_EXAMEN1]));
        txfExamen2.setPromptText(String.valueOf(tabSource[indLig][COL_EXAMEN2]));
        txfTP1.setPromptText(String.valueOf(tabSource[indLig][COL_TP1]));
        txfTP2.setPromptText(String.valueOf(tabSource[indLig][COL_TP2]));
        
    }

    public int [][] convTabInt(String[][] tabString){
    
        int[][] tabInt = new int[NB_LIG][NB_COL];

        for (int  i = 0; i < NB_LIG; i++){

            for(int j = 0; j < NB_COL; j++) {

                tabInt[i][j]=Integer.parseInt(tabString[i][j]);

            }

        }

        return tabInt;
    }

    public void garnirLsvSource(int[][] tabInt){
    
        for(int iLig = 0; iLig < tabInt.length; iLig++){

            lsvSource.getItems().add(tabSource[iLig][0]);
        
        }
    
    }
    
    public void viderTxf(){
        txfDA.setPromptText("");
        txfExamen1.setPromptText("");
        txfExamen2.setPromptText("");
        txfTP1.setPromptText("");
        txfTP2.setPromptText("");
        
        txfDA.clear();
        txfExamen1.clear();
        txfExamen2.clear();
        txfTP1.clear();
        txfTP2.clear();
    }
    
    public void txfEditable(boolean choix){
        txfDA.setEditable(choix);
        txfExamen1.setEditable(choix);
        txfExamen2.setEditable(choix);
        txfTP1.setEditable(choix);
        txfTP2.setEditable(choix);
    }
    
    public void transformePromptEnTexte(){
        
            txfDA.setText(txfDA.getPromptText());
            txfExamen1.setText(txfExamen1.getPromptText());
            txfExamen2.setText(txfExamen2.getPromptText());
            txfTP1.setText(txfTP1.getPromptText());
            txfTP2.setText(txfTP2.getPromptText());
        
    }
}
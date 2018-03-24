package tp1;

import javax.swing.JOptionPane;

public class Utilitaire {
    
    public static int[][] supprimer(int indLig, int[][] tabInt1){
        
        int nbLig1 = tabInt1.length;
        int nbCol1 = tabInt1[0].length;
        int[][] tabInt2 = new int[nbLig1-1][nbCol1];
        
        for(int i = 0; i < nbLig1; i++){
            
            if(i < indLig){
            
                for(int j = 0; j < nbCol1; j++){

                    tabInt2[i][j]= tabInt1[i][j];

                }
            }
            if(i > indLig){
                
                for(int j = 0; j < nbCol1; j++){

                    tabInt2[i-1][j]= tabInt1[i][j];

                }
            }
        }
        return tabInt2;
    }
    
    public static int[][] ajouter(int[][] tabInt1){
    
        int nbLig1 = tabInt1.length;
        int nbCol1 = tabInt1[0].length;
        if(nbLig1 < 25){
            int[][] tabInt2 = new int[nbLig1+1][nbCol1];

            tabInt2 = tabInt1;
            
            //tabInt2[nbLig1+1][0] = Integer.parseInt(txfDA.)


            return tabInt2;
        }
        else
            JOptionPane.showMessageDialog(null,"Le maximum de 25 élèves est déjà atteint.");
            return tabInt1;
    }
    public static void modifier(){
    }
    public static void moyenneEval(){
    }
    public static void minEval(){
    }
    public static void maxEval(){
    }
}

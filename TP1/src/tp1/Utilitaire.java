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
    
    public static int[][] ajouter(int[][] tabInt1, String noDA, String noExamen1, 
            String noExamen2, String noTP1, String noTP2){
        
        //PONDÉRATION
        final double POND_EX1 = 0.25;
        final double POND_EX2 = 0.3;
        final double POND_TP1 = 0.2;
        final double POND_TP2 = 0.25;
        
        int nbLig1 = tabInt1.length;
        int nbCol1 = tabInt1[0].length;
        int[][] tabInt2 = new int[nbLig1+1][nbCol1];
        int nbLig2 = tabInt2.length;
        int nbCol2 = tabInt2[0].length;
        double total;
        
        if(nbLig1 < 25){
            
            for(int iLig = 0; iLig < nbLig1; iLig++){
                
                for(int iCol = 0; iCol < nbCol1; iCol++){
                    
                    tabInt2[iLig][iCol] = tabInt1[iLig][iCol];
                    
                }
            
            }
            
            tabInt2[nbLig2-1][0] = Integer.parseInt(noDA);
            tabInt2[nbLig2-1][1] = Integer.parseInt(noExamen1);
            tabInt2[nbLig2-1][2] = Integer.parseInt(noExamen2);
            tabInt2[nbLig2-1][3] = Integer.parseInt(noTP1);
            tabInt2[nbLig2-1][4] = Integer.parseInt(noTP2);
            total = (POND_EX1*Double.parseDouble(noExamen1)) + 
                    (POND_EX2*Double.parseDouble(noExamen2)) + 
                    (POND_TP1*Double.parseDouble(noTP1)) + 
                    (POND_TP2*Double.parseDouble(noTP2));
            tabInt2[nbLig2-1][5] = (int)total;

        }
        
        else{
            JOptionPane.showMessageDialog(null,"Le maximum de 25 élèves est déjà atteint.");
            tabInt2 = tabInt1;
        }
        
        return tabInt2;
    }
    public static int[][] modifier(int[][] tabInt1, int indice, String noDA, String noExamen1, 
            String noExamen2, String noTP1, String noTP2){
        
        //PONDÉRATION
        final double POND_EX1 = 0.25;
        final double POND_EX2 = 0.3;
        final double POND_TP1 = 0.2;
        final double POND_TP2 = 0.25;
        
        int [][] tabInt2 = tabInt1;
        double total;
        
        tabInt2[indice][0]= Integer.parseInt(noDA);
        tabInt2[indice][1]= Integer.parseInt(noExamen1);
        tabInt2[indice][2]= Integer.parseInt(noExamen2);
        tabInt2[indice][3]= Integer.parseInt(noTP1);
        tabInt2[indice][4]= Integer.parseInt(noTP2);
        
        total = (POND_EX1*Double.parseDouble(noExamen1)) + 
                (POND_EX2*Double.parseDouble(noExamen2)) + 
                (POND_TP1*Double.parseDouble(noTP1)) + 
                (POND_TP2*Double.parseDouble(noTP2));
        
        tabInt2[indice][5] = (int)total;
        
        return tabInt2;
    }
    public static void moyenneEval(){
    }
    public static void minEval(){
    }
    public static void maxEval(){
    }

}

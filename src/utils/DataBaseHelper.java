package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBaseHelper {
    private Connection cnx;
    PreparedStatement pstmt;
    private void connexion() throws Exception {
        try {
            if (cnx==null || cnx.isClosed()){
                String url="jdbc:mysql://localhost:3306/coursjavafx";
                String user="jabbar",password="Fooly@1251";
                Class.forName("com.mysql.cj.jdbc.Driver");
                cnx= DriverManager.getConnection(url,user,password);

            }

        } catch (Exception e) {
            throw e;
        }

    }
    //Preparation de la requettes;
    public void myPrepardQuery(String sql) throws Exception {
        try {
            connexion();
            pstmt = cnx.prepareStatement(sql);
        }catch (Exception e){
            throw e;
        }
    }
    //La requete d'ajout dans la base de donnees peut importe l'objet ici elle sera inserer dans la base de donnees

    public void addParameters(Object[] parametres) throws Exception {
        try {
            int i;
            for (i=0 ; i<parametres.length; i++){
                pstmt.setObject(i+1,parametres[i]);
            }
        }catch (Exception e){
            throw e;
        }


    }
    public ResultSet myExecuteQuery() throws Exception{
        try {
            pstmt.executeQuery();
            return pstmt.executeQuery();

        }catch (Exception e){
            throw e;
        }
    }
    public int myExecuteUpdate()  throws Exception{
        try {
            pstmt.executeUpdate();
            return pstmt.executeUpdate();

        }catch (Exception e){
            throw e;
        }
    }
    //une fonction qui va me permettre de faire la selection;
    // soit je peux faire un select dans la table ou bien par critere
    public ResultSet select(String tableName , String critere,Object[] parametres) throws Exception {
        try {
            String sql ="SELECT * FROM "+tableName;
            if (!critere.equals("")){
                sql+=" WHERE "+critere;
            }
            try {
                myPrepardQuery(sql);
                if (parametres.length!=0){
                    addParameters(parametres);
                }
                return myExecuteQuery();

            }catch (Exception e){
                throw e;
            }

        }catch (Exception e){
            throw e;
        }
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }
}

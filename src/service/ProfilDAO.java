package service;

import model.Profil;
import model.User;
import utils.DataBaseHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfilDAO {
    private DataBaseHelper dbc;
    public ProfilDAO(){
        dbc = new DataBaseHelper();
    }

    public List<Profil> findAll() throws Exception{
        List<Profil> profils=new ArrayList<>();
        try {
            String sql="SELECT * FROM  profil";
            dbc.myPrepardQuery(sql);
            ResultSet resultat=dbc.myExecuteQuery();
            while (resultat.next()){

                Profil profil = new Profil();
                profil.setId(resultat.getInt(1));
                profil.setLibelle(resultat.getString(2));
                profils.add(profil);
            }
            resultat.close();

        }catch (Exception e){
            throw e;

        }
        return profils;

    }


}

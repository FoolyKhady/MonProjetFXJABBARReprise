package service;

import model.Profil;
import model.User;
import utils.DataBaseHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DataBaseHelper dbc;
    public UserDAO(){
        dbc = new DataBaseHelper();
    }
    public User getUser(String login , String password) throws Exception{
        String sql="SELECT * FROM utilisateur u , profil p WHERE u.profil_id=p.id AND login=? AND pwd=?";
        User user = null;
        try {
            dbc.myPrepardQuery(sql);
            Object[] parm={login,password};
            dbc.addParameters(parm);
            ResultSet resultat= dbc.myExecuteQuery();
            if (resultat.next()){
                user = new User();
                user.setId(resultat.getInt(1));
                user.setLogin(resultat.getString(2));
                user.setPassword(resultat.getString(3));
                user.setNomComplet(resultat.getString(4));
                user.setPhoto(resultat.getString(5));
                Profil profil=new Profil();
                profil.setId(resultat.getInt(6));
                profil.setLibelle(resultat.getString(7));
                user.setProfil(profil);
            }
            resultat.close();
            return user;

        }catch (Exception e){
            throw e;
        }

    }
    public List<User> findAll() throws Exception{
        List<User> users=new ArrayList<>();
        try {
            String sql="SELECT * FROM utilisateur u , profil p WHERE u.profil_id=p.id";
            dbc.myPrepardQuery(sql);
            ResultSet resultat=dbc.myExecuteQuery();
            while (resultat.next()){
                User user = new User();
                user.setId(resultat.getInt(1));
                user.setLogin(resultat.getString(2));
                user.setPassword(resultat.getString(3));
                user.setNomComplet(resultat.getString(4));
                user.setPhoto(resultat.getString(5));
                Profil profil = new Profil();
                profil.setId(resultat.getInt(6));
                profil.setLibelle(resultat.getString(8));
                user.setProfil(profil);
                users.add(user);
            }
            resultat.close();

        }catch (Exception e){
            throw e;

        }
        return users;

    }
    public void addUser(User user){
        String sql="INSERT INTO utilisateur VALUES(null, ?,?,?,?,? )";
        try {
            dbc.myPrepardQuery(sql);
            Object parm[]={
                    user.getLogin(),
                    user.getPassword(),
                    user.getNomComplet(),
                    user.getPhoto(),
                    user.getProfil().getId(),
            };
            dbc.addParameters(parm);
            dbc.myExecuteUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

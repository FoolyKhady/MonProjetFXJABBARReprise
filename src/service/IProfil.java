package service;

import model.Profil;

import java.util.List;

public interface IProfil {
    public List<Profil> findAll() throws Exception;
}

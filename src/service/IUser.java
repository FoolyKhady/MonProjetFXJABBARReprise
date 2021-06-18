package service;

import model.User;

import java.util.List;

public interface IUser {
    public User getUser(String login , String password) throws Exception;
    public List<User> findAll() throws Exception;
    public void addUser(User user, int type);
    public User findUserByLogin(String login) throws Exception;
}

package com.clintariac.components.userList;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import com.clintariac.components.userList.user.UserModel;

public class UsersListModel {

    private ListModel<UserModel> users;

    public UsersListModel(List<UserModel> users) {
        DefaultListModel<UserModel> user = new DefaultListModel<UserModel>();
        user.addAll(users);
        this.users = user;
    }

    /**
     * @return ListModel<UserModel>
     */
    public ListModel<UserModel> getUsers() {
        return users;
    }

    /**
     * @param users
     */
    public void setUsers(ListModel<UserModel> users) {
        this.users = users;
    }
}

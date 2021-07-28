package com.clintariac.components.userList.user;

public class UserModel {

    private String fullName;
    private String userId;

    public UserModel(String fullName, String userId) {

        this.fullName = fullName;
        this.userId = userId;
    }

    /**
     * @return String
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

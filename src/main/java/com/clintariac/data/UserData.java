package com.clintariac.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserData {

    public final String firstName;
    public final String lastName;
    public final String id;
    public final String email;
    public final String phone;
    private final List<MessageData> chat;

    public List<MessageData> getChat() {
        return chat.stream().collect(Collectors.toList());
    }

    public UserData(String firstName, String lastName, String id, String email, String phone,
            List<MessageData> chat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.chat = chat;
    }

    public UserData(String firstName, String lastName, String id, String email, String phone) {
        this(firstName, lastName, id, email, phone, new ArrayList<MessageData>());
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "UserData [email=" + email + ", firstName=" + firstName + ", id=" + id
                + ", lastName="
                + lastName + ", phone=" + phone + "]";
    }
}

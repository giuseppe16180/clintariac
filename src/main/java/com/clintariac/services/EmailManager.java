package com.clintariac.services;

import java.util.List;
import java.util.function.Consumer;

import com.clintariac.data.EmailData;

public interface EmailManager {

    /**
     * @param email
     */
    public boolean send(EmailData email);

    /**
     * @return List<EmailData>
     */
    public List<EmailData> pull();

    /**
     * @param onException
     */
    public void addOnException(Consumer<Exception> onException);

}

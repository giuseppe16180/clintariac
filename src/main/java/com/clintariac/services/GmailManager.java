package com.clintariac.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import com.clintariac.data.EmailData;
import com.clintariac.services.utils.SingletonException;

public class GmailManager implements EmailManager {

    private static boolean isInstantiated = false;

    private String user;
    private String password;
    private String sendingHost;
    private int sendingPort;
    private String address;

    private String receivingHost;

    Session sendSession;
    Session pullSession;

    private Consumer<Exception> onException;

    /**
     * Costruttore di GmailManager.
     * 
     * <p>
     * Permette di gestire il protocollo di invio di email (SMTP) e il protocolle
     * per la ricezione di email (IMAP).
     * </p>
     * 
     * <p>
     * Si tratta di un singleton, in quanto gestendo i protocolli, è necessario che
     * non possano esisterne più istanze. In caso di tentata istanziazione multipla,
     * viene lanciata un'eccezione.
     * </p>
     * 
     */
    public GmailManager(String username, String password) {

        if (GmailManager.isInstantiated == true) {
            throw new SingletonException();
        } else {

            this.user = username;
            this.password = password;
            this.address = username + "@gmail.com";
            this.sendingHost = "smtp.gmail.com";
            this.receivingHost = "imap.gmail.com";
            this.sendingPort = 465;

            Properties sendProps;

            sendProps = new Properties();

            sendProps.put("mail.smtp.host", this.sendingHost);
            sendProps.put("mail.smtp.port", String.valueOf(this.sendingPort));
            sendProps.put("mail.smtp.user", this.user);
            sendProps.put("mail.smtp.password", this.password);
            sendProps.put("mail.smtp.auth", "true");

            sendSession = Session.getDefaultInstance(sendProps);

            Properties pullProps;

            pullProps = System.getProperties();
            pullProps.setProperty("mail.store.protocol", "imaps");

            pullSession = Session.getDefaultInstance(pullProps, null);

            isInstantiated = true;
        }
    }

    /**
     * <p>
     * Metodo per l'invio di un'email ricevuta a parametro di tipo
     * {@code EmailData}.
     * </p>
     * 
     * <p>
     * Viene stabilito il mittente, il destinatario e l'oggetto dall'email. In
     * seguito viene stabilita la formattazione del messaggio dell'email, cioè
     * {@code "text/html; charset=utf-8"}.
     * </p>
     * 
     * <p>
     * Viene utilizzato il protocollo SMPT che stabilisce la connessione, e
     * successivamente procede con l'invio dall'email.
     * </p>
     * 
     * @param email da inviare
     */
    @Override
    public boolean send(EmailData email) {

        Message simpleMessage = new MimeMessage(sendSession);

        try {

            InternetAddress fromAddress = new InternetAddress(this.address);
            InternetAddress toAddress = new InternetAddress(email.address);

            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(RecipientType.TO, toAddress);
            simpleMessage.setSubject(email.subject);
            simpleMessage.setContent(email.message, "text/html; charset=utf-8");

            Transport transport = sendSession.getTransport("smtps");

            transport.connect(this.sendingHost, this.sendingPort, this.user, this.password);
            transport.sendMessage(simpleMessage, simpleMessage.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            onException.accept(e);
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Metodo per la lettura di email della casella postale, restituendo quindi una
     * lista di email non lette.
     * </p>
     * 
     * <p>
     * Attraverso il protocollo IMAP, si stabilisce la connessione per la lettura
     * dell'email.
     * </p>
     * 
     * <p>
     * Le email vengono memorizzate in una lista di {@code EmailData}, verrà
     * restutuita una volta lette tutte le email con {@code Flag.SEEN} falso.
     * </p>
     * 
     * <p>
     * Verrà lanciata un'eccezione nel caso in cui non è possibile stabilire la
     * connessione o nel caso in cui l'email non ha dei campi validi.
     * </p>
     * 
     * @return List<EmailData>
     */
    @Override
    public List<EmailData> pull() {

        List<EmailData> result = new ArrayList<>();

        try {

            Store store = pullSession.getStore("imaps");
            store.connect(this.receivingHost, this.user, this.password);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            result = Stream.of(folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)))

                    .map(message -> {

                        String address = null;
                        String subject = null;
                        String body = null;

                        try {

                            address = ((InternetAddress) message.getFrom()[0]).getAddress().toString();

                            subject = message.getSubject();

                            if (message.getContentType().startsWith("multipart/ALTERNATIVE")) {
                                MimeMultipart mime = (MimeMultipart) message.getContent();
                                body = mime.getBodyPart(0).getContent().toString().trim();

                            } else if (message.getContentType().startsWith("TEXT/PLAIN")) {
                                body = message.getContent().toString();

                            } else {
                                subject = "parsing error";
                            }

                            message.setFlag(Flags.Flag.SEEN, true);

                        } catch (MessagingException | IOException e) {
                            e.printStackTrace();
                        }
                        return new EmailData(address, subject, body);
                    }).collect(Collectors.toList());

            folder.close(true);
            store.close();

        } catch (MessagingException e) {
            e.printStackTrace();
            onException.accept(e);
        }
        return result;
    }

    /**
     * @param onException
     */
    public void addOnException(Consumer<Exception> onException) {
        this.onException = onException;
    }
}

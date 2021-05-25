package com.clintariac.services.config;

public interface StandardEmails {

        final String NAME = "Dott. Rossi";

        final String GREETINGS = String
                        .format("<p>Cordiali saluti, la segreteria dell'ambulatorio %s.</p>", NAME);

        final String INSTRUCTION = String.format(
                        "<h3>Le ricordiamo che per utilizzare il servizio deve inviare un email all'indirizzo <a href=mailto:%s>%s</a> seguendo le istruzioni qui riportate:</h3><ul><li><b>Nuova richiesta</b>: per richiedere un nuovo appuntamento inserisca come oggetto <code>PRENOTAZIONE</code>. Le sar&agrave; possibile comunicare il motivo della visita o delle eventuali esigenze particolari utilizzando il corpo del messaggio, in alternativa, per richiedere un nuovo appuntamento clicchi qui <a href=mailto:%s?subject=prenotazione>NUOVA PRENOTAZIONE</a></li><li><b>Conferma appuntamento</b>: per confermare una proposta di appuntamento inserisca come oggetto <code>CONFERMA</code> e come corpo del messaggio il <code>CODICE_TICKET</code> relativo alla sua richiesta.</li><li><b>Cancella appuntamento</b>: per cancellare un appuntamento (sia confermato che in attesa di conferma) inserisca come oggetto <code>ANNULLA</code> e come corpo del messaggio il <code>CODICE_TICKET</code> relativo alla sua richiesta.</li></ul>",
                        Credentials.email, Credentials.email, Credentials.email);

        final String WELCOME =
                        String.format("<h2>Benevenuto nel servizio Clintariac dell'ambulatorio del %s!</h2><h3>Tramite Clinariac le sar&agrave; possibile richiedere comodamente un appuntamento presso il nostro ambulatorio!</h3>%s%s",
                                        NAME, INSTRUCTION, GREETINGS);


        final String UPDATE =
                        "<h3>Gentile paziente, le sue informazioni sono state aggiornate.</h3>"
                                        + INSTRUCTION + GREETINGS;


        final String ERROR =
                        "<h3>Gentile paziente, la sua richiesta è mal formattata, pertanto non pu&ograve; essere processata, la preghiamo di riprovare.</h3>"
                                        + INSTRUCTION + GREETINGS;

        final String NOT_VALID =
                        "<h3>Gentile paziente, il suo codice ticket della sua precedente interazione non &egrave; corretto, pertanto la sua richiesta non pu&ograve; essere processata, la preghiamo di riprovare.</h3>"
                                        + INSTRUCTION + GREETINGS;
        final String NOT_ACCEPTED =
                        "<h3>Gentile paziente, ci dispiace comunicarle che la sua richiesta &egrave; stata cancellata. La preghiamo, eventualmente, di richiedere un nuovo appuntamento.</h3><h3>Per <b>richiedere</b> un nuovo appuntamento clicchi qui <a href=mailto:%s?subject=Prenotazione>NUOVA PRENOTAZIONE</a>."
                                        + GREETINGS;

        final String UNREGISTERED =
                        "<h3>Gentile paziente, le ricordiamo che il servizio è riservato a gli utenti registrati, pertanto le chiediamo di effettuare la registrazione presso la nostra segreteria.</h3>"
                                        + GREETINGS;

        static String deleteMessage(String ticketId) {
                return String.format(
                                "<h3>Gentile paziente, il suo appuntamento con codice ticket: <code>%s</code> è stato cancellato.</h3>%s",
                                ticketId, GREETINGS);
        }


        static String expireBooked(String ticketId) {
                return String.format(
                                "<h3>Gentile paziente, ci dispiace ma il tempo a disposizione per confermare il suo appuntamento con codice ticket <code>%s</code> è scaduto. La preghiamo, eventualmente, di richiedere un nuovo appuntamento.</h3><h3>Per <b>richiedere</b> un nuovo appuntamento clicchi qui <a href=mailto:%s?subject=prenotazione>NUOVA PRENOTAZIONE</a>.</h3>%s",
                                ticketId,
                                Credentials.email,
                                GREETINGS);
        }

        static String removeMessage(String ticketId) {

                return String.format(
                                "<h3>Gentile paziente, ci dispiace comunicarle che il suo appuntamento con codice ticket <code>%s</code> &egrave; stato cancellato. La preghiamo, eventualmente, di richiedere un nuovo appuntamento.</h3><h3>Per <b>richiedere</b> un nuovo appuntamento clicchi qui <a href=mailto:%s?subject=Prenotazione>NUOVA PRENOTAZIONE</a>.%s",
                                ticketId, Credentials.email, GREETINGS);
        }

        static String ticketMessage(String date, String time, String ticketId) {
                return String.format(
                                "<h3>Gentile paziente, le comunichiamo che in seguito alla sua richiesta le proponiamo un appuntamento il giorno %s alle ore %s.<h4>Il codice ticket relativo alla sua richiesta &egrave;: <code>%s</code></h4><h3>Per <b>confermare</b> l'appuntamento clicchi qui <a href=mailto:%s?subject=conferma&body=%s>CONFERMA</a>.<h3>Per <b>cancellare</b> l'appuntamento clicchi qui <a href=mailto:%s?subject=annulla&body=%s>ANNULLA</a>.<h3>%s",
                                date, time, ticketId, Credentials.email,
                                ticketId, Credentials.email, ticketId, GREETINGS);
        }

        static String confirmMessage(String date, String time, String ticketId) {
                return String.format(
                                "<h3>Gentile paziente, le comunichiamo che il suo appuntamento &egrave; stato confermato per il giorno %s alle ore %s.<h4>Il codice ticket relativo a questa richiesta &egrave;: <code>%s</code><h3>Per <b>cancellare</b> l'appuntamento clicchi qui <a href=mailto:%s?subject=annulla&body=%s>ANNULLA</a>.<h3>%s",
                                date, time, ticketId, Credentials.email, ticketId, GREETINGS);
        }
}

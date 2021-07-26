# Da fare 

## Agenda

- [X] Non eliminare gli appuntamenti passati
  - [x] Individuare dove venivano effettuate le eliminazioni
  - [x] alterare lo stato dei ticket "eliminati"
- [x] Creare una vista in ordine cronologico di tutti gli appuntamenti a partire dall'ora corrente
- [x] Abilitare le giornate passate nel calendario
- [x] Inserire il seletore del calendario nella colonna degli appuntamenti
- [x] Inserire un bottone per tornare alla vista cronologica
- [x] Le prenotazioni scadute, o eliminate compaiono nella lista appuntamenti, solo in modo diverso
- [ ] L'appuntamento non confermato deve esserre riproponibile (?)
- [x] In caso di eliminazione alterare la grafica del ticket nella lista
- [ ] In caso di eliminazione proporre un nuovo appuntamneto
- [ ] Se l'appuntamento non viene confermato elimnarlo in automatico, mandare il messaggio
- [ ] Permettere di modificare un appuntamento


## Pazienti

- [x] Inserire un campo di ricerca
- [x] Inserire una lista con tutti i pazienti
  - [x] model
  - [x] view
  - [x] controller 
- [x] Mostrare di default la lista con tutti i pazienti
- [x] Rivedere l'aggiuta di nuovi pazienti e la modifica di uno esistente (idealmente portarlo su un'altra finestra)
- [x] Selezionado un paziente si apre la sua chat
- [ ] messaggio in caso di email errata nel momento di registrazione????

## Lista ticket in attesa

- [x] Rendere la lista dei ticket in attesa una lista dei nuovi messaggi
- [x] Al click di un elemento di questa lista caricare la chat con quel paziente
- [x] In caso di più messaggi decidere il comportamento

## Gestione tickets

- [x] Details deve ricevere l'id di un utente, non più quello di un ticket
- [x] Creare una vista chat, che mostri tutti i messaggi scambiati con il paziente selezionato
- [x] Inserire la possibilità di inviare dei messaggi al paziente
- [x] All'invio di una risposta tutti i messaggi in coda come non letti risultano gestiti
- [x] Inserire la possibilità di inviare dei messaggi di testo liberi ai pazienti
- [x] I messaggi inviati in automatico dal sistema compaiono nella chat


## Logica di business

- [x] Separare i ticket dai messaggi
- [x] I nuovi tricket nascono dalla segreteria, non più dalla ricezione di un messaggio di nuovo appuntamento (nzomma)
- [ ] Modificare gli stati possibili per i ticket (non più in attesa di conferma, aggiungere "cancellato", "scaduto")
- [ ] aggiungere comunicazione all'email manager


## Vista chat

- [x] Dividere messaggi da ticket
- [ ] Rivedere lo stato dei ticket
- [x] Realizzare view
- [x] Realizzare model (deve ricevere la lista dei messaggi per un utente)
- [x] DataManager - predisporre il necessario per i messaggi
  - [x] aggiungere il messaggio quando si inviano le email
  - [x] predisporre metodo per ottenere i messaggi di un dato utente 
- [x] Realizzare controller

**NOTA** Per ora teniamo tutto in memoria centrale, poi si vede se è il caso di fare altro

- [ ] Riprogettare email handler   
- [ ] Caricare la data dell'appuntamento fissato in details quando e` presente un appuntamento fissato
- [x] l'eliminazione quando siamo in vista pazienti non funziona (solleva eccezione)
- [ ] aggiornare i testi delle email
- [ ] i ticket cancellati non devono essere eliminabili
- [ ] se un booked scade portarlo in stato waiting
- [ ] se un ticket viene riproposto mandare un messaggio diverso. (Se lo stato prima dello spostamento non era awaiting)
# Da fare 

## Agenda

- [X] Non eliminare gli appuntamenti passati
  - [ ] Individuare dove venivano effettuate le eliminazioni
  - [ ] alterare lo stato dei ticket "eliminati"
- [ ] Creare una vista in ordine cronologico di tutti gli appuntamenti a partire dall'ora corrente
- [x] Abilitare le giornate passate nel calendario
- [x] Inserire il seletore del calendario nella colonna degli appuntamenti
- [ ] Inserire un bottone per tornare alla vista cronologica
- [ ] Le prenotazioni scadute, o eliminate compaiono nella lista appuntamenti, solo in modo diverso
- [ ] L'appuntamento non confermato deve esserre riproponibile (?)
- [ ] In caso di eliminazione alterare la grafica del ticket nella lista
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
- [ ] Rivedere l'aggiuta di nuovi pazienti e la modifica di uno esistente (idealmente portarlo su un'altra finestra)
- [x] Selezionado un paziente si apre la sua chat
- [ ] messaggio in caso di email errata nel momento di registrazione????

## Lista ticket in attesa

- [ ] Rendere la lista dei ticket in attesa una lista dei nuovi messaggi
- [ ] Al click di un elemento di questa lista caricare la chat con quel paziente
- [ ] In caso di più messaggi decidere il comportamento

## Gestione tickets

- [ ] Details deve ricevere l'id di un utente, non più quello di un ticket
- [x] Creare una vista chat, che mostri tutti i messaggi scambiati con il paziente selezionato
- [x] Inserire la possibilità di inviare dei messaggi al paziente
- [ ] All'invio di una risposta tutti i messaggi in coda come non letti risultano gestiti
- [x] Inserire la possibilità di inviare dei messaggi di testo liberi ai pazienti
- [ ] Va mantenuta la possibilità di fissare un appuntamento come ora, che in automatico invia dei messaggi, comportamento come prima
- [ ] I messaggi inviati in automatico dal sistema compaiono nella chat
- [ ] 

## Logica di business

- [ ] Separare i ticket dai messaggi
- [ ] I nuovi tricket nascono dalla segreteria, non più dalla ricezione di un messaggio di nuovo appuntamento
- [ ] Modificare gli stati possibili per i ticket (non più in attesa di conferma, aggiungere "cancellato", "scaduto")
- [ ] aggiungere comunicazione all'email manager


## Vista chat

- [ ] Dividere messaggi da ticket
- [ ] Rivedere lo stato dei ticket
- [ ] Realizzare view
- [ ] Realizzare model (deve ricevere la lista dei messaggi per un utente)
- [x] DataManager - predisporre il necessario per i messaggi
  - [x] aggiungere il messaggio quando si inviano le email
  - [ ] predisporre metodo per ottenere i messaggi di un dato utente 
- [x] Realizzare controller

**NOTA** Per ora teniamo tutto in memoria centrale, poi si vede se è il caso di fare altro

- [ ] Riprogettare email handler   
- [ ] Caricare la data dell'appuntamento fissato in details quando e` presente un appuntamento fissato
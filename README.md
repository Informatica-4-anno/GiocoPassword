# GiocoPassword
Progetto didattico per l'utilizzo di Maven e JavaFX sottto Eclipse. Il computer deve scegliere casualmente una parola italiana di 5 6 o 7 lettere, L’utente deve tentare di indovinare la parola entro un numero massimo di tentativi, definito nel programma in modo facilmente modificabile.
Per ogni parola inserita il computer indicherà quali lettere sono presenti al posto giusto, quali presenti al posto sbagliato e quali non presenti.

Il progetto è stato realizzato secondo pattern MVC e fornisce una serie di spunti e di esempi utili ai fini didattici e di apprendimento delle intefacce grafiche in generale e di JavaFX in particolare.


## Indicazioni sul Model
Per questo progetto MVC è si è suddiviso il lavoro in più classi, ciascuna con una responsabilità precisa, perché MVC funziona meglio quando i compiti sono separati chiaramente.
L'organizzazione è la seguente:
GiocoPassword: classe principale del Model, gestisce lo stato della partita.
Dizionario: carica dal file le parole italiane e consente i controlli di validità.
EsitoTentativo: rappresenta il risultato di un tentativo, in modo che il Controller possa leggerlo e mostrarlo nella View.
Il Controller dovrà poter chiedere al Model di iniziare una nuova partita, inviare un tentativo, leggere il numero di tentativi rimasti, sapere se la partita è terminata e ottenere l’esito dell’ultimo inserimento.

## Interfaccia
Per facilitare l’integrazione con View e Controller, il Model è stato progettato con una su metodi che descrivono azioni o richieste di informazione.
```java
public class  GiocoPassword   {
    void caricaDizionario(String nomeFile) throws IOException;
    void nuovaPartita(Livello livello, int numeroMassimoTentativi);
    boolean isPartitaInCorso();
    boolean isPartitaVinta();
    boolean isPartitaPersa();
    boolean isPartitaTerminata();
    int getLunghezzaParola();
    int getTentativiMassimi();
    int getTentativiEffettuati();
    int getTentativiRimanenti();
    boolean isParolaValida(String parola);
    EsitoTentativo giocaTentativo(String parola);
    String getParolaSegreta();   // solo per test o fine partita
}
```

## Significato dei metodi
### caricaDizionario(...) 
   legge il file di testo e memorizza solo le parole valide, così il Model può poi filtrare quelle da 5, 6 o 7 lettere.
### nuovaPartita(...) 
   inizializza tutto lo stato della partita: livello scelto, numero massimo di tentativi, parola segreta casuale, contatore dei tentativi ed eventuale cronologia dei risultati.
### isParolaValida(...) 
   serve al Controller per verificare rapidamente se una parola inserita dall’utente è accettabile prima o durante il tentativo; in questo modo la View potrà mostrare un messaggio chiaro senza duplicare la logica.
### giocaTentativo(...) 
   è il metodo più importante: riceve la parola proposta dall’utente, controlla validità e lunghezza, aggiorna lo stato della partita e restituisce un oggetto con il risultato del confronto tra parola inserita e parola segreta.

I metodi isPartitaVinta(), isPartitaPersa() e isPartitaTerminata() permettono al Controller di capire cosa fare dopo ogni mossa, ad esempio bloccare l’input o mostrare un messaggio finale nella View.
I metodi getTentativiRimanenti() e simili forniscono dati già pronti da visualizzare, riducendo il lavoro del Controller.

## Oggetto EsitoTentativo
Per evitare che il metodo giocaTentativo(...) restituisca solo una stringa difficile da gestire, è consigliato usare una classe dedicata che rappresenti in modo strutturato il risultato del tentativo, perché questo rende più semplice l’uso nella futura interfaccia grafica.
Una possibile struttura è:

```java
public class EsitoTentativo {
    private String parolaInserita;
    private List<StatoLettera> stati; // ArrayLista con lo stato di ogni singola lettera 
    private boolean tentativoValido;
    private String messaggio;
    private boolean parolaIndovinata;
    // costruttori, getter, setter se necessari
}
```

dove StatoLettera può essere un enum del tipo:

```java
public enum StatoLettera {
    CORRETTA,
    PRESENTE,
    ASSENTE
}
```
o semplicemente una stringa che contiene una delle tre voci di stato

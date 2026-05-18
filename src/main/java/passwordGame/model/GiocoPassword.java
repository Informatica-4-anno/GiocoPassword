package passwordGame.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import passwordGame.Main;

public class GiocoPassword {
	private int numeroMassimoTentativi;
	private int livello;
    private int tentativiEffettuati;
    private int lunghezzaParola;
    private String parolaSegreta;
    private List<String>words=new ArrayList<String>();
    private final String fileParole="ParoleItaliane.txt";
    
	private void caricaDizionario(String nomeFile) throws IOException {
		BufferedReader bf=
    			new BufferedReader(new FileReader(nomeFile));
    	String parola;
    	while ((parola=bf.readLine())!=null) {
    		if (parola.length()==lunghezzaParola) {
    			words.add(parola.toUpperCase());
    		}
    	}
    	bf.close();
    }
	
    public void nuovaPartita(int livello, int numeroMassimoTentativi) {
	
    	this.numeroMassimoTentativi=numeroMassimoTentativi;
		this.livello=livello;
		lunghezzaParola=4+livello;
		try {
			caricaDizionario(fileParole);
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
		}
		Random rn=new Random();
		parolaSegreta=words.get(rn.nextInt(words.size()));
		Main.dprint(parolaSegreta);
	}

    public boolean isPartitaInCorso() {
    	return true;
    }
    
    public boolean isPartitaVinta() {
    	return true;
    }
    
    public boolean isPartitaPersa() {
    	return true;
    }
    
    public boolean isPartitaTerminata() {
    	return false;
    }
    
    private boolean isParolaValida(String parola) {
    	for (String s:words) {
    		if (s.equalsIgnoreCase(parola)) return true;
    	}
    	return false;
    }
   
    public int getTentativiMassimi() {
		return numeroMassimoTentativi;
	}
    
	
	public int getTentativiEffettuati() {
		return tentativiEffettuati;
	}
	
	public int getTentativiRimanaenti() {
		return numeroMassimoTentativi-tentativiEffettuati;
	}
	
	public int getLunghezzaParola() {
		return lunghezzaParola;
	}
	
	public String getParolaSegreta() {
		return parolaSegreta;
	}
	
	
	public EsitoTentativo giocaTentativo(String parola) {
		boolean parolaValida=false;
		boolean parolaIndovinata=false;
		int lungParola = parola.length();
		List<StatoLettera> stati=new ArrayList<>();
		for (int i=0; i<lungParola; i++) {
			stati.add(StatoLettera.ASSENTE);
		}
		boolean[] considerata = new boolean[lungParola];
		
		if ((parolaValida=isParolaValida(parola))) {
			// 1 - verifica parola
			if (!(parolaIndovinata=parola.equalsIgnoreCase(parolaSegreta))) { 
				// 2 - verifica giusta al posto giusto.
				for (int i=0; i<lungParola; i++) {
					if (parola.charAt(i)==parolaSegreta.charAt(i)) {
						stati.set(i, StatoLettera.CORRETTA);
						considerata[i]=true;
					}
				}
				
				// 3 - verifica giusto al posto sbagliato.
				for (int i=0; i<lungParola; i++) {
					if (stati.get(i)!=StatoLettera.ASSENTE) continue;
					for (int j=0; j<lungParola;j++) {
						if (considerata[j]) continue;
						if (parola.charAt(i)==parolaSegreta.charAt(j)) {
							stati.set(i, StatoLettera.PRESENTE);
							considerata[j]=true;
						}
					}
				}
			} else {
				for (int i=0; i<lungParola; i++) {
					stati.set(i, StatoLettera.CORRETTA);
				}
			}
		}
		return new EsitoTentativo(parola, stati, parolaValida, "Non Usato",parolaIndovinata);
	}
	

}

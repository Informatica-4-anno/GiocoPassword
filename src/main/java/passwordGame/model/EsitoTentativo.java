package passwordGame.model;

import java.util.List;

public class EsitoTentativo {
    private String parolaInserita;
    private List<StatoLettera> stati; // ArrayLista con lo stato di ogni singola lettera 
	
    public boolean isParolaIndovinata() {
		return parolaIndovinata;
	}

	public void setStati(List<StatoLettera> stati) {
		this.stati = stati;
	}

	private boolean tentativoValido;
    private String messaggio;
    private boolean parolaIndovinata;

    

	public EsitoTentativo(String parolaInserita, List<StatoLettera> stati, boolean tentativoValido, String messaggio,
			boolean parolaIndovinata) {
		this.parolaInserita = parolaInserita;
		this.stati = stati;
		this.tentativoValido = tentativoValido;
		this.messaggio = messaggio;
		this.parolaIndovinata = parolaIndovinata;
	}
	
	public List<StatoLettera> getStati() {
		return stati;
	}

	public String getParolaInserita() {
		return parolaInserita;
	}

	public boolean isTentativoValido() {
		return tentativoValido;
	}

	public void setTentativoValido(boolean tentativoValido) {
		this.tentativoValido = tentativoValido;
	}
	
}
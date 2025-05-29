package orodent.clientrelationmanager.model;

public class Disc {
    private int id;
    private String tipologia;
    private String misura;
    private String lotto;
    private String diametro;
    private String colore;
    private String problema;

    public Disc(){

    }
    //TODO NUOVO DA GPT
    public Disc(String tipologia, String misura, String lotto, String diametro, String colore, String problema) {
        this.tipologia = tipologia;
        this.misura = misura;
        this.lotto = lotto;
        this.diametro = diametro;
        this.colore = colore;
        this.problema = problema;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }
    public void setDiametro(String diametro) {
        this.diametro = diametro;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setLotto(String lotto) {
        this.lotto = lotto;
    }
    public void setMisura(String misura) {
        this.misura = misura;
    }
    public void setProblema(String problema) {
        this.problema = problema;
    }
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public int getId() {
        return id;
    }
    public String getColore() {
        return colore;
    }
    public String getDiametro() {
        return diametro;
    }
    public String getLotto() {
        return lotto;
    }
    public String getMisura() {
        return misura;
    }
    public String getProblema() {
        return problema;
    }
    public String getTipologia() {
        return tipologia;
    }
}

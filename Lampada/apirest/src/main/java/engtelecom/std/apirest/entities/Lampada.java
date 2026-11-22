package engtelecom.std.apirest.entities;

public class Lampada {
    private boolean ligada;
    private int brilho;


    public Lampada(){ 
        this.ligada = false;
        this.brilho = 0;
    }

    public Lampada(boolean ligada, int brilho){
        this.ligada = ligada;
        this.brilho = brilho;
    }

    public void setLigada(boolean ligada) { this.ligada = ligada; }

    public void setBrilho(int brilho) { this.brilho = brilho; }

    public boolean isLigada() { return ligada; }
    public int getBrilho() { return brilho; }
}


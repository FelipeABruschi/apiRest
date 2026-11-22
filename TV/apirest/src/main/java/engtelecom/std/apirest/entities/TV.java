package engtelecom.std.apirest.entities;

public class TV{
    private boolean ligada;
    private int volume;
    private int canal;

    public TV(){
        this.ligada = false;
        this.volume = 0;
        this.canal = 1;
    }

    public TV(boolean ligada, int volume, int canal){
        this.ligada = ligada;
        this.volume = volume;
        this.canal = canal;
    }

    public void setLigada(boolean ligada){this.ligada = ligada;}
    public void setCanal(int canal){this.canal = canal;}
    public void setVolume(int volume){this.volume = volume;}

    public boolean isLigada(){return this.ligada;}
    public int getVolume(){return this.volume;}
    public int getCanal(){return this.canal;}
}
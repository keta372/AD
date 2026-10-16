package Pt3;

import java.io.Serializable;

public class Videojoc implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titol;
    private String genere;
    private int anyLlancament;
    private String plataforma;
    private double preu;

    public Videojoc(String titol, String genere, int anyLlancament, String plataforma, double preu) {
        this.titol = titol;
        this.genere = genere;
        this.anyLlancament = anyLlancament;
        this.plataforma = plataforma;
        this.preu = preu;
    }

    // Getters
    public String getTitol() { return titol; }
    public String getGenere() { return genere; }
    public int getAnyLlancament() { return anyLlancament; }
    public String getPlataforma() { return plataforma; }
    public double getPreu() { return preu; }

    // Setters
    public void setTitol(String titol) { this.titol = titol; }
    public void setGenere(String genere) { this.genere = genere; }
    public void setAnyLlancament(int anyLlancament) { this.anyLlancament = anyLlancament; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public void setPreu(double preu) { this.preu = preu; }

    @Override
    public String toString() {
        return String.format("Títol: %s | Gènere: %s | Any: %d | Plataforma: %s | Preu: %.2f€", 
                             titol, genere, anyLlancament, plataforma, preu);
    }
}
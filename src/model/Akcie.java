package model;

import java.util.Date;

/**
 * Akcie uchovává informaci o akcii konkrétní firmy a <b>ke konkrétnímu datu</b>.
 */
class Akcie {
    
    //zkratka firmy
    private String zkratka;
    
    //datum, ke kterému byla akcie vypsána na burze
    private Date datum;
    
    //odchylka aktuální ceny od denního maxima
    private double odchylkaMax;
    blbost, to neni zadna odchylka ale max
    //odchylka aktuální ceny od denního minima
    private double odchylkaMin;
    
    //prumerna cena akcie za tento den
    //(pocatecniHodnotaAkcie + koncovaHodnotaAkcie)/2
    private double prumernaCena;
    
    //rozdíl mezi cenou akcie na začátku dne a na jeho konci
    private double prumernyPropad;

    /**
     * Vytvoří akcii pro konkrétní firmu a ke konkrétnímu datu.
     * @param odchylkaMax odchylka aktuální ceny od denního maxima
     * @param odchylkaMin odchylka aktuální ceny od denního minima
     * @param prumernaCena prumerna cena akcie za tento den (pocatecniHodnotaAkcie – koncovaHodnotaAkcie)/2
     * @param prumernyPropad rozdíl mezi cenou akcie na začátku dne a na jeho konci
     * @param datum datum, ke kterému byla kacie vypsána
     */
    public Akcie(double odchylkaMax, double odchylkaMin, double prumernaCena, double prumernyPropad, Date datum, String zkratka) {
        this.odchylkaMax = odchylkaMax;
        this.odchylkaMin = odchylkaMin;
        this.prumernaCena = prumernaCena;
        this.prumernyPropad = prumernyPropad;
        this.datum = datum;
        this.zkratka = zkratka;
    }
    
    /**
     * @return zkratka firmy
     */
    public String getZkratka() {
        return zkratka;
    }

    /**
     * @return odchylka aktuální ceny od denního maxima 
     */
    public double getOdchylkaMax() {
      return odchylkaMax;
    }

    /**
     * @return odchylka aktuální ceny od denního minima 
     */
    public double getOdchylkaMin() {
      return odchylkaMin;
    }

    /**
     * @return prumernou cenu akcie za tento den
     */
    public double getPrumernaCena() {
      return prumernaCena;
    }

    /**
     * @return prumerny propad akcie k tomuto dni 
     * (rozdíl mezi cenou akcie na začátku dne a na jeho konci)
     */
    public double getPrumernyPropad() {
      return prumernyPropad;
    }

    /**
     * @return datum ke kterému byla akcie vypsána
     */
    public Date getDatum() {
      return datum;
    }
}
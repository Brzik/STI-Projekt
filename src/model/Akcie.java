package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Akcie uchovává informaci o akcii konkrétní firmy a <b>ke konkrétnímu datu</b>.
 * 
 * @author Jan Brzobohatý
 */
class Akcie {
    
    //zkratka firmy
    private String zkratka;
    
    //datum, ke kterému byla akcie vypsána na burze
    private Date datum;
    
    //odchylka průměrné ceny od denního maxima
    private double odchylkaMax;
    
    //odchylka průměrné ceny od denního minima
    private double odchylkaMin;
    
    //prumerna cena akcie za tento den
    private double prumernaCena;
    
    //propad akcie k tomuto dni
    private double propad;
    
    //objem akcie
    private double objem;

    /**
     * Vytvoří akcii pro konkrétní firmu a ke konkrétnímu datu.
     * @param odchylkaMax odchylka aktuální ceny od denního maxima
     * @param odchylkaMin odchylka aktuální ceny od denního minima
     * @param prumernaCena prumerna cena akcie za tento den
     * @param propad propad akcie za tento den
     * @param datum datum, ke kterému byla kacie vypsána
     * @param zkratka zkratka názvu firmy
     * @param objem objem akcie
     */
    public Akcie(double odchylkaMax, double odchylkaMin, double prumernaCena, double propad, Date datum, String zkratka, int objem) {
        this.odchylkaMax = odchylkaMax;
        this.odchylkaMin = odchylkaMin;
        this.prumernaCena = prumernaCena;
        this.propad = propad;
        this.datum = datum;
        this.zkratka = zkratka;
        this.objem = objem;
    }
    
    /**
     * @return zkratka firmy
     */
    public String getZkratka() {
        return zkratka;
    }

    /**
     * @return odchylka průměrné ceny od denního maxima 
     */
    public double getOdchylkaMax() {
      return odchylkaMax;
    }

    /**
     * @return odchylka průměrné ceny od denního minima 
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
     * @return propad akcie k tomuto dni 
     */
    public double getPropad() {
      return propad;
    }

    /**
     * @return datum ke kterému byla akcie vypsána
     */
    public Date getDatum() {
      return datum;
    }

    /**
     * @return objem akcie
     */
    public double getObjem() {
        return objem;
    }
    
    /**
     * @return vrací datum jako řetězec ve formátu "dd. mm. yyyy"
     */
    public String getDatumToString(){
        SimpleDateFormat ft;
        
        ft = new SimpleDateFormat ("dd. MM. yyyy");

        return ft.format(datum);
    }
}
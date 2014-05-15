package model;

import java.util.Date;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Akcie uchovává informaci o akcii konkrétní firmy a <b>ke konkrétnímu datu</b>.
 * 
 * @author Jan Brzobohatý
 */
class Akcie {
    
    //zkratka firmy
    private String zkratka;
    
    //datum, ke kterému byla akcie vypsána na burze
    private LocalDate datum;
    
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
     * @param odchylkaMax odchylka aktuální ceny od denního maxima (musí být >= 0)
     * @param odchylkaMin odchylka aktuální ceny od denního minima (musí být >= 0)
     * @param prumernaCena prumerna cena akcie za tento den (prumerna cena >= 0 
     * a mensi nebo rovno 9999999999999999)
     * @param propad propad akcie za tento den
     * @param datum datum, ke kterému byla kacie vypsána
     * @param zkratka zkratka názvu firmy (musí obsahovat alespoň jeden znak)
     * @param objem objem akcie (musí být >= 0)
     * 
     * @throws FatalException když data nejsou validní
     */
    public Akcie(double odchylkaMax, double odchylkaMin, double prumernaCena, double propad, LocalDate datum, String zkratka, int objem) throws FatalException {
        if(odchylkaMax<0||odchylkaMin<0||prumernaCena<0||objem<0||prumernaCena>9999999999999999.0){
            if(datum==null||zkratka.isEmpty()){
                throw new FatalException("Data nejsou validní. (class Akcie)");
            }
            throw new FatalException("Data nejsou validní. (class Akcie)" + " " + zkratka + " " +datum.toString());
        }
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
    public LocalDate getDatum() {
      return datum;
    }

    /**
     * @return objem akcie
     */
    public double getObjem() {
        return objem;
    }
    
    /**
     * @return datum jako formát Date"
     */
    public Date getDatumToDate(){
        return datum.toDate();
    }
}
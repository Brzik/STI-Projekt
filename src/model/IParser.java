package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>Inteface obsahuje jedinou metodu, která slouží pro parsování dat ze souboru.</p>
 * 
 * <h3>Kontrakt:</h3>
 * <p>Metoda musí vracet ArrayList jednotlivých akcií.
 * Každá akcie je reprezentována dalším ArrayListem, který obsahuje 7 hodnot.
 * <table>
 * <tr><th>index</th><th>hodnota</th><th>typ</th></tr>
 * <tr><th>0</th><th> zkratka firmy </th><th>String</th></tr>
 * <tr><th>1</th><th>datum</th><th>LocalDate</th></tr>
 * <tr><th>2</th><th>zahajovaci cena</th><th>double</th></tr>
 * <tr><th>3</th><th>zaviraci cena</th><th>double</th></tr>
 * <tr><th>4</th><th>maximální cena</th><th>double</th></tr>
 * <tr><th>5</th><th>minimalni cena</th><th>double</th></tr>
 * <tr><th>6</th><th>objem akcie</th><th>int</th></tr>
 * </table>
 * </p>
 * 
 * @author Jan Brzobohatý
 */
interface IParser {
    /**
     * Metoda slouží k rozparsování dat ze souboru. 
     * 
     * @param br BufferReader pro soubor, z kterého cheme parsovat
     * @return
     * <p>Metoda vrací ArrayList jednotlivých akcií.
     * Každá akcie je reprezentována dalším ArrayListem, který obsahuje 7 hodnot.
     * <table>
     * <tr><th>index</th><th>hodnota</th><th>typ</th></tr>
     * <tr><th>0</th><th> zkratka firmy </th><th>String</th></tr>
     * <tr><th>1</th><th>datum</th><th>LocalDate</th></tr>
     * <tr><th>2</th><th>zahajovaci cena</th><th>double</th></tr>
     * <tr><th>3</th><th>zaviraci cena</th><th>double</th></tr>
     * <tr><th>4</th><th>maximální cena</th><th>double</th></tr>
     * <tr><th>5</th><th>minimalni cena</th><th>double</th></tr>
     * <tr><th>6</th><th>objem akcie</th><th>int</th></tr>
     * </table>
     * </p>
     * @throws IOException když nastane chyba při čtení ze souboru
     */
    public abstract ArrayList<ArrayList> parse(BufferedReader br) throws IOException;
}
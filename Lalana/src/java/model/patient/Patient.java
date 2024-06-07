/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.patient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Jimmy
 */
public class Patient {
    int idPatient;
    String nom;
    int idSoin;
    Date daty;
    double argent;
    String choixPriorite;
    
    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdSoin() {
        return idSoin;
    }

    public void setIdSoin(int idSoin) {
        this.idSoin = idSoin;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public double getArgent() {
        return argent;
    }

    public void setArgent(double argent) {
        this.argent = argent;
    }

    public Patient(int idPatient, String nom) {
        this.idPatient = idPatient;
        this.nom = nom;
    }

    public String getChoixPriorite() {
        return choixPriorite;
    }

    public void setChoixPriorite(String choixPriorite) {
        this.choixPriorite = choixPriorite;
    }

    public Patient(int idPatient, String nom, int idSoin, Date daty, double argent, String choixPriorite) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.idSoin = idSoin;
        this.daty = daty;
        this.argent = argent;
        this.choixPriorite = choixPriorite;
    }
    
    

    public Patient(int idPatient, String nom, int idSoin, Date daty, double argent) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.idSoin = idSoin;
        this.daty = daty;
        this.argent = argent;
    }
    
    public static void InsertPatient(Connection c,String nom) throws Exception{
        String query = "INSERT INTO patient (nom) VALUES (?)";
        
        try(PreparedStatement st = c.prepareStatement(query)){
            st.setString(1, nom);
            st.executeUpdate();
        }
    }
    
    public static void InsertSoin(Connection c,int idPatient,String daty,double argent,String choixPriorite) throws Exception{
        String query = "INSERT INTO soinPatient (idPatient,daty,argent,choixPriorite) VALUES (?,'"+daty+"',?,?)";
        
        try(PreparedStatement st = c.prepareStatement(query)){
            st.setInt(1, idPatient);
            st.setDouble(2, argent);
            st.setString(3, choixPriorite);
            st.executeUpdate();
        }
    }
    
    public static Patient getLastSoin(Connection c) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from lastsoin");
        
        while(res.next()){
            return new Patient(res.getInt(1), res.getString(6), res.getInt(2), res.getDate(3), res.getDouble(4),res.getString(5));
        }
        
        return null;
    }
    
    public static int getIdLastPatient(Connection c) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select max(idPatient) from patient");
        
        while(res.next()){
            return res.getInt(1);
        }
        
        return 0;    
    }
    
    public static Patient getSoinById(Connection c,int idSoin) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from soinPatient natural join patient where idSoin="+idSoin);
        
        while(res.next()){
            return new Patient(res.getInt(1), res.getString(6), res.getInt(2), res.getDate(3), res.getDouble(4),res.getString(5));
        }
        
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Dent;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import model.connexion.Connexion;

/**
 *
 * @author Jimmy
 */
public class Dent {
    int idDent;
    String nom;
    double coutTraitement;
    double coutRemplacement;
    double coutNettoyage;
    double coutEnlevement;
    int ordreNumerique;
    int positionGD;
    int positionHB;
    String typePriorite;
    
    public int getIdDent(){
        return idDent;
    }

    public void setIdDent(int idDent) {
        this.idDent = idDent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCoutTraitement() {
        return coutTraitement;
    }

    public void setCoutTraitement(double coutTraitement) {
        this.coutTraitement = coutTraitement;
    }

    public double getCoutRemplacement() {
        return coutRemplacement;
    }

    public void setCoutRemplacement(double coutRemplacement) {
        this.coutRemplacement = coutRemplacement;
    }

    public double getCoutNettoyage() {
        return coutNettoyage;
    }

    public void setCoutNettoyage(double coutNettoyage) {
        this.coutNettoyage = coutNettoyage;
    }

    public double getCoutEnlevement() {
        return coutEnlevement;
    }

    public void setCoutEnlevement(double coutEnlevement) {
        this.coutEnlevement = coutEnlevement;
    }

    public int getOrdreNumerique() {
        return ordreNumerique;
    }

    public void setOrdreNumerique(int ordreNumerique) {
        this.ordreNumerique = ordreNumerique;
    }

    public int getPositionGD() {
        return positionGD;
    }

    public void setPositionGD(int positionGD) {
        this.positionGD = positionGD;
    }

    public int getPositionHB() {
        return positionHB;
    }

    public void setPositionHB(int positionHB) {
        this.positionHB = positionHB;
    }

    public String getTypePriorite() {
        return typePriorite;
    }

//    iddent | nom | couttraitement | coutremplacement | coutnettoyage | coutenlevement | ordrenumerique | positiongd | positionhb | typepriorite
    public void setTypePriorite(String typePriorite) {
        this.typePriorite = typePriorite;
    }

    public Dent() {
    }

    public Dent(int idDent, String nom, double coutTraitement, double coutRemplacement, double coutNettoyage, double coutEnlevement, int ordreNumerique, int positionGD, int positionHB, String typePriorite) {
        this.idDent = idDent;
        this.nom = nom;
        this.coutTraitement = coutTraitement;
        this.coutRemplacement = coutRemplacement;
        this.coutNettoyage = coutNettoyage;
        this.coutEnlevement = coutEnlevement;
        this.ordreNumerique = ordreNumerique;
        this.positionGD = positionGD;
        this.positionHB = positionHB;
        this.typePriorite = typePriorite;
    }
    
    

    public Dent[] getAllDent(Connection c) throws Exception {
        Vector<Dent> all =  new Vector<>();
        
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select * from dent");
        
        while(res.next()){
            all.add(new Dent(res.getInt(1), res.getString(2), res.getDouble(3), res.getDouble(4), res.getDouble(5),res.getDouble(6),res.getInt(7),
                    res.getInt(8),res.getInt(9),res.getString(10)));
        }
        return all.toArray(new Dent[all.size()]);
    }
    
    public static Dent getDentById(Connection c,int idDent) throws Exception{
        PreparedStatement  st = c.prepareStatement("select * from dent where idDent="+idDent);
        ResultSet res = st.executeQuery();
        while(res.next()){
            return new Dent(res.getInt(1), res.getString(2), res.getDouble(3), res.getDouble(4), res.getDouble(5),res.getDouble(6),res.getInt(7),
                    res.getInt(8),res.getInt(9),res.getString(10));
        }
        return null;
    }
    
    public void updateInfo(Connection c,double pT,double pR,String priorite,double pN,double pE,int idDent) throws Exception{
        String updateQuery = "UPDATE dent SET coutTraitement=?, coutRemplacement=?, typePriorite=?, coutnettoyage=?, coutenlevement=? WHERE idDent=?";
        Dent d = this.getDentById(c, idDent);
        this.insertHistoriqueCout(c, idDent, d.getCoutTraitement(), d.getCoutRemplacement(),d.getCoutNettoyage(),d.getCoutEnlevement());
        try (PreparedStatement st = c.prepareStatement(updateQuery)) {
            st.setDouble(1, pT);
            st.setDouble(2, pR);
            st.setDouble(4, pN);
            st.setDouble(5, pE);
            st.setString(3, priorite);
            st.setInt(6, idDent);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Aucune dent trouvée avec l'ID spécifié pour la mise à jour.");
            }
        }
    }
    
    public void insertHistoriqueCout(Connection connection, int idDent, double coutTraitement, double coutRemplacement,double coutNettoyage,double coutEnlev) throws Exception {
        String insertQuery = "INSERT INTO historiqueCout (idDent, daty, coutTraitement, coutRemplacement,coutNettoyage,coutEnlevement) VALUES (?, now(), ?, ?,?,?)";

        try (PreparedStatement st = connection.prepareStatement(insertQuery)) {
            st.setInt(1, idDent);
            st.setDouble(2, coutTraitement);
            st.setDouble(3, coutRemplacement);
            st.setDouble(4, coutNettoyage);
            st.setDouble(5, coutEnlev);
            st.executeUpdate();
        }
    }
    
    public static void insertEtatDentPatient(Connection c,int idSoin,int idDent,int Etat) throws Exception{
        String query = "INSERT INTO etatDentPatient (idSoin, idDent, etat) VALUES (?, ?, ?)";
        
        try(PreparedStatement st = c.prepareStatement(query)){
            st.setInt(1, idSoin);
            st.setInt(2, idDent);
            st.setInt(3, Etat);
            st.executeUpdate();
        }
    }
    
    public static void insertEtatDent(Connection c,int idSoin,int[] etat) throws Exception{
        for (int i = 0; i < etat.length; i++) {
            Dent.insertEtatDentPatient(c, idSoin,i+1, etat[i]);
        }
    }
}

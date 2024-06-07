/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.scan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import model.Dent.Dent;
import model.connexion.Connexion;
import model.patient.Patient;

/**
 *
 * @author Jimmy
 */
public class ResultatScan {
    Dent nify;
    int idSoin ;
    int etat;
    String designationEta;
    int typeSoin;
    String designationTypeSoin;
    int pointAjouter;
    
    public Dent getNify() {
        return nify;
    }

    public void setNify(Dent nify) {
        this.nify = nify;
    }

    public int getIdSoin() {
        return idSoin;
    }

    public void setIdSoin(int idSoin) {
        this.idSoin = idSoin;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getDesignationEta() {
        return designationEta;
    }

    public void setDesignationEta(String designationEta) {
        this.designationEta = designationEta;
    }

    public int getTypeSoin() {
        return typeSoin;
    }

    public void setTypeSoin(int typeSoin) {
        this.typeSoin = typeSoin;
    }

    public String getDesignationTypeSoin() {
        return designationTypeSoin;
    }

    public void setDesignationTypeSoin(String designationTypeSoin) {
        this.designationTypeSoin = designationTypeSoin;
    }

    public int getPointAjouter() {
        return pointAjouter;
    }

    public void setPointAjouter(int pointAjouter) {
        this.pointAjouter = pointAjouter;
    }

    public ResultatScan() {
    }

    public ResultatScan(Dent nify, int idSoin, int etat) {
        this.nify = nify;
        this.idSoin = idSoin;
        this.etat = etat;
    }
    
    

    public ResultatScan(Dent nify, int idSoin, int etat, String designationEta, int typeSoin, String designationTypeSoin, int pointAjouter) {
        this.nify = nify;
        this.idSoin = idSoin;
        this.etat = etat;
        this.designationEta = designationEta;
        this.typeSoin = typeSoin;
        this.designationTypeSoin = designationTypeSoin;
        this.pointAjouter = pointAjouter;
    }

    public ResultatScan(int etat, String designationEta, int typeSoin, String designationTypeSoin, int pointAjouter) {
        this.etat = etat;
        this.designationEta = designationEta;
        this.typeSoin = typeSoin;
        this.designationTypeSoin = designationTypeSoin;
        this.pointAjouter = pointAjouter;
    }
    
    
    public ResultatScan[] getPrioriteSoinByBeaute(Connection c, int idSoin) throws  Exception{
        Vector<ResultatScan> resScan = new Vector<>();
        
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select idDent,idSoin,etat,designationEtat,typeSoin,DesignationTypeSoin,pointAjouter from v_getDentBeauteSante natural join etat ");
        
        while(res.next()){
            if(idSoin == res.getInt(2)){
                Dent d = Dent.getDentById(c, res.getInt(1));
                resScan.add(new ResultatScan(d, idSoin, res.getInt(3), res.getString(4), res.getInt(5), res.getString(6), res.getInt(7)));
            }
        }
        
        return resScan.toArray(new ResultatScan[resScan.size()]);
    }
    
    public ResultatScan[] getPrioriteSoinByBeauteByEtat(Connection c, int idSoin) throws  Exception{
        Vector<ResultatScan> resScan = new Vector<>();
        
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select idDent,idSoin,etat,designationEtat,typeSoin,DesignationTypeSoin,pointAjouter from v_getDentBeauteSanteEtat natural join etat ");
        
        while(res.next()){
            if(idSoin == res.getInt(2)){
                Dent d = Dent.getDentById(c, res.getInt(1));
                resScan.add(new ResultatScan(d, idSoin, res.getInt(3), res.getString(4), res.getInt(5), res.getString(6), res.getInt(7)));
            }
        }
        
        return resScan.toArray(new ResultatScan[resScan.size()]);
    }
    
    public ResultatScan[] getPrioriteSoinBySante(Connection c, int idSoin) throws  Exception{
        Vector<ResultatScan> resScan = new Vector<>();
        
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select idDent,idSoin,etat,designationEtat,typeSoin,DesignationTypeSoin,pointAjouter from v_getDentSanteBeaute natural join etat ");
        
        while(res.next()){
            if(idSoin == res.getInt(2)){
                Dent d = Dent.getDentById(c, res.getInt(1));
                resScan.add(new ResultatScan(d, idSoin, res.getInt(3), res.getString(4), res.getInt(5), res.getString(6), res.getInt(7)));
            }
        }
        
        return resScan.toArray(new ResultatScan[resScan.size()]);
    }
    
    public ResultatScan[] getPriorite(Connection c, int idSoin,String priorite) throws  Exception{
        Vector<ResultatScan> resScan = new Vector<>();
        String query ="";
        if(priorite.hashCode()=="sante".hashCode()){
            
         query = "select idDent,idSoin,etat from v_getDentSanteBeaute  where idSoin="+idSoin;
        }else if(priorite.hashCode()=="beaute".hashCode()){
            query = "select idDent,idSoin,etat from v_getDentBeauteSante  where idSoin="+idSoin;
        }else if(priorite.hashCode()=="santeEtat".hashCode()){
            query="select idDent,idSoin,etat from v_getDentSanteBeauteEtat  where idSoin="+idSoin;
        }else if(priorite.hashCode()=="beauteEtat".hashCode()){
            query="select idDent,idSoin,etat from v_getDentBeauteSanteEtat  where idSoin="+idSoin;
        }
        
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery(query);
        
        while(res.next()){
            if(idSoin == res.getInt(2)){
                ResultatScan etat = ResultatScan.getInfoEtat(c, res.getInt(3));
                Dent d = Dent.getDentById(c, res.getInt(1));
                resScan.add(new ResultatScan(d, idSoin, res.getInt(3), etat.getDesignationEta(), etat.getTypeSoin(), etat.getDesignationTypeSoin(), etat.getPointAjouter()));
            }
        }
        
        return resScan.toArray(new ResultatScan[resScan.size()]);
    }
    
    public ResultatScan[] getPrioriteSoinBySanteByEtat(Connection c, int idSoin) throws  Exception{
        Vector<ResultatScan> resScan = new Vector<>();
        
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select idDent,idSoin,etat,designationEtat,typeSoin,DesignationTypeSoin,pointAjouter from v_getDentSanteBeauteEtat natural join etat ");
        
        while(res.next()){
            if(idSoin == res.getInt(2)){
                Dent d = Dent.getDentById(c, res.getInt(1));
                resScan.add(new ResultatScan(d, idSoin, res.getInt(3), res.getString(4), res.getInt(5), res.getString(6), res.getInt(7)));
            }
        }
        
        return resScan.toArray(new ResultatScan[resScan.size()]);
    }
    
    public static void InsertDetailSoin(Connection c,int idDent,int idSoin,int etatInitial,int etatFinal,double cout ) throws Exception{
        String query = "INSERT INTO detailSoin (idDent,idSoin,etatInitial,etatFinal,cout) VALUES (?,?,?,?,?)";
        
        try(PreparedStatement st = c.prepareStatement(query)){
            st.setInt(1, idDent);
            st.setInt(2, idSoin);
            st.setInt(3, etatInitial);
            st.setInt(4, etatFinal);
            st.setDouble(5, cout);
            st.executeUpdate();
        }
    }
    
    public void proposition(Connection c, int idSoin) throws Exception{
        Patient infoPatient = Patient.getSoinById(c, idSoin);
        
        ResultatScan resScan = new ResultatScan();
        
        ResultatScan[] allResScan = resScan.getPriorite(c, idSoin,infoPatient.getChoixPriorite());
        
        for (int i = 0; i < allResScan.length; i++) {
            int etatDent = allResScan[i].getEtat();
            while(etatDent<10){
                ResultatScan infoEtat = this.getInfoEtat(c, etatDent);
                
                int idNify = allResScan[i].getNify().getIdDent();
                
                if(infoEtat.getPointAjouter()<0){
                    
                    etatDent=0;
                    
                    ResultatScan.InsertDetailSoin(c,idNify , idSoin, allResScan[i].getEtat(), etatDent, allResScan[i].getNify().getCoutEnlevement());
                }else{
                    
                    
                    double montant =0;
                    
                    int etatfinal = etatDent+ infoEtat.getPointAjouter();
                    
                    if(infoEtat.getTypeSoin()==1){
                        
                        montant = allResScan[i].getNify().getCoutNettoyage();
                        
                    }else if(infoEtat.getTypeSoin()==2){
                        
                        montant = allResScan[i].getNify().getCoutTraitement();
                        
                    }else if(infoEtat.getTypeSoin()==4){
                        
                        montant = allResScan[i].getNify().getCoutRemplacement();
                        
                    }
                    
                    ResultatScan.InsertDetailSoin(c, idNify, idSoin, etatDent, etatfinal, montant);
                    
                    etatDent= etatfinal;
                }
//                System.out.println("etaDent: "+etatDent);
            }
        }        
        
    }

public void propositionByArgent(Connection c, int idSoin) throws Exception{
        Patient infoPatient = Patient.getSoinById(c, idSoin);
        
        ResultatScan resScan = new ResultatScan();
        
        ResultatScan[] allResScan = resScan.getPriorite(c, idSoin,infoPatient.getChoixPriorite());
        
        double resteVola = infoPatient.getArgent();
        
        for (int i = 0; i < allResScan.length; i++) {
            System.out.println("reste vola: "+resteVola);
            int etatDent = allResScan[i].getEtat();
            while(etatDent<10){
                ResultatScan infoEtat = this.getInfoEtat(c, etatDent);
                
                int idNify = allResScan[i].getNify().getIdDent();
                
                if(infoEtat.getPointAjouter()<0){
                    
                    
                    if(resteVola >= allResScan[i].getNify().getCoutEnlevement()){
                        etatDent=0;
                        ResultatScan.InsertDetailSoin(c,idNify , idSoin, allResScan[i].getEtat(), etatDent, allResScan[i].getNify().getCoutEnlevement());
                        resteVola = resteVola-allResScan[i].getNify().getCoutEnlevement();
                    }else if(resteVola < allResScan[i].getNify().getCoutEnlevement()){
                        break;
                    }
                    
                }else{
                    
                    
                    double montant =0;
                    
                    int etatfinal = etatDent+ infoEtat.getPointAjouter();
                    
                    if(infoEtat.getTypeSoin()==1){
                        
                        montant = allResScan[i].getNify().getCoutNettoyage();
                        
                    }else if(infoEtat.getTypeSoin()==2){
                        
                        montant = allResScan[i].getNify().getCoutTraitement();
                        
                    }else if(infoEtat.getTypeSoin()==4){
                        
                        montant = allResScan[i].getNify().getCoutRemplacement();
                        
                    }
                    
                    if(resteVola >= montant){
                        
                        ResultatScan.InsertDetailSoin(c, idNify, idSoin, etatDent, etatfinal, montant);
                        
                        resteVola = resteVola - montant;
                    
                        etatDent= etatfinal;
                    }else if(resteVola < montant){
                    
                        break;
                    
                    }
                    
                }
//                System.out.println("etaDent: "+etatDent);
            }
            
        }        
        
    }
    
    public static ResultatScan getInfoEtat(Connection c, int etat) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from etat where etat="+etat);
        while(res.next()){
            return new ResultatScan(etat, res.getString(2), res.getInt(3), res.getString(4), res.getInt(5));
        }
        
        return null;
    }
    public static ResultatScan[] getAllEtat(Connection c) throws Exception{
        Vector<ResultatScan> all = new Vector<>();
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from etat order by etat desc");
        while(res.next()){
            all.add( new ResultatScan(res.getInt(1), res.getString(2), res.getInt(3), res.getString(4), res.getInt(5)));
        }
        
        return all.toArray(new ResultatScan[all.size()]);
    }
    
    public static ResultatScan getEtatByIdSoinByIdDent(Connection c, int idSoin,int idDent) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from etatDentPatient where idSoin="+idSoin+" and idDent="+idDent);
        while(res.next()){
            Dent d = Dent.getDentById(c, res.getInt(2));
            return new ResultatScan(d,res.getInt(1),res.getInt(3));
        }
        
        return null;
    }
}

//    public ResultatScan[] allResultat(int[] numDent,int[] etat)throws Exception{
//        Vector<ResultatScan> all = new Vector<>();
//        
//        for (int i = 0; i < numDent.length; i++) {
//            all.add(new ResultatScan(numDent[i],etat[i]));
//        }
//        return all.toArray(new ResultatScan[all.size()]);
//    }
//    
//    public  void getPrixByChoixPriorite(ResultatScan[] all,String priorite){
//        for (int i = 0; i < all.length; i++) {
//            System.out.println(all[i].getNify().getTypePriorite());
//            if(priorite.hashCode()==all[i].getNify().getTypePriorite().hashCode() && all[i].getEtat()==10){
//                System.out.println("Prix de Remplacement: "+all[i].getNify().getCoutRemplacement());
//            }else if(all[i].getEtat()==10){
//                System.out.println("Prix de Traitement: "+all[i].getNify().getCoutTraitement());
//            }
//        }
//    }

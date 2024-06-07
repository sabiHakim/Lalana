/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.detailSoin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import model.Dent.Dent;
import model.scan.ResultatScan;

/**
 *
 * @author Jimmy
 */
public class DetailSoin {
    Dent nify;
    int etatInitial;
    int etatFinal;
    int idSoin;
    double cout;
    ResultatScan etat;
    
    public Dent getNify() {
        return nify;
    }

    public void setNify(Dent nify) {
        this.nify = nify;
    }

    public int getEtatInitial() {
        return etatInitial;
    }

    public void setEtatInitial(int etatInitial) {
        this.etatInitial = etatInitial;
    }

    public int getEtatFinal() {
        return etatFinal;
    }

    public void setEtatFinal(int etatFinal) {
        this.etatFinal = etatFinal;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public int getIdSoin() {
        return idSoin;
    }

    public void setIdSoin(int idSoin) {
        this.idSoin = idSoin;
    }

    public ResultatScan getEtat() {
        return etat;
    }

    public void setEtat(ResultatScan etat) {
        this.etat = etat;
    }

    
    public DetailSoin() {
    }

    public DetailSoin(Dent nify, int etatInitial, int etatFinal, int idSoin, double cout, ResultatScan etat) {
        this.nify = nify;
        this.etatInitial = etatInitial;
        this.etatFinal = etatFinal;
        this.idSoin = idSoin;
        this.cout = cout;
        this.etat = etat;
    }

    
    public DetailSoin(Dent nify, int etatInitial, int etatFinal, int idSoin, double cout) {
        this.nify = nify;
        this.etatInitial = etatInitial;
        this.etatFinal = etatFinal;
        this.idSoin = idSoin;
        this.cout = cout;
    }

   
    
    public static DetailSoin getInfoEtatFinal(Connection c, int idSoin,int idDent) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from detailSoin where idDetailSoin in (select max(iddetailsoin) from detailSoin where idDent="+idDent+" and idSoin="+idSoin+")");
        while(res.next()){
            Dent d = Dent.getDentById(c, res.getInt(2));
            return new DetailSoin(d,res.getInt(4),res.getInt(5),res.getInt(3),res.getDouble(6));
        }
        
        return null;
    }
    public static DetailSoin getInfoEtatInitial(Connection c, int idSoin,int idDent) throws Exception{
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from detailSoin where idDetailSoin in (select min(iddetailsoin) from detailSoin where idDent="+idDent+" and idSoin="+idSoin+")");
        while(res.next()){
            Dent d = Dent.getDentById(c, res.getInt(2));
            return new DetailSoin(d,res.getInt(4),res.getInt(5),res.getInt(3),res.getDouble(6));
        }
        
        return null;
    }
    
    public static DetailSoin[] getDetailByIdSoinByidDent(Connection c, int idSoin,int idDent) throws Exception{
        Vector<DetailSoin> all = new Vector<>();
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from detailSoin where  idDent="+idDent+" and idSoin="+idSoin);
        while(res.next()){
            Dent d = Dent.getDentById(c, res.getInt(2));
            ResultatScan etat = ResultatScan.getInfoEtat(c, res.getInt(4));
            all.add( new DetailSoin(d,res.getInt(4),res.getInt(5),res.getInt(3),res.getDouble(6),etat));
        }
        
        return all.toArray(new DetailSoin[all.size()]);
    }
    
        public static DetailSoin[] getResumer(Connection c, int idSoin) throws Exception{
        Vector<DetailSoin> all = new Vector<>();
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select idDent,idSoin,sum(Cout) from detailSoin where  idSoin="+idSoin+" group by iddent,idSoin");
        while(res.next()){
            int idDent = res.getInt(1);
            Dent d = Dent.getDentById(c, idDent);
            DetailSoin etatInitial = DetailSoin.getInfoEtatInitial(c, idSoin, idDent);
            DetailSoin etatFinal = DetailSoin.getInfoEtatFinal(c, idSoin, idDent);
            
            all.add( new DetailSoin(d,etatInitial.getEtatInitial(),etatFinal.getEtatFinal(),res.getInt(2),res.getDouble(3)));
        }
        
        return all.toArray(new DetailSoin[all.size()]);
    }
        
        public static double coutTotal(Connection c, int idSoin) throws Exception{
            
            Statement st = c.createStatement();
            ResultSet res = st.executeQuery("select sum(Cout) from detailSoin where  idSoin="+idSoin);
            while(res.next()){
                return res.getDouble(1);
            }
            
            return 0;
        }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.sql.Connection;
import model.Dent.Dent;
import model.connexion.Connexion;
import model.detailSoin.DetailSoin;
import model.patient.Patient;
import model.scan.ResultatScan;

/**
 *
 * @author Jimmy
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Connection co = Connexion.connect();
        System.out.println(co);
        Dent d = new Dent();
        //Avoir Tous les dent
        Dent[] allDent = d.getAllDent(co);
        
//        for (int i = 0; i < allDent.length; i++) {
//            System.out.println(allDent[i].getNom());
//        }
        
        //Get Dent By Id
        Dent dentById = d.getDentById(co,6);
        
//        System.out.println(dentById.getNom());
        
        //RESScan
//        ResultatScan res = new ResultatScan(35,20);
        //System.out.println("IdNidy: "+res.getNumDent());
                
        //All ResScan
        int[] numDent = new int[2];
        numDent[0] = 3;  
        numDent[1] = 22;  
        int[] etat = new int[2];
        etat[0] = 10;  
        etat[1] = 10;  
        
//        ResultatScan[] allres = res.allResultat(numDent, etat);
        
//        for (int i = 0; i < allres.length; i++) {
//            System.out.println("Num Nify: "+allres[i].getNumDent()+" idNify:"+allres[i].getNify().getIdDent());
//        }
        
//        res.getPrixByChoixPriorite(allres, "beaut‚");
        
        //Update
//        d.updateInfo(co, 20000,40000,"beaute",10000,15000,11);

//GET priorite des dents par prioriter ou par etat
        ResultatScan resScan = new ResultatScan();
        ResultatScan[] allResScan = resScan.getPriorite(co, 5,"beauteEtat");
        //resScan.propositionByArgent(co, 1);
        
//        
        for (int i = 0; i < allResScan.length; i++) {
            System.out.println("idDent: "+allResScan[i].getNify().getIdDent()+" Etat: "+allResScan[i].getEtat()+" TypeSoin: "+allResScan[i].getTypeSoin());
        }
        
//Insert etatDentPatient
       // Dent.insertEtatDentPatient(co, 1, 2, 10);
        
//Insert patient
        //Patient.InsertPatient(co, "Jim");
        
//Insert soinPatient
//        Patient.InsertSoin(co, 2, "18-12-2022", 90000,"beaute");

//Get lastPatient
Patient p = Patient.getSoinById(co,1);
       // System.out.println(p.getNom());
        
        //Insert detaill Soin
        //ResultatScan.InsertDetailSoin(co, 20, 1, 0, 10, 30000);
        
//       maka info etat 
//         ResultatScan r = ResultatScan.getInfoEtat(co, 0);
//         System.out.println(r.getDesignationTypeSoin());


//        DetailSoin[] detail = DetailSoin.getDetailByIdSoinByidDent(co,1, 7);
//        for (int i = 0; i < detail.length; i++) {
//            
//        System.out.println("idDent: "+detail[i].getNify().getIdDent()+" etatInitial: "+detail[i].getEtatInitial()+" etatFinal: "+detail[i].getEtatFinal()+" Montant: "+detail[i].getCout()+" Type Soin: "+detail[i].getEtat().getDesignationTypeSoin());
//            
//        }

    }
}

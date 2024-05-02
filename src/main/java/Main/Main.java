package Main;

import Entities.Commentaire;
import Entities.Etablissement;
import Services.CommentaireService;
import Services.EtablissementService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Etablissement e=new Etablissement(10.2, 10.2,"hotel sahabeach","HOTEL","saka");
        EtablissementService es=new EtablissementService();
        CommentaireService cs = new CommentaireService();
        
        //us.insert(s);
        //us.update(s1);
        //String resultat=us.readById(2).toString();;
        //System.out.println(resultat);
        
       ArrayList<Commentaire> userList = cs.readAll();

       for(Commentaire i:userList)
       {
        System.out.println(i.toString());
       }

       // es.delete(10);
        
        
        
    }
    
}

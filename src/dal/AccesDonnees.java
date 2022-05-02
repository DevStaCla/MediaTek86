package dal;

import connexion.ConnexionBDD;
import model.Absence;
import model.Motif;
import model.Personnel;
import model.Responsable;
import model.Service;

import java.util.ArrayList;

/**
 * classe qui fait le lien entre la vue et la classe de connexion
 * @author Claire
 *
 */
public class AccesDonnees {
	/**
	 * cha�ne de connexion � la base de donn�es
	 */
	private static String connectionURL = "jdbc:mysql://localhost:3306/mediatek86";
	/**
	 * login pour acc�der � la base de donn�es
	 */
	private static String login = "mtmanager";
	/**
	 * mot de passe pour acc�der � la base de donn�es
	 */
	private static String pwd = "Aga,Ajtp86";	
	
	/**
	 * demande � ConnexionBDD la liste d'enregistrements de la table personnel
	 * @return liste du personnel sous forme de ArrayList
	 */
	public static ArrayList<Personnel> getPersonnel() {
		ArrayList<Personnel> lePersonnel = new ArrayList<>();
		String req = "select p.idpersonnel as idpersonnel, p.nom as nom, p.prenom as prenom, p.tel as tel, p.mail as mail, s.idservice as idservice, s.libelle as service ";
        req += "from personnel p join service s on (p.idservice = s.idservice) ";
        req += "order by nom, prenom;";
        ConnexionBDD conn = ConnexionBDD.getInstance(connectionURL, login, pwd);
        conn.requeteSelect(req, null);
        while (Boolean.TRUE.equals(conn.lireCurseur()))
        {
            Personnel personnel = new Personnel(
            		(int)conn.champ("iddeveloppeur"),
            		(String)conn.champ("nom"),
            		(String)conn.champ("prenom"),
            		(String)conn.champ("tel"),
            		(String)conn.champ("mail"),
            		(int)conn.champ("idprofil"),
            		(String)conn.champ("profil"));
            lePersonnel.add(personnel);
        }
        conn.fermeCurseur();
        return lePersonnel;
	}
	
	public static ArrayList<Responsable> getResponsables() {
		ArrayList<Responsable> lesResponsables = new ArrayList<>();
		String req = "select login, pwd from responsable;";
		ConnexionBDD conn = ConnexionBDD.getInstance(connectionURL, login, pwd);
		conn.requeteSelect(req, null);
		while (Boolean.TRUE.equals(conn.lireCurseur())) {
			Responsable resp = new Responsable(
					(String)conn.champ("login"),
					(String)conn.champ("pwd"));
			lesResponsables.add(resp);
		}
		conn.fermeCurseur();
		return lesResponsables;
	}
}

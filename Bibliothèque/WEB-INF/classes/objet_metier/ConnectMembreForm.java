package objet_metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import objet_metier.FormFunctionGeneral;
import objet_metier.MembreAction;

import beans.Membre;

public class ConnectMembreForm {

	private static final String CHAMP_MDP  = "mdp";
    private static final String CHAMP_MAIL = "mail";
   
    private Map<String, String> erreurs = new HashMap<String, String>();
    
    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    /**
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
	
	/**
	 * Valide le mot de passe
	 */
	private void validationMdp ( String mdp, String mail ) throws Exception{
	    if (mdp == null) {
	        throw new Exception("Attention, le champ MOT DE PASSE n'est pas remplie !");
	    }
	    if (!ConnectorSQL.VerifMdpMailMembre(mdp, mail)) {
	    	throw new Exception("Attention, le mot de passe est incorrect !");
	    }
	}
	
	/**
	 * Valide l'email saisie
	 */
	private void validationMail ( String mail ) throws Exception{
	    if (mail == null) {
	        throw new Exception("Attention, le champ MAIL n'est pas remplie !");
	    }
	    if (!ConnectorSQL.VerifMailMembre(mail, 0)) {
	    	throw new Exception("Attention, cet adresse mail n'existe pas !");
	    }
	}
	
    /**
	 * Permet de récupérer les données envoyé par le formulaire et de renvoyer un objet Membre
	 * @param request
	 * @return
	 */
	public Membre connectMembre ( HttpServletRequest request ) {
		// Récupère les valeurs des inputs du formulaire
		String mdp  = FormFunctionGeneral.getValeurChamp(request, CHAMP_MDP);
		String mail = FormFunctionGeneral.getValeurChamp(request, CHAMP_MAIL);

		Membre old_Membre = new Membre();
		
		// Validation du champ MAIL
        try {
        	validationMail ( mail );
        } catch ( Exception e ) {
        	setErreur( CHAMP_MAIL, e.getMessage() );
        }
        
		
		// Validation du champ Mot de passe
        try {
            validationMdp ( mdp, mail );
        } catch ( Exception e ) {
            setErreur( CHAMP_MDP, e.getMessage() );
        }
        
        
        // On vérifie s'il n'y a pas d'erreurs
        if ( getErreurs().isEmpty() ) {
        	ResultSet rs = ConnectorSQL.selectMemberByMail(mail);
        	
        	try {
    			while (rs.next()) {
    				MembreAction.initializeMembre(old_Membre, rs);
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        	
        } else {
        	// On renvoie les données du formulaire
        	old_Membre.setE_mail (mail);
        	old_Membre.setMdp(mdp);
        }

	    return old_Membre;
	}
}

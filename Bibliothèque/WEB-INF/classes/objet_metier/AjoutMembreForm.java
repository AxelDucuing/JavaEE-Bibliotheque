package objet_metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import objet_metier.FormFunctionGeneral;
import beans.Membre;
import beans.Role;

public class AjoutMembreForm {

	private static final String CHAMP_MDP   = "mdp";
    private static final String CHAMP_MAIL  = "mail";
    private static final String CHAMP_TEL   = "tel";
    
    private static final String ROLE_MEMBRE = "membre";
   
    private Map<String, String> erreurs = new HashMap<String, String>();
    
    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
	
	/**
	 * Valide le mot de passe
	 */
	private void validationMdp ( String mdp ) throws Exception{
	    if (mdp == null) {
	        throw new Exception("Attention, le champ MOT DE PASSE n'est pas remplie !");
	    }
	}
	
	/**
	 * Valide l'email saisie
	 */
	private void validationMail ( String mail ) throws Exception{
	    if (mail == null) {
	        throw new Exception("Attention, le champ MAIL n'est pas remplie !");
	    }
	    if (!ConnectorSQL.VerifMailMembre(mail, 1)) {
	    	throw new Exception("Attention, cet adresse mail existe déjà !");
	    }
	}
	
	/**
	 * Valide le telephone saisie
	 */
	private void validationTelephone ( String tel ) throws Exception{
	    if (tel == null) {
	        throw new Exception("Attention, le champ TELEPHONE n'est pas remplie !");
	    }
	}
	
    /**
	 * Permet de récupérer les données envoyé par le formulaire et de renvoyer un objet Membre
	 * @param request : requête Do Post
	 * @return
	 */
	public Membre inscrireMembre ( HttpServletRequest request ) {
		// Récupère les valeurs des inputs du formulaire
		String mdp  = FormFunctionGeneral.getValeurChamp(request, CHAMP_MDP);
		String mail = FormFunctionGeneral.getValeurChamp(request, CHAMP_MAIL);
		String tel  = FormFunctionGeneral.getValeurChamp(request, CHAMP_TEL);

		Membre new_Membre = new Membre();
		
		// Validation du champ Mot de passe
        try {
            validationMdp ( mdp );
        } catch ( Exception e ) {
            setErreur( CHAMP_MDP, e.getMessage() );
        }
        
        
        // Validation du champ MAIL
        try {
        	validationMail ( mail );
        } catch ( Exception e ) {
        	setErreur( CHAMP_MAIL, e.getMessage() );
        }
        
        
        // Validation du champ TEL
        try {
        	validationTelephone ( tel );
        } catch ( Exception e ) {
        	setErreur( CHAMP_TEL, e.getMessage() );
        }
        
        new_Membre.setMdp(mdp);
    	new_Membre.setE_mail (mail);
    	new_Membre.setTelephone (tel);
    	
    	ResultSet rs = ConnectorSQL.selectRoleMember(ROLE_MEMBRE);
    	// Creation d'un auteur
		Role role = new Role();
    	try {
			while (rs.next()) {
				RoleAction.initializeRole(role, rs);
				new_Membre.setRole(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
	    return new_Membre;
	}
}

package objet_metier;

import javax.servlet.http.HttpServletRequest;

public class FormFunctionGeneral {
	
	/**
	 * Permet de récupérer une valeur du formulaire de la requete
	 * @param request : requete
	 * @param NomChamp : Nom du champs (name) du formulaire
	 * @return : la valeur du champ
	 */
	public static String getValeurChamp (HttpServletRequest request, String NomChamp) {
		String valeur = request.getParameter(NomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
	
}

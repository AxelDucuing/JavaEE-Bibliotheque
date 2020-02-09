package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Membre;

import objet_metier.AjoutMembreForm;
import objet_metier.ConnectorSQL;

/**
 * Servlet implementation class MaPremiereServlet
 */
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_FORM      = "/WEB-INF/inscription.jsp";
	
	private static final String URL_ACCUEIL       = "index";
	private static final String URL_INSCRIPTION   = "inscription";
	private static final String URL_CONNEXION     = "connexion";
	
	private static final String ATTR_URL_MAIN        = "url_main";
	private static final String ATTR_URL_INSCRIPTION = "url_signin";
	private static final String ATTR_URL_CONNEXION    = "url_login";
	private static final String ATTR_FORM            = "form";
	private static final String ATTR_MEMBRE 	 	 = "membre";
	
	private static final String ATT_SESSION_USER = "membre_log";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	//init
	public void init() {
		
	}
	
	public Inscription() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_URL_INSCRIPTION, URL_INSCRIPTION);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		
		getServletContext().getRequestDispatcher(VUE_FORM).forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Préparation de l'objet formulaire
		AjoutMembreForm form = new AjoutMembreForm();
		
		// Appel au traitement et à la validation de la requête, et récupération du bean en résultant
        Membre new_membre = form.inscrireMembre(request);
        
        request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_FORM, form);
		request.setAttribute(ATTR_MEMBRE, new_membre);
		request.setAttribute(ATTR_URL_INSCRIPTION, URL_INSCRIPTION);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		
		// Création ou récupération de la session
		HttpSession session = request.getSession();
        
        // On vérifie s'il y a des erreurs
        if ( form.getErreurs().isEmpty() ) {
        	// On ajoute le nouveau membre à la base de données
        	int result = ConnectorSQL.insertMembre(new_membre);
        	
        	// On vérifie que l'ajout à bien été faites
    		if (result == 0) {
    			// Il y a eu une erreur lors de la requete
    			getServletContext().getRequestDispatcher(VUE_FORM).forward(request,response);
    		} else {
    			// On ajoute le membre à la session
        		session.setAttribute(ATT_SESSION_USER, new_membre);
        		
        		response.sendRedirect(URL_ACCUEIL);
    		}
		}
		else {
			// Le membre log dans la session est vide car il y a des erreurs
    		// session.setAttribute(ATT_SESSION_USER, null);
			
			getServletContext().getRequestDispatcher(VUE_FORM).forward(request,response);
		}
	}
}
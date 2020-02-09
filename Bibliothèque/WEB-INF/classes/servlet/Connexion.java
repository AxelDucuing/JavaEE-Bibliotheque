package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Membre;
import objet_metier.ConnectMembreForm;

/**
 * Servlet implementation class Connexion
 */
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_FORM      = "/WEB-INF/connexion.jsp";
	
	private static final String URL_ACCUEIL     = "index";
	private static final String URL_CONNEXION   = "connexion";
	private static final String URL_INSCRIPTION = "inscription";
	
	private static final String ATTR_URL_MAIN      = "url_main";
	private static final String ATTR_URL_INSCRIRE  = "url_signin";
	private static final String ATTR_URL_CONNEXION = "url_login";
	private static final String ATTR_FORM          = "form";
	private static final String ATTR_MEMBRE        = "membre";
	
	private static final String ATT_SESSION_USER   = "membre_log";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Connexion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		request.setAttribute(ATTR_URL_INSCRIRE, URL_INSCRIPTION);
		
		getServletContext().getRequestDispatcher(VUE_FORM).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Préparation de l'objet formulaire
		ConnectMembreForm form = new ConnectMembreForm();
		
		// Appel au traitement et à la validation de la requête, et récupération du bean en résultant
        Membre connect_membre = form.connectMembre(request);
        
        request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_FORM, form);
		request.setAttribute(ATTR_MEMBRE, connect_membre);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		request.setAttribute(ATTR_URL_INSCRIRE, URL_INSCRIPTION);
		
		// Création ou récupération de la session
		HttpSession session = request.getSession();
        
        // On vérifie s'il y a des erreurs
        if ( form.getErreurs().isEmpty() ) {
			// On ajoute le membre à la session
    		session.setAttribute(ATT_SESSION_USER, connect_membre);
    		
    		response.sendRedirect(URL_ACCUEIL);
		}
		else {
			// Le membre log dans la session est vide car il y a des erreurs
    		// session.setAttribute(ATT_SESSION_USER, null);
			
			getServletContext().getRequestDispatcher(VUE_FORM).forward(request,response);
		}
	}

}

package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Livre;
import beans.Membre;
import objet_metier.ConnectorSQL;
import objet_metier.LivreAction;

/**
 * Servlet implementation class MaPremiereServlet
 */
public class VoirPlus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_VOIRPLUS = "/WEB-INF/voir_plus.jsp";
	
	private static final String URL_ACCUEIL       = "index";
	private static final String URL_DECONNECTION  = "deconnexion";
	private static final String URL_INSCRIPTION   = "inscription";
	private static final String URL_CONNEXION     = "connexion";
	private static final String URL_EMPRUNT       = "liste-emprunt";
	private static final String URL_AUTEUR        = "info-auteur";
	
	private static final String ATTR_GETURL_ID        = "id";
	
	private static final String ATTR_URL_MAIN         = "url_main";
	private static final String ATTR_URL_INSCRIPTION  = "url_signin";
	private static final String ATTR_URL_CONNEXION    = "url_login";
	private static final String ATTR_URL_DECONNECTION = "url_deco";
	private static final String ATTR_URL_EMPRUNT      = "url_emprunt";
	private static final String ATTR_URL_AUTEUR       = "url_auteur";
	private static final String ATTR_URL_ACTION       = "action";
	private static final String ATTR_LIVRE            = "Livre";
	
	private static final String ACTION_RENDU     = "rendu";
	private static final String ACTION_EMPRUNT   = "emprunt";
	
	private static final String LIVRE_EMPRUNTER   = "pris";
	private static final String LIVRE_POSSEDER    = "possede";
	
	private static final String ATT_SESSION_USER   = "membre_log";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	//init
	public void init() {
		
	}
	
	public VoirPlus() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupération de l'id du livre passer dans l'URL
		int id = Integer.parseInt(request.getParameter(ATTR_GETURL_ID));
	
		// Recupération résultat de la requete
		ResultSet rs = ConnectorSQL.selectLivreById(id);
		
		// Livre selectionner par l'utilisateur
		Livre livre = new Livre();
		
		try {
			while (rs.next()) {
				// Initialisation des donnés du livre
				LivreAction.initializeLivre(livre, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (livre.getMembre().getId() == 0) {
			request.setAttribute(ATTR_URL_ACTION, ACTION_EMPRUNT);
			request.setAttribute(LIVRE_EMPRUNTER, false);
		} else {
			request.setAttribute(LIVRE_EMPRUNTER, true);
			request.setAttribute(ATTR_URL_ACTION, ACTION_RENDU);
			// On vérifie si le membre connecté possède ce livre
			boolean possede = false;
			// Création ou récupération de la session
			HttpSession session = request.getSession();
			Membre membre_log = (Membre) session.getAttribute(ATT_SESSION_USER);
			if (livre.getMembre().getId() == membre_log.getId()) {
				possede = true;
			}
			request.setAttribute(LIVRE_POSSEDER, possede);
		}
		
		ConnectorSQL.close();
		
		request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_URL_DECONNECTION, URL_DECONNECTION);
		request.setAttribute(ATTR_URL_INSCRIPTION, URL_INSCRIPTION);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		request.setAttribute(ATTR_URL_EMPRUNT, URL_EMPRUNT);
		request.setAttribute(ATTR_URL_AUTEUR, URL_AUTEUR);
		request.setAttribute(ATTR_LIVRE, livre);
		
		getServletContext().getRequestDispatcher(VUE_VOIRPLUS).forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Livre;
import beans.Membre;
import objet_metier.ConnectorSQL;
import objet_metier.LivreAction;

/**
 * Servlet implementation class ListeEmprunt
 */
@WebServlet("/ListeEmprunt")
public class ListeEmprunt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_EMPRUNT    = "/WEB-INF/liste_emprunt.jsp";
	
	private static final String URL_ACCUEIL       = "index";
	private static final String URL_DECONNECTION  = "deconnexion";
	private static final String URL_SEARCH        = "rechercher";
	private static final String URL_VOIR_PLUS     = "voir-plus";
	
	private static final String ATTR_GETURL_ID        = "id";
	private static final String ATTR_GETURL_ACTION    = "action";
	private static final String ACTION_RENDU          = "rendu";
	private static final String ACTION_EMPRUNT        = "emprunt";
	
	private static final String ATTR_URL_MAIN         = "url_main";
	private static final String ATTR_URL_DECONNECTION = "url_deco";
	private static final String ATTR_URL_SEARCH       = "rechercher";
	private static final String ATTR_VOIR_PLUS        = "url_voirplus";
	private static final String ATTR_LISTE            = "liste_Livre";
	private static final String ATTR_MESSAGE          = "message";
	
	private static final String MESSAGE_LIVRE_MAX       = "Vous avez déjà 5 livres empruntés, "
			+ "pensez à rendre vos livres pour pouvoir en emprunter de nouveau.";
	private static final String MESSAGE_SQL_ERROR       = "Il y a eu une erreur avec la connexion "
			+ "à la base de données ! Veuillez réessayer ultérieurement.";
	private static final String MESSAGE_SUCCESS_EMPRUNT = "Ce livre a bien été ajouté à votre "
			+ "liste d'emprunts.";
	private static final String MESSAGE_SUCCESS_RENDU   = "Le livre a bien été rendu.";
	
	private static final String ATT_SESSION_USER   = "membre_log";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeEmprunt() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Création ou récupération de la session
		HttpSession session = request.getSession();
		
		Membre membre_actuel = (Membre) session.getAttribute(ATT_SESSION_USER);
		
		// Check si le parametre id exist
        if (request.getParameterMap().containsKey(ATTR_GETURL_ID)) {
        	String message = "";
        	int id_livre = Integer.parseInt(request.getParameter(ATTR_GETURL_ID));
        	int result = 0;
        	// On regarde si c'est un emprunt ou un rendu
        	switch (request.getParameter(ATTR_GETURL_ACTION)) {
	        	case ACTION_RENDU :
	        		// On retire le livre de la liste de livre emprunté
	        		result = ConnectorSQL.SupprLivreToMember(id_livre);
	        		if (result == 0) {
	        			// Il y a eu une erreur lors de la requête
	        			message = MESSAGE_SQL_ERROR;
	        		} else {
	        			message = MESSAGE_SUCCESS_RENDU;
	        		}
	        		break;
	        	case ACTION_EMPRUNT :
	        		// Si le membre à moins de 5 livres d'emprunté
	            	if ( ConnectorSQL.checkNombreLivreEmprunt(membre_actuel.getId()) ) {
	            		// On ajoute le livre à sa liste de livre emprunté
	            		result = ConnectorSQL.AddLivreToMember(membre_actuel.getId(), id_livre);
	            		if (result == 0) {
	            			// Il y a eu une erreur lors de la requête
	            			message = MESSAGE_SQL_ERROR;
	            		} else {
	            			message = MESSAGE_SUCCESS_EMPRUNT;
	            		}
	            	} else {
	            		message = MESSAGE_LIVRE_MAX;
	            	}
	        		break;
        	}
        	
        	request.setAttribute(ATTR_MESSAGE, message);
        }
		
		ResultSet rs = ConnectorSQL.selectLivreByMembre(membre_actuel.getId());
		
		// Liste des livres de l'utilisateur
		List<Livre> listeLivre = new ArrayList<Livre>();
		
		// Ajout des données pour chaques livre récupéré de la requête
		try {
			while (rs.next()) {
				Livre l = new Livre();
				LivreAction.initializeLivre(l, rs);
				listeLivre.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ConnectorSQL.close();
		
		request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_URL_DECONNECTION, URL_DECONNECTION);
		request.setAttribute(ATTR_URL_SEARCH, URL_SEARCH);
		request.setAttribute(ATTR_VOIR_PLUS, URL_VOIR_PLUS);
		request.setAttribute(ATTR_LISTE, listeLivre);
		
		getServletContext().getRequestDispatcher(VUE_EMPRUNT).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

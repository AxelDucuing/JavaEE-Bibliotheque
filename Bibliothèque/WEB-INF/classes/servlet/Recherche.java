package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Livre;

import objet_metier.ConnectorSQL;
import objet_metier.LivreAction;
import objet_metier.FormFunctionGeneral;
/**
 * Servlet implementation class MaPremiereServlet
 */
public class Recherche extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_RECHERCHER = "/WEB-INF/recherche.jsp";
	
	private static final String URL_ACCUEIL       = "index";
	private static final String URL_DECONNECTION  = "deconnexion";
	private static final String URL_INSCRIPTION   = "inscription";
	private static final String URL_CONNEXION     = "connexion";
	private static final String URL_EMPRUNT       = "liste-emprunt";
	private static final String URL_AUTEUR        = "info-auteur";
	private static final String URL_VOIR_PLUS     = "voir-plus";
	
	private static final String ATTR_URL_MAIN         = "url_main";
	private static final String ATTR_URL_INSCRIPTION  = "url_signin";
	private static final String ATTR_URL_CONNEXION    = "url_login";
	private static final String ATTR_URL_DECONNECTION = "url_deco";
	private static final String ATTR_RECHERCHE        = "recherche";
	private static final String ATTR_URL_EMPRUNT      = "url_emprunt";
	private static final String ATTR_URL_AUTEUR       = "url_auteur";
	private static final String ATTR_VOIR_PLUS        = "url_voirplus";
	private static final String ATTR_LISTE            = "liste_Livre";
	
	private static final String CHAMP_TITRE    = "titre";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	//init
	public void init() {
		
	}
	
	public Recherche() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs = ConnectorSQL.SearchLivreByTitle("");
		
		// Liste des livres
		List<Livre> listeLivre = new ArrayList<Livre>();
		
		// Ajout des données pour chaques livre récupéré de la requête
		try {
			while (rs.next()) {
				// Creation d'un livre
				Livre l = new Livre();
				LivreAction.initializeLivre(l, rs);
				// Ajout livre à la liste
				listeLivre.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ConnectorSQL.close();
		
		request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_URL_DECONNECTION, URL_DECONNECTION);
		request.setAttribute(ATTR_URL_INSCRIPTION, URL_INSCRIPTION);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		request.setAttribute(ATTR_URL_EMPRUNT, URL_EMPRUNT);
		request.setAttribute(ATTR_URL_AUTEUR, URL_AUTEUR);
		request.setAttribute(ATTR_RECHERCHE, "");
		request.setAttribute(ATTR_VOIR_PLUS, URL_VOIR_PLUS);
		request.setAttribute(ATTR_LISTE, listeLivre);
		
		getServletContext().getRequestDispatcher(VUE_RECHERCHER).forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupère les valeurs des inputs du formulaire
		String titre = FormFunctionGeneral.getValeurChamp(request, CHAMP_TITRE);
		
		if (titre == null) {
			titre = "";
		}
		
		ResultSet rs = ConnectorSQL.SearchLivreByTitle(titre);
		
		// Liste des livres
		List<Livre> listeLivre = new ArrayList<Livre>();
		
		// Ajout des données pour chaques livre récupéré de la requête
		try {
			while (rs.next()) {
				// Creation d'un livre
				Livre l = new Livre();
				LivreAction.initializeLivre(l, rs);
				// Ajout livre à la liste
				listeLivre.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ConnectorSQL.close();
		
		request.setAttribute(ATTR_URL_MAIN, URL_ACCUEIL);
		request.setAttribute(ATTR_URL_DECONNECTION, URL_DECONNECTION);
		request.setAttribute(ATTR_URL_INSCRIPTION, URL_INSCRIPTION);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		request.setAttribute(ATTR_URL_EMPRUNT, URL_EMPRUNT);
		request.setAttribute(ATTR_URL_AUTEUR, URL_AUTEUR);
		request.setAttribute(ATTR_RECHERCHE, titre);
		request.setAttribute(ATTR_VOIR_PLUS, URL_VOIR_PLUS);
		request.setAttribute(ATTR_LISTE, listeLivre);
		
		getServletContext().getRequestDispatcher(VUE_RECHERCHER).forward(request,response);
	}
}
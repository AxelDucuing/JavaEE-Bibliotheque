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

/**
 * Servlet implementation class MaPremiereServlet
 */
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_ACCUEIL    = "/WEB-INF/index.jsp";
	
	private static final String URL_ACCUEIL       = "index";
	private static final String URL_DECONNECTION  = "deconnexion";
	private static final String URL_INSCRIPTION   = "inscription";
	private static final String URL_CONNEXION     = "connexion";
	private static final String URL_SEARCH        = "rechercher";
	private static final String URL_EMPRUNT       = "liste-emprunt";
	private static final String URL_VOIR_PLUS     = "voir-plus";
	
	private static final String ATTR_URL_MAIN         = "url_main";
	private static final String ATTR_URL_INSCRIPTION  = "url_signin";
	private static final String ATTR_URL_CONNEXION    = "url_login";
	private static final String ATTR_URL_DECONNECTION = "url_deco";
	private static final String ATTR_VOIR_PLUS        = "url_voirplus";
	private static final String ATTR_URL_SEARCH       = "rechercher";
	private static final String ATTR_URL_EMPRUNT      = "url_emprunt";
	private static final String ATTR_LISTE            = "liste_Livre";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	//init
	public void init() {
		
	}
	
	public Accueil() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs = ConnectorSQL.selectTenLast();
		
		// Liste des livres
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
		request.setAttribute(ATTR_URL_INSCRIPTION, URL_INSCRIPTION);
		request.setAttribute(ATTR_URL_CONNEXION, URL_CONNEXION);
		request.setAttribute(ATTR_URL_SEARCH, URL_SEARCH);
		request.setAttribute(ATTR_URL_EMPRUNT, URL_EMPRUNT);
		request.setAttribute(ATTR_VOIR_PLUS, URL_VOIR_PLUS);
		request.setAttribute(ATTR_LISTE, listeLivre);
		
		getServletContext().getRequestDispatcher(VUE_ACCUEIL).forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
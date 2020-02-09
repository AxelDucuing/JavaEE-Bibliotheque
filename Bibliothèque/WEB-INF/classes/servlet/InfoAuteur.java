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
import beans.Auteur;

import objet_metier.ConnectorSQL;
import objet_metier.LivreAction;
import objet_metier.AuteurAction;

/**
 * Servlet implementation class MaPremiereServlet
 */
public class InfoAuteur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String VUE_AUTEUR       = "/WEB-INF/auteur.jsp";
	
	private static final String URL_ACCUEIL       = "index";
	private static final String URL_DECONNECTION  = "deconnexion";
	private static final String URL_INSCRIPTION   = "inscription";
	private static final String URL_CONNEXION     = "connexion";
	private static final String URL_VOIR_PLUS     = "voir-plus";
	private static final String URL_EMPRUNT       = "liste-emprunt";
	
	private static final String ATTR_GETURL           = "id";
	
	private static final String ATTR_URL_MAIN         = "url_main";
	private static final String ATTR_URL_INSCRIPTION  = "url_signin";
	private static final String ATTR_URL_CONNEXION    = "url_login";
	private static final String ATTR_URL_DECONNECTION = "url_deco";
	private static final String ATTR_VOIR_PLUS        = "url_voirplus";
	private static final String ATTR_URL_EMPRUNT      = "url_emprunt";
	private static final String ATTR_AUTEUR           = "auteur";
	private static final String ATTR_LISTE            = "liste_Livre";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	//init
	public void init() {
		
	}
	
	public InfoAuteur() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupération de l'id de l'auteur passer dans l'URL
		int id_auteur = Integer.parseInt(request.getParameter(ATTR_GETURL));
		
		Auteur auteur = new Auteur();
		
		ResultSet rs1 = ConnectorSQL.selectAuteurById(id_auteur);
		// Ajout des données pour l'auteur récupéré de la requête
		try {
			while (rs1.next()) {
				AuteurAction.initializeAuteur(auteur, rs1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet rs2 = ConnectorSQL.selectLivresFromAuteurById(auteur.getId());
		
		// Liste des livres
		List<Livre> listeLivre = new ArrayList<Livre>();
		
		// Ajout des données pour chaques livre récupéré de la requête
		try {
			while (rs2.next()) {
				Livre l = new Livre();
				LivreAction.initializeLivre(l, rs2);
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
		request.setAttribute(ATTR_VOIR_PLUS, URL_VOIR_PLUS);
		request.setAttribute(ATTR_URL_EMPRUNT, URL_EMPRUNT);
		request.setAttribute(ATTR_AUTEUR, auteur);
		request.setAttribute(ATTR_LISTE, listeLivre);
		
		getServletContext().getRequestDispatcher(VUE_AUTEUR).forward(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
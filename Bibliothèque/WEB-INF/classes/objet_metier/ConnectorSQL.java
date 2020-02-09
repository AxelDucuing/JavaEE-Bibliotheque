package objet_metier;

import java.sql.*;

import beans.Membre;

public class ConnectorSQL {
	private static final String HOST = "localhost";
	private static final String PORT = ":3306";
	private static final String DATABASE = "/Bibliotheque";
	private static final String URL = "jdbc:mysql://" + HOST + PORT + DATABASE;

	private static final String USER = "biblio";
	private static final String PASSWORD = "1234512345";
	
	// ######################## LIVRE ########################
	private static final String TABLE_LIVRE      = "LIVRE";
	private static final String LIVRE_ID_LIVRE   = "id_livre";
	private static final String LIVRE_TITRE      = "titre";
	
	// ######################## AUTEUR #######################
	private static final String TABLE_AUTEUR     = "AUTEUR";
	private static final String AUTEUR_ID_AUTEUR = "id_auteur";
	
	// ######################## MEMBRE #######################
	private static final String TABLE_MEMBRE     = "MEMBRES";
	private static final String MEMBRE_ID_MEMBRE = "id_membre";
	private static final String MEMBRE_MDP       = "mdp";
	private static final String MEMBRE_TEL       = "telephone";
	private static final String MEMBRE_MAIL 	 = "adresse_mail";
	
	// ######################## ROLE #######################
	private static final String TABLE_ROLE       = "ROLE";
	private static final String ROLE_ID_ROLE     = "id_role";
	private static final String ROLE_NOM         = "nom";
	
	private static Connection con = null;
	
	static PreparedStatement pstmt;
	static ResultSet rs = null;
	
	/**
	 * Ferme toute connexion à la base de données et driver SQL
	 */
	public static void close() {
		try {
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Connexion à la base de données et driver SQL
	 * @return
	 */
	public static Connection getInstance() {
		if (con == null) {
			/* Chargement du driver JDBC pour MySQL */
			try {
			    Class.forName( "com.mysql.jdbc.Driver" );
			} catch ( ClassNotFoundException e ) {
				e.printStackTrace();
			}
			
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	
	/**
	 * ########################################################
	 * ######################## LIVRE ########################
	 * ########################################################
	 */
	
	/**
	 * Requete SQL : recherche les 10 derniers livre ajouté à la bibliotheque
	 * dont aucun membre n'a fait d'emprunt
	 * @return
	 */
	public static ResultSet selectTenLast() {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_LIVRE + " "
		 + "WHERE " + MEMBRE_ID_MEMBRE + " IS NULL "
 		 + "ORDER BY " + LIVRE_ID_LIVRE + " desc "
 		 + "LIMIT 10;";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : recherche un livre par son Id
	 * @param id : Id du livre
	 * @return
	 */
	public static ResultSet selectLivreById(int id) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_LIVRE + " "
 		 + "WHERE " + LIVRE_ID_LIVRE + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : recherche tout les livres commençant par @param titreResearch
	 * @param titreResearch : Titre recherché
	 * @return
	 */
	public static ResultSet SearchLivreByTitle(String titreResearch) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_LIVRE + " "
 		 + "WHERE " + LIVRE_TITRE + " LIKE '%" + titreResearch + "%' "
 		 + "ORDER BY " + LIVRE_TITRE + " asc; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : recherche tout les livres ayant le même auteur
	 * @param id : Id de l'auteur
	 * @return
	 */
	public static ResultSet selectLivresFromAuteurById(int id) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_LIVRE + " "
 		 + "WHERE " + AUTEUR_ID_AUTEUR + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : recherche tout les livres ayant le même membre
	 * @param id : Id du membre
	 * @return
	 */
	public static ResultSet selectLivreByMembre(int id) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_LIVRE + " "
 		 + "WHERE " + MEMBRE_ID_MEMBRE + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : compte le nombre de livres ayant le même membre
	 * @param id : Id du membre
	 * @return
	 */
	public static boolean checkNombreLivreEmprunt(int id) {
		Connection con = getInstance();
		String requete = "SELECT COUNT(" + LIVRE_ID_LIVRE + ") AS nombre "
		 + "FROM " + TABLE_LIVRE + " "
 		 + "WHERE " + MEMBRE_ID_MEMBRE + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		boolean result = false;
		// Verifie le nombre d'entrée trouvé
		try {
			while (rs.next()) {
				if (rs.getInt("nombre") < 5) {
					result = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Requete SQL : ajoute le livre au membre
	 * @param id_membre : id du membre
	 * @param id_livre : id du livre
	 * @return
	 */
	public static int AddLivreToMember(int id_membre, int id_livre) {
		int result = 0;
		Connection con = getInstance();
		String requete = "UPDATE  " + TABLE_LIVRE +  " "
		 + "SET " + MEMBRE_ID_MEMBRE + " = " + id_membre + " " 
		 + "WHERE " + LIVRE_ID_LIVRE + " = " + id_livre + ";";
		try{
			pstmt = con.prepareStatement(requete);
			result = pstmt.executeUpdate(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Requete SQL : retire le membre du livre
	 * @param id_livre : id du livre
	 * @return
	 */
	public static int SupprLivreToMember(int id_livre) {
		int result = 0;
		Connection con = getInstance();
		String requete = "UPDATE  " + TABLE_LIVRE +  " "
		 + "SET " + MEMBRE_ID_MEMBRE + " = null " 
		 + "WHERE " + LIVRE_ID_LIVRE + " = " + id_livre + ";";
		try{
			pstmt = con.prepareStatement(requete);
			result = pstmt.executeUpdate(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ########################################################
	 * ######################## AUTEUR ########################
	 * ########################################################
	 */
	
	/**
	 * Requete SQL : recherche un auteur par son Id
	 * @param id : Id de l'auteur à chercher
	 * @return
	 */
	public static ResultSet selectAuteurById(int id) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_AUTEUR + " "
 		 + "WHERE " + AUTEUR_ID_AUTEUR + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * ########################################################
	 * ######################## MEMBRE ########################
	 * ########################################################
	 */
	
	/**
	 * Requete SQL : recherche un membre par son Id
	 * @param id : Id du membre à chercher
	 * @return
	 */
	public static ResultSet selectMembreById(int id) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_MEMBRE + " "
 		 + "WHERE " + MEMBRE_ID_MEMBRE + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : selectionne un membre par son mail
	 * @param mail : mail du membre à rechercher
	 * @return
	 */
	public static ResultSet selectMemberByMail(String mail) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_MEMBRE + " "
 		 + "WHERE " + MEMBRE_MAIL + " = '" + mail + "'; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : ajoute un membre
	 * @param new_membre : nouveau membre
	 * @return
	 */
	public static int insertMembre(Membre new_membre) {
		int result = 0;
		Connection con = getInstance();
		String requete = "INSERT INTO " + TABLE_MEMBRE +  " "
		 + "(" 
		 	+ MEMBRE_MDP + ", "
		 	+ MEMBRE_MAIL + ", "
		 	+ MEMBRE_TEL + ", "
		 	+ ROLE_ID_ROLE + ") "
		 + "VALUES (" 
		 	+ " '" + new_membre.getMdp() + "',"
		 	+ " '" + new_membre.getE_mail() + "',"
		 	+ " '" + new_membre.getTelephone() + "',"
		 	+ " " + new_membre.getRole().getId() + "); ";
		try{
			pstmt = con.prepareStatement(requete);
			result = pstmt.executeUpdate(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Requete SQL : verifie si un membre possède déjà la mail
	 * @param mail  : mail à vérifier
	 * @param choix : 0 --> Verifie si le mail existe (connexion)
	 * 				  1 --> Verifie si le mail existe déjà (inscription)
	 * @return
	 */
	public static boolean VerifMailMembre(String mail, int choix) {
		Connection con = getInstance();
		String requete = "SELECT COUNT(" + MEMBRE_MAIL + ") AS nombre "
		 + "FROM " + TABLE_MEMBRE + " "
 		 + "WHERE " + MEMBRE_MAIL + " = '" + mail + "'; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		boolean result = false;
		// Verifie le nombre d'entrée trouvé
		try {
			while (rs.next()) {
				switch (choix) {
					case 0 :
						// L'adresse mail existe
						if (rs.getInt("nombre") != 0) {
							result = true;
						}
						break;
					
					case 1 :
						// L'adresse mail recherché n'existe pas
						if (rs.getInt("nombre") == 0) {
							result = true;
						}
						break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Requete qui vérifie si le mail et le mot de passe corresponde à un membre
	 * @param mdp  : mot de passe du membre à vérifier
	 * @param mail : mail du membre à vérifier
	 * @return
	 */
	public static boolean VerifMdpMailMembre(String mdp, String mail) {
		ResultSet rs2 = selectMemberByMail(mail);
		String mdpNormal = "";
		
		try {
			while (rs2.next()) {
				mdpNormal = rs2.getString(MEMBRE_MDP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		boolean result = false;
		
		if (mdp.compareTo(mdpNormal) == 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * ########################################################
	 * ########################  ROLE  ########################
	 * ########################################################
	 */
	
	/**
	 * Requete SQL : recherche le role du membre
	 * @param id : Id du membre
	 * @return
	 */
	public static ResultSet selectRoleByIdMember(int id) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_ROLE + " "
 		 + "WHERE " + ROLE_ID_ROLE + " = " + id + "; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Requete SQL : recherche le role par le nom
	 * @param nom : nom du role
	 * @return
	 */
	public static ResultSet selectRoleMember(String nom) {
		Connection con = getInstance();
		String requete = "SELECT * "
		 + "FROM " + TABLE_ROLE + " "
 		 + "WHERE " + ROLE_NOM + " = '" + nom + "'; ";
		try{
			pstmt = con.prepareStatement(requete);
			rs = (ResultSet) pstmt.executeQuery(requete);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}
}

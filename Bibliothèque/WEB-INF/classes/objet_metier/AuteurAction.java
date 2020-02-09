package objet_metier;

import java.sql.*;
import java.sql.SQLException;

import beans.Auteur;

public class AuteurAction {
	
	private static final String COLUMN_ID_AUTEUR = "id_auteur";
	private static final String COLUMN_NOM       = "nom";
	private static final String COLUMN_PRENOM    = "prenom";
	private static final String COLUMN_BIO       = "biographie";
	
	/**
	 * Remplie les données d'un objet auteur avec les résultats d'une requête
	 * @param a : auteur à remplir
	 * @param rs : résultat de la requête
	 */
	public static void initializeAuteur(Auteur a, ResultSet rs) {
		try {
			a.setId(rs.getInt(COLUMN_ID_AUTEUR));
			a.setNom(rs.getString(COLUMN_NOM));
			a.setPrenom(rs.getString(COLUMN_PRENOM));
			a.setBiographie(rs.getString(COLUMN_BIO));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

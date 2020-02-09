package objet_metier;

import java.sql.*;
import java.sql.SQLException;

import beans.Role;

public class RoleAction {
	
	private static final String COLUMN_ID_ROLE   = "id_role";
	private static final String COLUMN_NOM       = "nom";
	
	/**
	 * Remplie les données d'un objet role avec les résultats d'une requête
	 * @param r : role à remplir
	 * @param rs : résultat de la requête
	 */
	public static void initializeRole(Role r, ResultSet rs) {
		try {
			r.setId(rs.getInt(COLUMN_ID_ROLE));
			r.setNom(rs.getString(COLUMN_NOM));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

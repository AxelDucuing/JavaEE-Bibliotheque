package objet_metier;

import java.sql.*;

import beans.Membre;
import beans.Role;

public class MembreAction {
	
	private static final String COLUMN_ID_MEMBRE = "id_membre";
	private static final String COLUMN_MDP       = "mdp";
	private static final String COLUMN_TEL       = "telephone";
	private static final String COLUMN_MAIL 	    = "adresse_mail";
	
	/**
	 * Remplie les données d'un objet membre avec les résultats d'une requête
	 * @param m : membre à remplir
	 * @param rs : résultat de la requête
	 */
	public static void initializeMembre(Membre m, ResultSet rs) {
		try {
			m.setId(rs.getInt(COLUMN_ID_MEMBRE));
			m.setE_mail(rs.getString(COLUMN_MAIL));
			m.setMdp(rs.getString(COLUMN_MDP));
			m.setTelephone(rs.getString(COLUMN_TEL));
			
			ResultSet rs2 = ConnectorSQL.selectRoleByIdMember(rs.getInt(COLUMN_ID_MEMBRE));
			// Creation d'un role
			Role role = new Role();
			try {
				while (rs2.next()) {
					RoleAction.initializeRole(role, rs2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			m.setRole(role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

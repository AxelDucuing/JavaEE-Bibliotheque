package objet_metier;

import java.sql.*;

import objet_metier.AuteurAction;
import objet_metier.MembreAction;
import objet_metier.ConnectorSQL;

import beans.Livre;
import beans.Auteur;
import beans.Membre;

public class LivreAction {
	
	private static final String COLUMN_ID_LIVRE  = "id_livre";
	private static final String COLUMN_TITRE     = "titre";
	private static final String COLUMN_PARUTION  = "date_parution";
	private static final String COLUMN_RESUME    = "resume";
	private static final String COLUMN_NBR_PAGE  = "nombre_pages";
	
	private static final String COLUMN_ID_AUTEUR = "id_auteur";
	
	private static final String COLUMN_ID_MEMBRE = "id_membre";
	
	/**
	 * Remplie les données d'un objet livre avec les résultats d'une requête
	 * @param l : livre à remplir
	 * @param rs : résultat de la requête
	 */
	public static void initializeLivre(Livre l, ResultSet rs) {
		try {
			l.setId(rs.getInt(COLUMN_ID_LIVRE));
			l.setTitre(rs.getString(COLUMN_TITRE));
			l.setResume(rs.getString(COLUMN_RESUME));
			l.setNombre_pages(rs.getInt(COLUMN_NBR_PAGE));
			l.setParution(rs.getDate(COLUMN_PARUTION));
			
			ResultSet rs2 = ConnectorSQL.selectAuteurById(rs.getInt(COLUMN_ID_AUTEUR));
			// Creation d'un auteur
			Auteur auteur = new Auteur();
			try {
				while (rs2.next()) {
					AuteurAction.initializeAuteur(auteur, rs2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			l.setAuteur(auteur);
			
			// Creation d'un membre
			Membre membre = new Membre();
			if (rs.getInt(COLUMN_ID_MEMBRE) != 0) {
				ResultSet rs3 = ConnectorSQL.selectMembreById(rs.getInt(COLUMN_ID_MEMBRE));
				
				try {
					while (rs3.next()) {
						MembreAction.initializeMembre(membre, rs3);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				l.setMembre(membre);
			} else {
				membre.setId(0);
				l.setMembre(membre);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

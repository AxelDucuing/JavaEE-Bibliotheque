package beans;

import java.util.Date;

import beans.Auteur;
import beans.Membre;

public class Livre
{
	private int id;
	private String titre;
	private Date parution;
	private String resume;
	private int nombre_pages;
	private Auteur auteur;
	private Membre membre;
	
	public Livre(){}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getTitre() 
	{
		return titre;
	}
	
	public void setTitre(String titre)
	{
		this.titre = titre;
	}
	
	public Date getParution() {
		return parution;
	}

	public void setParution(Date parution) {
		this.parution = parution;
	}
	
	public String getResume()
	{
		return resume;
	}
	
	public void setResume(String resume)
	{
		this.resume = resume;
	}
	
	public int getNombre_pages()
	{
		return nombre_pages;
	}
	
	public void setNombre_pages(int nombre_pages)
	{
		this.nombre_pages = nombre_pages;
	}

	public Auteur getAuteur() 
	{
		return auteur;
	}

	public void setAuteur(Auteur auteur) 
	{
		this.auteur = auteur;
	}
	
	public Membre getMembre()
	{
		return membre;
	}

	public void setMembre(Membre membre) 
	{
		this.membre = membre;
	}
}

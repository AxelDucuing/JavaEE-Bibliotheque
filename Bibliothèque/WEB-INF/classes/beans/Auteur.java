package beans;

public class Auteur 
{
	private int id;
	private String nom;
	private String prenom;
	private String biographie;
	
	public Auteur(){}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getNom() 
	{
		return nom;
	}
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	public String getPrenom()
	{
		return prenom;
	}
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	
	public String getBiographie()
	{
		return biographie;
	}
	
	public void setBiographie(String biographie)
	{
		this.biographie = biographie;
	}
}

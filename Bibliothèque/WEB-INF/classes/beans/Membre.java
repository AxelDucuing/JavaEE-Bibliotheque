package beans;

import beans.Role;

public class Membre 
{
	private int id;
	private String mdp;
	private String e_mail;
	private String telephone;
	private Role role;
	
	public Membre(){}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getMdp() 
	{
		return mdp;
	}

	public void setMdp(String mdp) 
	{
		this.mdp = mdp;
	}
	
	public String getE_mail()
	{
		return e_mail;
	}
	
	public void setE_mail(String e_mail)
	{
		this.e_mail = e_mail;
	}
	
	public String getTelephone()
	{
		return telephone;
	}
	
	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}
}

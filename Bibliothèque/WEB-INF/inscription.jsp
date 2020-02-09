
<head>
	<meta charset="UTF-8">
	<title>Bibliothèque - Inscription</title>
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body id="inscription">
	<header>
		<h1><a href="<c:out value="${ url_main }" />">Bibliothèque</a></h1>
		<c:choose>
			<c:when test="${ empty sessionScope.membre_log }">
				<p>
					<a href="<c:out value="${ url_signin }" />">Inscription</a>
					<span></span>
					<a href="<c:out value="${ url_login }" />">Connexion</a>
				</p>
			</c:when>
			<c:otherwise>
				<p>
					<a href="<c:out value="${ url_emprunt }" />"><c:out value="${ sessionScope.membre_log.getE_mail() }" /></a>
					<span></span>
					<a href="<c:out value="${ url_deco }" />">Déconnexion</a>
				</p>
			</c:otherwise>
		</c:choose>
		
	</header>
	
	<h2>Inscription</h2>
	
	<form method="post" action="<c:out value="${ url_signin }" />">

		<p>
			<label for="mail">Mail <span class="requis">*</span></label>
			<input type="email" id="mail" name="mail" 
			value="<c:if test="${ !empty membre.getE_mail() }"><c:out value="${ membre.getE_mail() }"/></c:if>" 
			maxlength="249" 
			required
			/>
			<c:if test="${ !empty form.erreurs['mail'] }"><span class="erreur">${ form.erreurs['mail'] }</span></c:if>
		</p>
		
		<p>
			<label for="mdp">Mot de Passe <span class="requis">*</span></label>
			<input type="text" id="mdp" name="mdp" 
			value="<c:if test="${ !empty membre.getMdp() }"><c:out value="${ membre.getMdp() }"/></c:if>" 
			minlength="6"
			maxlength="249" 
			required
			/>
			<c:if test="${ !empty form.erreurs['mdp'] }"><span class="erreur">${ form.erreurs['mdp'] }</span></c:if>
		</p>
	    
	    <p>
			<label for="tel">N° de téléphone <span class="requis">*</span></label>
			<input type="tel" id="tel" name="tel" 
			value="<c:if test="${ !empty membre.getTelephone() }"><c:out value="${ membre.getTelephone() }"/></c:if>" 
			minlength="10"
			maxlength="10" 
			required
			/>
			<c:if test="${ !empty form.erreurs['tel'] }"><span class="erreur">${ form.erreurs['tel'] }</span></c:if>
		</p>
	    
		<input type="submit" value="S'inscrire">
		<a href="<c:out value="${ url_main }" />">Annuler</a>
	</form>
	
	
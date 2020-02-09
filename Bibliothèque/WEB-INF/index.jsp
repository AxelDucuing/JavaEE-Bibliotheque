
<head>
	<meta charset="UTF-8">
	<title>Bibliothèque - Accueil</title>
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body>
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
	
	<form method="post" action="rechercher" class="recherche">
		<input type="text" id="titre" name="titre" value="" placeholder="Titre du Livre... (laisser vide pour afficher la totalité de la bibliothèque)"/>
		<input class="send" type="submit" value="Rechercher">
	</form>
	
	<div class="resultats">
		 <c:forEach var="item" items="${ liste_Livre }" >
		 	
			<c:choose>
				<c:when test="${ empty sessionScope.membre_log }">
					<a href="<c:out value="${ url_login }" />" > 
						<c:choose>
							<c:when test="${item.getMembre().getId() != 0}">
								<img src="https://img.icons8.com/color/48/000000/cancel.png" alt="Indisponible" title="Indisponible"/>
							</c:when>
							<c:otherwise>
								<img src="https://img.icons8.com/color/48/000000/verified-account.png" alt="Disponible" title="Disponible"/>
							</c:otherwise>
						</c:choose>
						<p><c:out value="${ item.getTitre() }" /></p> 
						<p>Voir Plus >></p>
					</a>
				</c:when>
				<c:otherwise>
					<a href="<c:out value="${ url_voirplus }" />?id=<c:out value="${ item.getId() }" />" > 
						<c:choose>
							<c:when test="${item.getMembre().getId() != 0}">
								<img src="https://img.icons8.com/color/48/000000/cancel.png" alt="Indisponible" title="Indisponible"/>
							</c:when>
							<c:otherwise>
								<img src="https://img.icons8.com/color/48/000000/verified-account.png" alt="Disponible" title="Disponible"/>
							</c:otherwise>
						</c:choose>
						<p><c:out value="${ item.getTitre() }" /></p> 
						<p>Voir Plus >></p>
					</a>
				</c:otherwise>
			</c:choose>
			
		</c:forEach>
	</div>
	
	
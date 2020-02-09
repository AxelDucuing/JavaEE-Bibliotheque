
<head>
	<meta charset="UTF-8">
	<title>Bibliothèque - <c:out value="${ sessionScope.membre_log.getE_mail() }" /></title>
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body id="recherche">
	<header>
		<h1><a href="<c:out value="${ url_main }" />">Bibliothèque</a></h1>
		<p>
			<a href="<c:out value="${ url_emprunt }" />"><c:out value="${ sessionScope.membre_log.getE_mail() }" /></a>
			<span></span>
			<a href="<c:out value="${ url_deco }" />">Déconnexion</a>
		</p>
	</header>
	
	<form method="post" action="rechercher" class="recherche">
		<input type="text" id="titre" name="titre" value="" placeholder="Titre du Livre... (laisser vide pour afficher la totalité de la bibliothèque)"/>
		<input class="send" type="submit" value="Rechercher">
	</form>
	
	<c:if test="${ !empty liste_Livre }">
		<h2>Liste de livres empruntés</h2>
	</c:if>
	
	<c:if test="${ !empty message }">
		<p class="message-livre"><c:out value="${ message }" /></p>
	</c:if>
	
	<c:choose>
		<c:when test="${!empty liste_Livre }">
			<div class="resultats">
				 <c:forEach var="item" items="${ liste_Livre }" >
					
					<div>
						<div>
						
							<img src="https://img.icons8.com/ultraviolet/40/000000/reading.png" alt="En cours de lecture" title="En cours de lecture"/>
						
							<a href="<c:out value="${ url_voirplus }" />?id=<c:out value="${ item.getId() }" />"><c:out value="${ item.getTitre() }" /></a>
							
							- 
							<a href="<c:out value="${ url_auteur }" />?id=<c:out value="${ item.auteur.getId() }" />">
								<c:out value="${ item.auteur.getPrenom() }" /> 
								<c:out value="${ item.auteur.getNom() }" />
							</a>
							
						</div>
						<a href="<c:out value="${ url_voirplus }" />?id=<c:out value="${ item.getId() }" />">Voir Plus >></a>
					</div>
					
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<h2>Vous n'avez aucun livre emprunté</h2>
		</c:otherwise>
	</c:choose>
	
	
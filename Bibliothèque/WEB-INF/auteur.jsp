
<head>
	<meta charset="UTF-8">
	<title>Bibliothèque - <c:out value="${ auteur.getPrenom() }" /> <c:out value="${ auteur.getNom() }" /></title>
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body id="auteur">
	<header>
		<h1><a href="<c:out value="${ url_main }" />">Bibliothèque</a></h1>
		<p>
			<a href="<c:out value="${ url_emprunt }" />"><c:out value="${ sessionScope.membre_log.getE_mail() }" /></a>
			<span></span>
			<a href="<c:out value="${ url_deco }" />">Déconnexion</a>
		</p>
	</header>
	
	<h2><c:out value="${ auteur.getPrenom() }" /> <c:out value="${ auteur.getNom() }" /></h2>
	
	<div>
		<h4>Biographie</h4>
	
		<p><c:out value="${ auteur.getBiographie() }" /></p>
		
		<h4>Liste des oeuvres</h4>
		<div class="resultats">
			 <c:forEach var="item" items="${ liste_Livre }" >
				
				<a href="<c:out value="${ url_voirplus }" />?id=<c:out value="${ item.getId() }" />" > 
				
					<c:choose>
						<c:when test="${ item.getMembre().getId() != 0 }">
							<c:choose>
								<c:when test="${ item.getMembre().getId() == sessionScope.membre_log.getId() }">
									<img src="https://img.icons8.com/ultraviolet/40/000000/reading.png" alt="En cours de lecture" title="En cours de lecture"/>
								</c:when>
								<c:otherwise>
									<img src="https://img.icons8.com/color/48/000000/cancel.png" alt="Indisponible" title="Indisponible"/>
								</c:otherwise>
							</c:choose>
							
						</c:when>
						<c:otherwise>
							<img src="https://img.icons8.com/color/48/000000/verified-account.png" alt="Disponible" title="Disponible"/>
						</c:otherwise>
					</c:choose>
									
					<p><c:out value="${ item.getTitre() }" /></p> 
					<p>Voir Plus >></p>
				</a>
				
			</c:forEach>
		</div>
	</div>
		
		
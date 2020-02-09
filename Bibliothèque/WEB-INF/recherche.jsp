
<head>
	<meta charset="UTF-8">
	<title>
		<c:choose>
			<c:when test="${!empty recherche}">
				<c:choose>
					<c:when test="${!empty liste_Livre}">
						Bibliothèque - Recherches pour : <c:out value="${ recherche }" />
					</c:when>
					<c:otherwise>
						Bibliothèque - Aucun résultats pour : <c:out value="${ recherche }" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				Bibliothèque - Tous les livres
			</c:otherwise>
		</c:choose>
	</title>
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body id="recherche">
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
	
	<c:if test="${ !empty recherche and !empty liste_Livre }">
	<h2>Résultats de recherche pour : <c:out value="${ recherche }" /></h2>
	</c:if>
	
	<c:choose>
		<c:when test="${!empty liste_Livre }">
			<div class="resultats">
				 <c:forEach var="item" items="${ liste_Livre }" >
					<c:choose>
						<c:when test="${ empty sessionScope.membre_log }">
						
							<div>
								<div>
								
									<c:choose>
										<c:when test="${item.getMembre().getId() != 0}">
											<img src="https://img.icons8.com/color/48/000000/cancel.png" alt="Indisponible" title="Indisponible"/>
										</c:when>
										<c:otherwise>
											<img src="https://img.icons8.com/color/48/000000/verified-account.png" alt="Disponible" title="Disponible"/>
										</c:otherwise>
									</c:choose>
									
									<a href="<c:out value="${ url_login }" />">
										<c:out value="${ item.getTitre() }" />
									</a>
									 - 
									<a href="<c:out value="${ url_login }" />">
										<c:out value="${ item.auteur.getPrenom() }" /> 
										<c:out value="${ item.auteur.getNom() }" />
									</a>
									
								</div>
								<a href="<c:out value="${ url_login }" />">Voir Plus >></a>
							</div>
							
						</c:when>
						<c:otherwise>
						
							<div>
								<div>
								
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
								
									<a href="<c:out value="${ url_voirplus }" />?id=<c:out value="${ item.getId() }" />">
										<c:out value="${ item.getTitre() }" />
									</a>
									 - 
									<a href="<c:out value="${ url_auteur }" />?id=<c:out value="${ item.auteur.getId() }" />">
										<c:out value="${ item.auteur.getPrenom() }" /> 
										<c:out value="${ item.auteur.getNom() }" />
									</a>
									
								</div>
								<a href="<c:out value="${ url_voirplus }" />?id=<c:out value="${ item.getId() }" />">Voir Plus >></a>
							</div>
							
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
		<h2>Aucun résultats pour : <c:out value="${ recherche }" /></h2>
		</c:otherwise>
	</c:choose>
	
	

<head>
	<meta charset="UTF-8">
	<title>Bibliothèque - <c:out value="${ Livre.getTitre() }" /></title>
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
</head>
<body id="voir-plus">
	<header>
		<h1><a href="<c:out value="${ url_main }" />">Bibliothèque</a></h1>
		<p>
			<a href="<c:out value="${ url_emprunt }" />"><c:out value="${ sessionScope.membre_log.getE_mail() }" /></a>
			<span></span>
			<a href="<c:out value="${ url_deco }" />">Déconnexion</a>
		</p>
	</header>
	
	<h2><c:out value="${ Livre.getTitre() }" /></h2>
	
	<h3><a href="<c:out value="${ url_auteur }" />?id=<c:out value="${ Livre.getAuteur().getId() }" />"><c:out value="${ Livre.auteur.getPrenom() }" /> <c:out value="${ Livre.auteur.getNom() }" /></a></h3>
	
	<div>
		<p>Nombre de pages : <c:out value="${ Livre.getNombre_pages() }" /></p>
		<p>Date de parution : <c:out value="${ Livre.getParution() }" /></p>
		<h4>Résumé</h4>
	
		<p><c:out value="${ Livre.getResume() }" /></p>
	</div>
	<div class="div-emprunt">
		<c:choose>
			<c:when test="${ !pris }">
				<a href="<c:out value="${ url_emprunt }" />?id=<c:out value="${ Livre.getId() }" />&action=<c:out value="${ action }" />" >Emprunter</a>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${ possede }">
						<a href="<c:out value="${ url_emprunt }" />?id=<c:out value="${ Livre.getId() }" />&action=<c:out value="${ action }" />" >Rendre</a>
					</c:when>
					<c:otherwise>
						<p>Non disponible</p>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
	
	
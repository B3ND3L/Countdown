<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>CountDown</title>
	<style>
	blockquote {
		font-style: italic;
		padding: 20px;
	}

	blockquote footer{
		color:#555;
		font-weight: bold
	}
	</style>
</head>

<body>
	<blockquote>
	&laquo; Il reste
	<%
            String diff = (String) request.getAttribute("diff");
            out.println( diff );
	%>
	avant la fin de ce cours ! &raquo;
	<footer>
	<%
            String parametre = request.getParameter( "author" );
            out.println( parametre );
	%>
	</footer>
	</blockquote>
</body>
</html>

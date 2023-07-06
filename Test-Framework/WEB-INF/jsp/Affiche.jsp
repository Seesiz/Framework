<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    Nom: <% out.println(request.getParameter("nom"));%>
    Prenom: <% out.println(request.getParameter("prenom"));%>
</body>
</html>
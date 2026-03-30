<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Obras</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/contatos.css">
</head>
<body>

<a class="btn-voltar" href="voltar-home" title="Voltar">
    ←
</a>

<a class="btn-novo" href="<%=request.getContextPath()%>/nova-obra">
    Nova Obra
</a>

<h1 class="titulo">Obras</h1>

<div class="container">

<!-- LISTA -->
<div class="lista-container">

    <c:forEach var="obra" items="${obras}">
        <div class="contato-item">

            <div class="contato-info">
                <span class="nome">${obra.nome}</span>
            </div>

            <a class="btn-excluir" href="editar-obra?idObra=${obra.idObra}" title="Excluir">✕</a>
            
            <a class="btn-excluir" href="editar-obra?idObra=${obra.idObra}" title="Editar">Editar</a>

        </div>
    </c:forEach>

</div>
</div>

</body>
</html>
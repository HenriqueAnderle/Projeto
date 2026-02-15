<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Contatos</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/contatos.css">
</head>
<body>

<!-- VOLTAR -->
<a class="btn-voltar" href="voltar-home" title="Voltar">
    ←
</a>

<!-- NOVO CONTATO -->
<a class="btn-novo" href="<%=request.getContextPath()%>/novo-contato">
    Novo Contato
</a>

<h1 class="titulo">Contatos</h1>

<!-- LISTA -->
<div class="lista-container">

    <c:forEach var="contato" items="${contatos}">
        <div class="contato-item">

            <div class="contato-info">
                <span class="nome">${contato.nome}</span>
                <span class="email">${contato.email}</span>
            </div>

            <a class="btn-excluir"
               href="deletar-contato?idContato=${contato.idContato}"
               title="Excluir">
                ✕
            </a>

        </div>
    </c:forEach>

</div>

</body>
</html>
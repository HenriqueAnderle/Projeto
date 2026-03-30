<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contatos</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/contatos.css">
</head>
<body>


<a class="btn-voltar" href="voltar-home" title="Voltar">
    ←
</a>


<a class="btn-novo" href="<%=request.getContextPath()%>/novo-contato">
    Novo Contato
</a>

<h1 class="titulo">Contatos</h1>

<div class="container">

    <div class="lista-container">
        <c:forEach var="contato" items="${contatos}">
            <div class="contato-item">

                <div class="contato-info">
                    <span class="nome">${contato.nome}</span>
                    <span class="email">${contato.email}</span>
                </div>

                <a class="btn-excluir" href="deletar-contato?idContato=${contato.idContato}" title="Excluir">✕</a>
				
				<a class="btn-excluir" href="atualizar-contato?idContato=${contato.idContato}" title="Editar">Editar</a>
				
            </div>
        </c:forEach>
    </div>

</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Obra</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/form-contato.css">
</head>
<body>

<!-- VOLTAR -->
<a class="btn-voltar" href="home.jsp" title="Voltar">
    ←
</a>

<div class="container">

    <div class="card">

        <form action="inserir-obra" method="post">

            <div class="input-group">
                <span class="icon">👤</span>
                <input type="text" name="nome" placeholder="Nome" value="${obra.nome}" required>
            </div>

            <button type="submit" class="btn-primario">
                Nova Obra
            </button>

        </form>

    </div>

</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Criar Conta</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/cadastro.css">
</head>
<body>

<!-- VOLTAR -->
<a class="btn-voltar" href="<%=request.getContextPath()%>/index.jsp" title="Voltar">
    ←
</a>

<div class="container">

    <div class="card">

        <form action="inserir-usuario" method="post">

            <div class="input-group">
                <span class="icon">👤</span>
                <input type="text" name="nome" placeholder="Nome" required>
            </div>

            <div class="input-group">
                <span class="icon">@</span>
                <input type="email" name="email" placeholder="E-mail" required>
            </div>

            <div class="input-group">
                <span class="icon">🔒</span>
                <input type="password" name="senha" placeholder="Senha" required>
            </div>

            <button type="submit" class="btn-primario">
                Cadastrar
            </button>

        </form>

    </div>

</div>

</body>
</html>
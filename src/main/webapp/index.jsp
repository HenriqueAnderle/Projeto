<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Projeto</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/login.css">
</head>
<body>

<a class="btn-cadastro" href="<%=request.getContextPath()%>/novo-usuario">
    Cadastro
</a>

<div class="login-container">

    <div class="login-card">

        <form action="logar-usuario" method="post">

            <div class="input-group">
                <span class="icon">@</span>
                <input type="email" name="email" placeholder="E-mail" required>
            </div>

            <div class="input-group">
                <span class="icon">🔒</span>
                <input type="password" name="senha" placeholder="Senha" required>
            </div>

            <button type="submit" class="btn-login">
                Avançar
            </button>

        </form>

    </div>

</div>

</body>
</html>
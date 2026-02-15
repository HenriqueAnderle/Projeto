<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
<head>
<title>Projeto</title>
</head>
<body>

<h1>Login</h1>

<form action="logar-usuario" method="post">

<label>E-mail</label>
<input type="email" id="email" name="email" placeholder="Digite seu E-Mail">

<label>Senha</label>
<input type="password" id="senha" name="senha" placeholder="Digite sua Senha">

<button type="submit">Salvar</button>
<button type="reset">Limpar formulário</button>

</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Criar Conta</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/cadastro.css">
</head>
<body>

<!-- VOLTAR -->
<a class="btn-voltar" href="<%=request.getContextPath()%>/index.jsp" title="Voltar" aria-label="Voltar">
    ←
</a>

<div class="container">
    <div class="card">
        <h2>Criar Conta</h2>
        
        <form action="inserir-usuario" method="post" id="formCadastro">
            <div class="input-group">
                <span class="icon">👤</span>
                <input type="text" name="nome" placeholder="Nome completo" required autocomplete="name">
            </div>

            <div class="input-group">
                <span class="icon">@</span>
                <input type="email" name="email" placeholder="E-mail" required autocomplete="email">
            </div>

            <div class="input-group">
                <span class="icon">🔒</span>
                <input type="password" name="senha" placeholder="Senha segura" required autocomplete="new-password" minlength="6">
            </div>

            <button type="submit" class="btn-primario" id="btnCadastrar">
                Cadastrar
            </button>
        </form>
    </div>
</div>

<script>
    // Feedback visual para o botão
    document.getElementById('formCadastro').addEventListener('submit', function() {
        const btn = document.getElementById('btnCadastrar');
        btn.textContent = 'Criando...';
        btn.classList.add('loading');
    });
</script>

</body>
</html>
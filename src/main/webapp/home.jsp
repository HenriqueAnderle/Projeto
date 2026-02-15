<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Home</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/home.css">
</head>
<body>

<!-- LOGOUT -->
<a class="btn-logout" href="<%=request.getContextPath()%>/deslogar-usuario" title="Sair">
    ➜
</a>

<!-- CONTEÚDO CENTRAL -->
<div class="home-container">

    <h1>Bem-vindo, ${usuarioLogado.nome}!</h1>

    <div class="acoes">
        <a class="btn-acao" href="<%=request.getContextPath()%>/contatos">Contatos</a>
        
        <a class="btn-acao" href="<%=request.getContextPath()%>/obras">Obras</a>

        <a class="btn-acao" href="<%=request.getContextPath()%>/novo-relatorio">Novo Relatório Vistoria</a>
        
        <a class="btn-acao" href="<%=request.getContextPath()%>/nova-solicitacao">Solicitação de Materiais Serviços</a>
        
    </div>

</div>

<!-- TABELA -->

<!--
<div class="tabela-container">
    <table>
        <thead>
            <tr>
                <th>Assunto do E-mail</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="emailEnviado" items="${emailsEnviados}">
                <tr>
                    <td>${emailEnviado.assunto}</td>
                    <td>
                        <a class="btn-deletar"
                           href="deletar-emailEnviado?idEmailEnviado=${emailEnviado.idEmailEnviado}">
                            Deletar
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
-->
</body>
</html>
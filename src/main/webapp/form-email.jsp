<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Contatos</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/form-email.css">
</head>
<body>

<a class="btn-voltar" href="voltar-form" title="Voltar">←</a>



<div class="container">

    <!-- CONTATOS NÃO ADICIONADOS -->
    <h2>Contatos</h2>

    <div class="lista">
        <c:forEach var="contato" items="${contatosNaoAdicionados}">
            <div class="item">
                <div class="info">
                    <strong>${contato.nome}</strong>
                    <span>${contato.email}</span>
                </div>

                <a class="acao adicionar"
                   href="adicionar-contato?idContato=${contato.idContato}">
                    +
                </a>
            </div>
        </c:forEach>
    </div>

    <!-- CONTATOS ADICIONADOS -->
    <h2>Contatos Adicionados</h2>

    <div class="lista">
        <c:forEach var="contato" items="${contatosAdicionados}">
            <div class="item">
                <div class="info">
                    <strong>${contato.nome}</strong>
                    <span>${contato.email}</span>
                </div>

                <a class="acao remover"
                   href="remover-contato?idContato=${contato.idContato}">
                    ✕
                </a>
            </div>
        </c:forEach>
    </div>
    
    <c:if test="${not empty erroEmail}">
    <div id="erroEmail" 
         style="color: red; font-weight: bold; margin-bottom: 10px;">
        ${erroEmail}
    </div>

    <script>
        setTimeout(function() {
            var alerta = document.getElementById("erroEmail");
            if (alerta) {
                alerta.style.display = "none";
            }
        }, 3000);
    </script>
	</c:if>

    <!-- FORM DESTINATÁRIOS -->
    <form action="inserir-email" method="post">

        <h2>Outros Destinatários</h2>

        <div id="destinatariosContainer"></div>

        <button type="button" class="btn-add" onclick="adicionarDestinatario()">+</button>

        <div class="botoes">
            <button type="submit">Enviar Email</button>
        </div>

        <!-- TEMPLATE -->
        <div id="destinatarioTemplate" class="destinatario" style="display:none">
            <label>E-mail</label>
            <input type="email" data-name="email" placeholder="Digite o e-mail">

            <button type="button" class="removerDestinatario">✕</button>
        </div>

    </form>

<c:if test="${not empty sessionScope.pdfGerado}">
    <h2>Visualização do PDF</h2>
    <iframe 
        src="${pageContext.request.contextPath}/visualizar-pdf"
        width="100%" 
        height="600px"
        style="border:1px solid #ccc;">
    </iframe>
</c:if>

</div>

<script>
function adicionarDestinatario() {

    const container = document.getElementById("destinatariosContainer");
    const template = document.getElementById("destinatarioTemplate");

    const clone = template.cloneNode(true);
    clone.style.display = "block";
    clone.removeAttribute("id");

    clone.querySelector('[data-name="email"]').name = "destinatarios[].email";
    clone.querySelector('.removerDestinatario').onclick = () => clone.remove();

    container.appendChild(clone);
}
</script>

</body>
</html>
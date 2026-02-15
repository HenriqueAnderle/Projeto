<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Solicitação de Materiais/Serviços</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/form-solicitacao.css">
</head>
<body>

<!-- VOLTAR -->
<a class="btn-voltar" href="voltar-home" title="Voltar">←</a>

<h1 class="titulo">Solicitação de Materiais/Serviços</h1>

<div class="container">
<div class="card">

<form action="inserir-solicitacao" method="post" enctype="multipart/form-data">

    <!-- DADOS GERAIS -->
    
    <div class="form-group">
        <label>Logo:</label>
        <div class="opcoes-logo">
            <label class="opcao">
                <input type="radio" name="logo" value="true" ${solicitacaoMateriaisServicos.logo ? 'checked' : ''}>
                <img src="img/idheaEngenharia.png" alt="IdheaEngenharia" class="logo-box">
                <span>Idhea Engenharia</span>
            </label>
            <label class="opcao">
                <input type="radio" name="logo" value="false" ${!solicitacaoMateriaisServicos.logo ? 'checked' : ''}>
                <img src="img/idheaMannz.png" alt="IdheaMannz" class="logo-box">
                <span>IdheaMannz</span>
            </label>
        </div>
    </div>
    
    <div class="form-group">
	    <label>Nome da Obra :</label>
	    <select name="obra">
	        <c:forEach var="obra" items="${obras}">
	            <option value="${obra.nome}"
	                ${not empty solicitacaoMateriaisServicos and 
	                  not empty solicitacaoMateriaisServicos.obra and 
	                  solicitacaoMateriaisServicos.obra.nome == obra.nome ? 'selected="selected"' : ''}>
	                ${obra.nome}
	            </option>
	        </c:forEach>
	    </select>
	</div>
    
    <div class="form-group">
        <label>Solicitante :</label>
        <select name="solicitante">
            <c:forEach var="solicitante" items="${solicitante}">
                <option value="${solicitante}" ${solicitacaoMateriaisServicos.solicitante == solicitante ? 'selected' : ''}>
                    ${solicitante.descricao}
                </option>
            </c:forEach>
        </select>
    </div>

    <!-- SOLICITAÇÕES -->
    <h2>Solicitações</h2>

    <div id="solicitacaoContainer">
        <c:if test="${not empty solicitacoes}">
            <c:forEach var="solicitacao" items="${solicitacoes}" varStatus="status">
                <div class="solicitacao-item" style="display: block;">
                    <h3>Solicitação</h3>
                    
                    <div class="form-group">
                        <label>Material/Serviço :</label>
                        <input type="text" name="solicitacoes[].materialServico" value="${solicitacao.materialServico}">
                    </div>

                    <div class="form-group">
                        <label>Unidade :</label>
                        <select name="solicitacoes[].unidade">
                            <c:forEach var="unidade" items="${unidade}">
                                <option value="${unidade}" ${solicitacao.unidade == unidade ? 'selected' : ''}>${unidade.descricao}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Quantidade :</label>
                        <input type="text" name="solicitacoes[].quantidade" value="${solicitacao.quantidade}">
                    </div>

                    <div class="form-group">
                        <label>Descrição :</label>
                        <input type="text" name="solicitacoes[].descricao" value="${solicitacao.descricao}">
                    </div>

                    <button type="button" class="remover" onclick="removerSolicitacao(this)">✕</button>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <button type="button" class="btn-add" onclick="adicionarSolicitacao()">+</button>

    <div class="form-group">
        <br>
        <label>Etapa Da Obra :</label>
        <select name="etapaDaObra">
            <c:forEach var="etapaDaObra" items="${etapaDaObra}">
                <option value="${etapaDaObra}" ${solicitacaoMateriaisServicos.etapaDaObra == etapaDaObra ? 'selected' : ''}>
                    ${etapaDaObra.descricao}
                </option>
            </c:forEach>
        </select>
    </div>
        
    <div class="form-group">
    <label>Imagem :</label>
    <c:if test="${not empty solicitacaoMateriaisServicos.imagem}">
        <div>Imagem atual: ${solicitacaoMateriaisServicos.imagem}</div>
    </c:if>
    <input type="file" name="imagem" accept="image/*" capture="environment">
	</div>
    
   	<div class="form-group">
    <label>Previsão de Chegada :</label>
    <input type="datetime-local" name="previsaoDeChegada" 
           value="${empty solicitacaoMateriaisServicos.previsaoChegada ? '' : solicitacaoMateriaisServicos.previsaoChegada}">
	</div>
    
    <div class="form-group">
        <label>Observação :</label>
        <textarea name="observacao">${solicitacaoMateriaisServicos.observacao}</textarea>
    </div>

    <!-- AÇÕES -->
    <div class="acoes-finais">
        <button type="submit" class="btn-primario">Salvar</button>
    </div>

</form>

</div>
</div>

<!-- ===================== TEMPLATES ===================== -->

<!-- TEMPLATE SOLICITAÇÃO -->
<div id="solicitacaoTemplate" style="display:none">
    <h3>Solicitação</h3>

    <div class="form-group">
        <label>Material/Serviço :</label>
        <input type="text" data-name="materialServico">
    </div>

    <div class="form-group">
        <label>Unidade :</label>
        <select data-name="unidade">
            <c:forEach var="unidade" items="${unidade}">
                <option value="${unidade}">${unidade.descricao}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label>Quantidade :</label>
        <input type="text" data-name="quantidade">
    </div>

    <div class="form-group">
        <label>Descrição :</label>
        <input type="text" data-name="descricao">
    </div>

    <button type="button" class="remover" data-name="remove">✕</button>
</div>

<!-- ===================== JS ===================== -->

<script>
let solicitacaoCounter = ${not empty solicitacoes ? solicitacoes.size() : 0};

function adicionarSolicitacao() {
    const container = document.getElementById("solicitacaoContainer");
    const template = document.getElementById("solicitacaoTemplate");

    const clone = template.cloneNode(true);
    clone.style.display = "block";
    clone.removeAttribute("id");

    // Definir nomes únicos para os campos
    clone.querySelector('[data-name="materialServico"]').name = "solicitacoes[${solicitacaoCounter}].materialServico";
    clone.querySelector('[data-name="unidade"]').name = "solicitacoes[${solicitacaoCounter}].unidade";
    clone.querySelector('[data-name="quantidade"]').name = "solicitacoes[${solicitacaoCounter}].quantidade";
    clone.querySelector('[data-name="descricao"]').name = "solicitacoes[${solicitacaoCounter}].descricao";

    const removerBtn = clone.querySelector('.remover');
    removerBtn.onclick = () => clone.remove();
    removerBtn.removeAttribute("data-name");

    // Remover data-name dos outros campos
    const dataNameElements = clone.querySelectorAll('[data-name]');
    dataNameElements.forEach(el => el.removeAttribute("data-name"));

    solicitacaoCounter++;
    container.appendChild(clone);
}

function removerSolicitacao(btn) {
    btn.parentElement.remove();
}
</script>

</body>
</html>
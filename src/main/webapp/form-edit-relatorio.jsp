<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório Vistoria</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/form-relatorio.css">
</head>

<!-- VOLTAR -->
<a class="btn-voltar" href="voltar-home" title="Voltar">←</a>

<h1 class="titulo">Relatório Vistoria</h1>

<div class="container">
<div class="card">

<form action="inserir-relatorio" method="post" enctype="multipart/form-data">

    <!-- DADOS GERAIS -->
	<div class="form-group">
	    <label>Nome da Obra :</label>
	    <select name="obra">
	        <c:forEach var="obra" items="${obras}">
	            <option value="${obra.nome}"
	                ${not empty relatorio and 
	                  not empty relatorio.obra and 
	                  relatorio.obra.nome == obra.nome ? 'selected="selected"' : ''}>
	                ${obra.nome}
	            </option>
	        </c:forEach>
	    </select>
	</div>

    <div class="form-group">
        <label>Empresa Executora :</label>
        <input type="text" name="empresaExecutora" value="${relatorio.empresaExecutora}">
    </div>

    <div class="linha-3">
        <div class="form-group">
            <label>Condição Climática :</label>
            <select name="condicaoClimatica" required>
                <c:forEach var="condicaoClimatica" items="${condicaoClimatica}">
                    <option value="${condicaoClimatica}" ${relatorio.condicaoClimatica == condicaoClimatica ? 'selected' : ''}>
                        ${condicaoClimatica.descricao}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Tempo :</label>
            <select name="tempo" required>
                <c:forEach var="tempo" items="${tempo}">
                    <option value="${tempo}" ${relatorio.tempo == tempo ? 'selected' : ''}>
                        ${tempo.descricao}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Condição :</label>
            <select name="condicao" required>
                <option value="true" ${relatorio.condicao ? 'selected' : ''}>Trabalhável</option>
                <option value="false" ${!relatorio.condicao ? 'selected' : ''}>Inviável</option>
            </select>
        </div>
    </div>

    <!-- VISTORIADORES -->
    <h2>Vistoriadores</h2>

    <div id="vistoriadoresContainer">
        <c:if test="${not empty vistoriadores}">
            <c:forEach var="vistoriador" items="${vistoriadores}" varStatus="status">
                <div class="vistoriador-item" style="display: block;">
                    <div class="form-group">
                        <label>Nome do Vistoriador :</label>
                        <input type="text" name="vistoriadores[].nome" value="${vistoriador.nome}">
                    </div>
                    <button type="button" class="removerVistoriador" onclick="removerVistoriador(this)">✕</button>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <button type="button" class="btn-add" onclick="adicionarVistoriador()">+</button>

    <!-- NÃO CONFORMIDADES -->
    <h2>Não Conformidades</h2>

<div id="naoConformidadesContainer">
    <c:if test="${not empty naoConformidades}">
        <c:forEach var="naoConformidade" items="${naoConformidades}" varStatus="status">
            <div class="nao-conformidade-item" style="display: block;">
                <h3>Não Conformidade</h3>

                <div class="form-group">
                    <label>Pavimento :</label>
                    <select name="naoConformidades[].pavimento" required>
                        <c:forEach var="p" items="${pavimento}">
                            <option value="${p}" ${naoConformidade.pavimento == p ? 'selected' : ''}>${p.descricao}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Etapa da Obra :</label>
                    <select name="naoConformidades[].etapaDaObra" required>
                        <c:forEach var="e" items="${etapaDaObra}">
                            <option value="${e}" ${naoConformidade.etapaDaObra == e ? 'selected' : ''}>${e.descricao}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Tipo de Não Conformidade :</label>
                    <select name="naoConformidades[].tipoDeNaoConformidade" required>
                        <c:forEach var="tipoNC" items="${tipoDeNaoConformidade}">
                            <option value="${tipoNC}" ${naoConformidade.tipoDeNaoConformidade == tipoNC ? 'selected' : ''}>${tipoNC.descricao}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Observação :</label>
                    <textarea name="naoConformidades[].observacao">${naoConformidade.observacao}</textarea>
                </div>

                <div class="form-group">
                    <label>Imagem :</label>
                    <c:if test="${not empty naoConformidade.imagem}">
                        <div>Imagem atual: ${naoConformidade.imagem}</div>
                        <input type="hidden" name="naoConformidades[].imagemExistente" value="${naoConformidade.imagem}">
                    </c:if>
                    <input type="file" name="naoConformidades[].imagem" accept="image/*" capture="environment">
                </div>

                <button type="button" class="remover" onclick="removerNaoConformidade(this)">✕</button>
            </div>
        </c:forEach>
    </c:if>
</div>

    <button type="button" class="btn-add" onclick="adicionarNaoConformidade()">+</button>
    
    <!-- AÇÕES -->
    <div class="acoes-finais">
        <button type="submit" class="btn-primario">Salvar</button>
    </div>

</form>

</div>
</div>

<!-- ===================== TEMPLATES ===================== -->

<!-- TEMPLATE VISTORIADOR -->
<div id="vistoriadorTemplate" style="display:none">
    <div class="form-group">
        <label>Nome do Vistoriador :</label>
        <input type="text" data-name="nome">
    </div>
    <button type="button" class="removerVistoriador" data-name="remove">✕</button>
</div>

<!-- TEMPLATE NÃO CONFORMIDADE -->
<div id="naoConformidadeTemplate" style="display:none">
    <h3>Não Conformidade</h3>

    <div class="form-group">
        <label>Pavimento :</label>
        <select data-name="pavimento">
            <c:forEach var="p" items="${pavimento}">
                <option value="${p}">${p.descricao}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label>Etapa da Obra :</label>
        <select data-name="etapaDaObra">
            <c:forEach var="e" items="${etapaDaObra}">
                <option value="${e}">${e.descricao}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label>Tipo de Não Conformidade :</label>
        <select data-name="tipoDeNaoConformidade">
            <c:forEach var="tipoNC" items="${tipoDeNaoConformidade}">
                <option value="${tipoNC}">${tipoNC.descricao}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label>Observação :</label>
        <textarea data-name="observacao"></textarea>
    </div>

    <div class="form-group">
        <label>Imagem :</label>
        <input type="hidden" data-name="imagemExistente">
        <input type="file" data-name="imagem" accept="image/*" capture="environment">
    </div>

    <button type="button" class="remover" data-name="remove">✕</button>
</div>

<!-- ===================== JS ===================== -->

<script>
let naoConformidadeCounter = ${not empty naoConformidades ? naoConformidades.size() : 0};

function adicionarVistoriador() {
    const container = document.getElementById("vistoriadoresContainer");
    const template = document.getElementById("vistoriadorTemplate");

    const clone = template.cloneNode(true);
    clone.style.display = "block";
    clone.removeAttribute("id");

    const nomeInput = clone.querySelector('[data-name="nome"]');
    nomeInput.name = "vistoriadores[].nome";
    nomeInput.removeAttribute("data-name");

    const removerBtn = clone.querySelector('.removerVistoriador');
    removerBtn.onclick = () => clone.remove();
    removerBtn.removeAttribute("data-name");

    container.appendChild(clone);
}

function adicionarNaoConformidade() {
    const container = document.getElementById("naoConformidadesContainer");
    const template = document.getElementById("naoConformidadeTemplate");

    const clone = template.cloneNode(true);
    clone.style.display = "block";
    clone.removeAttribute("id");

    // Definir nomes únicos para os campos
    clone.querySelector('[data-name="pavimento"]').name = "naoConformidades[${naoConformidadeCounter}].pavimento";
    clone.querySelector('[data-name="etapaDaObra"]').name = "naoConformidades[${naoConformidadeCounter}].etapaDaObra";
    clone.querySelector('[data-name="tipoDeNaoConformidade"]').name = "naoConformidades[${naoConformidadeCounter}].tipoDeNaoConformidade";
    clone.querySelector('[data-name="observacao"]').name = "naoConformidades[${naoConformidadeCounter}].observacao";
    clone.querySelector('[data-name="imagem"]').name = "naoConformidades[${naoConformidadeCounter}].imagem";

    const removerBtn = clone.querySelector('.remover');
    removerBtn.onclick = () => clone.remove();
    removerBtn.removeAttribute("data-name");

    // Remover data-name dos outros campos
    const dataNameElements = clone.querySelectorAll('[data-name]');
    dataNameElements.forEach(el => el.removeAttribute("data-name"));

    naoConformidadeCounter++;
    container.appendChild(clone);
}

function removerVistoriador(btn) {
    btn.parentElement.remove();
}

function removerNaoConformidade(btn) {
    btn.parentElement.remove();
}

document.querySelector('form').addEventListener('submit', function(e) {
    console.log('Formulário enviado!');
    const pavimentos = document.querySelectorAll('select[name*="pavimento"]');
    console.log('Pavimentos encontrados:', pavimentos.length);
    pavimentos.forEach((p, i) => console.log(`Pavimento ${i}:`, p.value, p.name));
});

</script>

</body>
</html>
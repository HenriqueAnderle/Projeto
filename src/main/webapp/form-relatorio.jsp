<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Relatório Vistoria</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/form-relatorio.css">
</head>
<body>

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
		        <option>
		            ${obra.nome}
		        </option>
		    </c:forEach>
		</select>
    </div>

    <div class="form-group">
        <label>Empresa Executora :</label>
        <input type="text" name="empresaExecutora">
    </div>

    <div class="linha-3">
        <div class="form-group">
            <label>Condição Climática :</label>
				<select name="condicaoClimatica">
				    <c:forEach var="condicaoClimatica" items="${condicaoClimatica}">
				        <option value="${condicaoClimatica}">
				            ${condicaoClimatica.descricao}
				        </option>
				    </c:forEach>
				</select>
        </div>

        <div class="form-group">
            <label>Tempo :</label>
				<select name="tempo">
				    <c:forEach var="tempo" items="${tempo}">
				        <option value="${tempo}">
				            ${tempo.descricao}
				        </option>
				    </c:forEach>
				</select>
        </div>

        <div class="form-group">
            <label>Condição :</label>
				<select name="condicao">
				    <option value="true">Trabalhável</option>
				
				    <option value="false">Inviável</option>
				</select>
        </div>
    </div>

    <!-- VISTORIADORES -->
    <h2>Vistoriadores</h2>

    <div id="vistoriadoresContainer"></div>

    <button type="button" class="btn-add" onclick="adicionarVistoriador()">+</button>

    <!-- NÃO CONFORMIDADES -->
    <h2>Não Conformidades</h2>

    <div id="naoConformidadesContainer"></div>

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

    <button type="button" class="removerVistoriador">✕</button>
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

    <button type="button" class="remover">✕</button>

</div>

<!-- ===================== JS ===================== -->

<script>
function adicionarVistoriador() {
    const container = document.getElementById("vistoriadoresContainer");
    const template = document.getElementById("vistoriadorTemplate");

    const clone = template.cloneNode(true);
    clone.style.display = "block";
    clone.removeAttribute("id");

    clone.querySelector('[data-name="nome"]').name = "vistoriadores[].nome";
    clone.querySelector('.removerVistoriador').onclick = () => clone.remove();

    container.appendChild(clone);
}

function adicionarNaoConformidade() {
    const container = document.getElementById("naoConformidadesContainer");
    const template = document.getElementById("naoConformidadeTemplate");

    const clone = template.cloneNode(true);
    clone.style.display = "block";
    clone.removeAttribute("id");

    clone.querySelector('[data-name="pavimento"]').name = "naoConformidades[].pavimento";
    clone.querySelector('[data-name="etapaDaObra"]').name = "naoConformidades[].etapaDaObra";
    clone.querySelector('[data-name="tipoDeNaoConformidade"]').name = "naoConformidades[].tipoDeNaoConformidade";
    clone.querySelector('[data-name="observacao"]').name = "naoConformidades[].observacao";
    clone.querySelector('[data-name="imagem"]').name = "naoConformidades[].imagem";

    clone.querySelector('.remover').onclick = () => clone.remove();

    container.appendChild(clone);
}
</script>

</body>
</html>
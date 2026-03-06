package modelo.entidade.pdfSolicitacao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.entidade.solicitacao.Solicitacao;
import modelo.entidade.solicitacaoMateriaisServicos.SolicitacaoMateriaisServicos;
import modelo.entidade.usuario.Usuario;

public class PdfSolicitacao {

    public byte[] gerarSolicitacaoMateriaisServicos(Usuario usuario, SolicitacaoMateriaisServicos sms, List<Solicitacao> solicitacoes, ServletContext servletContext) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, baos);
        document.open();

        /* ================= FONTES E CORES ================= */
        
        Font label = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
        Font value = new Font(Font.FontFamily.HELVETICA, 10);
        
        Font fontePadrao = new Font(Font.FontFamily.HELVETICA, 10);
        Font fontePadraoNegrito = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

        BaseColor cinzaClaro = new BaseColor(230, 230, 230);
        BaseColor cinzaEscuro = new BaseColor(80, 80, 80);

        /* ================= CABEÇALHO ================= */

        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);

        boolean logoBoolean = sms.getLogo();
        
        if(logoBoolean == true) {
        	String caminhoLogo = servletContext.getRealPath("/img/logo.png");
        	Image logo = Image.getInstance(caminhoLogo);
        	 logo.scaleToFit(55, 55);
        	
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.BOX);
            header.addCell(logoCell);            
            
        }else {
        	String caminhoLogo = servletContext.getRealPath("/img/idheaMannz.png");
        	Image logo = Image.getInstance(caminhoLogo);
        	logo.scaleToFit(100, 100);
        	
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.BOX);
            header.addCell(logoCell);
        }
        
        PdfPCell solicitacaoMaterialServicosCell = new PdfPCell(new Phrase("SOLICITAÇÃO MATERIAIS/SERVIÇOS", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE)));
        solicitacaoMaterialServicosCell.setBackgroundColor(cinzaEscuro);
        solicitacaoMaterialServicosCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        solicitacaoMaterialServicosCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        solicitacaoMaterialServicosCell.setBorder(Rectangle.BOX);
        solicitacaoMaterialServicosCell.setBorderColor(cinzaEscuro);
        solicitacaoMaterialServicosCell.setPadding(4);
        header.addCell(solicitacaoMaterialServicosCell);
        
        document.add(header);
        
        /* ================= DADOS DA SOLICITAÇÃO ================= */
        
        PdfPTable dadosDaSolicitacao = new PdfPTable(6);
        dadosDaSolicitacao.setWidths(new float[]{2.0f, 0.5f, 2.5f, 1.5f, 1.7f, 1.8f});
        dadosDaSolicitacao.setWidthPercentage(100);
        
        PdfPCell solicitacaoNumeroCell = new PdfPCell(new Phrase("RELATÓRIO Nº ", fontePadraoNegrito));
        solicitacaoNumeroCell.setBackgroundColor(cinzaClaro);
        solicitacaoNumeroCell.setColspan(1);
        solicitacaoNumeroCell.setPadding(4);
        dadosDaSolicitacao.addCell(solicitacaoNumeroCell);
        
        if(sms.getLogo() == true) {
        
        PdfPCell idSolicitacaoCell = new PdfPCell(new Phrase(String.valueOf(usuario.getContadorSolicitacao()), fontePadrao));
        idSolicitacaoCell.setColspan(1);
        idSolicitacaoCell.setPadding(4);
        dadosDaSolicitacao.addCell(idSolicitacaoCell);

        }else {
        	
        	PdfPCell idSolicitacaoCell = new PdfPCell(new Phrase(String.valueOf(usuario.getContadorSolicitacao2()), fontePadrao));
            idSolicitacaoCell.setColspan(1);
            idSolicitacaoCell.setPadding(4);
            dadosDaSolicitacao.addCell(idSolicitacaoCell);
        	
        }
        
        PdfPCell dataSolicitacaoTituloCell = new PdfPCell(new Phrase("DATA DA SOLICITAÇÃO", fontePadraoNegrito));
        dataSolicitacaoTituloCell.setBackgroundColor(cinzaClaro);
        dataSolicitacaoTituloCell.setColspan(1);
        dataSolicitacaoTituloCell.setPadding(4);
        dadosDaSolicitacao.addCell(dataSolicitacaoTituloCell);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PdfPCell dataCell = new PdfPCell(new Phrase(sms.getDataSolicitacao().format(formatter), fontePadrao));
        dataCell.setColspan(1);
        dataCell.setPadding(4);
        dadosDaSolicitacao.addCell(dataCell);
        
        PdfPCell solicitanteLabelCell = new PdfPCell(new Phrase("SOLICITANTE", label));
        solicitanteLabelCell.setBorder(Rectangle.BOX);
        solicitanteLabelCell.setBackgroundColor(cinzaClaro);
        solicitanteLabelCell.setPadding(4);
        solicitanteLabelCell.setColspan(1);
        
        PdfPCell solicitanteCell = new PdfPCell(new Phrase(sms.getSolicitante().getDescricao(), value));
        solicitanteCell.setBorder(Rectangle.BOX);
        solicitanteCell.setColspan(1);
        solicitanteCell.setPadding(4);
        
        dadosDaSolicitacao.addCell(solicitanteLabelCell);
        dadosDaSolicitacao.addCell(solicitanteCell);
        
        PdfPCell solicitacaoLabelCell = new PdfPCell(new Phrase("OBRA", label));
        solicitacaoLabelCell.setBorder(Rectangle.BOX);
        solicitacaoLabelCell.setBackgroundColor(cinzaClaro);
        solicitacaoLabelCell.setColspan(1);
        solicitacaoLabelCell.setPadding(4);
        
        PdfPCell solicitacaoCell = new PdfPCell(new Phrase(sms.getObra().getNome(), value));
        solicitacaoCell.setBorder(Rectangle.BOX);
        solicitacaoCell.setColspan(5);
        solicitacaoCell.setPadding(4);
        
        dadosDaSolicitacao.addCell(solicitacaoLabelCell);
        dadosDaSolicitacao.addCell(solicitacaoCell);

        document.add(dadosDaSolicitacao);
        document.add(new Paragraph("\n"));
        
        /* ================= TABELA DE MATERIAIS ================= */
        
        PdfPTable tabela = new PdfPTable(4);
        tabela.setWidthPercentage(100);
        tabela.setWidths(new float[]{3.0f, 2.0f, 2.0f, 3.0f});
        tabela.setHorizontalAlignment(Element.ALIGN_LEFT);

        String[] headers = {
                "MATERIAL / SERVIÇO",
                "UNIDADE",
                "QUANTIDADE",
                "DESCRIÇÃO"
        };

        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, fontePadraoNegrito));
            cell.setBorder(Rectangle.BOX);
            cell.setBackgroundColor(cinzaClaro);
            cell.setPadding(4);
            tabela.addCell(cell);
        }

        for (Solicitacao s : solicitacoes) {
        	PdfPCell materialServicoCell = new PdfPCell(new Phrase(s.getMaterialServico(), fontePadrao));
        	materialServicoCell.setBorder(Rectangle.BOX);
        	materialServicoCell.setPadding(4);
            tabela.addCell(materialServicoCell);
            
            PdfPCell unidadeCell = new PdfPCell(new Phrase(s.getUnidade().getDescricao(), fontePadrao));
            unidadeCell.setBorder(Rectangle.BOX);
            unidadeCell.setPadding(4);
            tabela.addCell(unidadeCell);
            
            PdfPCell quantidadeCell = new PdfPCell(new Phrase(s.getQuantidade(), fontePadrao));
            quantidadeCell.setBorder(Rectangle.BOX);
            quantidadeCell.setPadding(4);
            tabela.addCell(quantidadeCell);
            
            PdfPCell descricaoCell = new PdfPCell(new Phrase(s.getDescricao(), fontePadrao));
            descricaoCell.setBorder(Rectangle.BOX);
            descricaoCell.setPadding(4);
            tabela.addCell(descricaoCell);
        }

        document.add(tabela);
        
        document.add(new Paragraph("\n"));
        
        PdfPTable dadosDaSolicitacao2 = new PdfPTable(2);
        dadosDaSolicitacao2.setWidthPercentage(100);
        dadosDaSolicitacao2.setWidths(new float[]{3f, 7f});
        
        PdfPCell etapaDaObraLabelCell = new PdfPCell(new Phrase("ETAPA DA OBRA", fontePadraoNegrito));
        etapaDaObraLabelCell.setBackgroundColor(cinzaClaro);
        etapaDaObraLabelCell.setBorder(Rectangle.BOX);
        etapaDaObraLabelCell.setPadding(4);
        etapaDaObraLabelCell.setColspan(1);
        
        PdfPCell etapaDaObraCell = new PdfPCell(new Phrase(sms.getEtapaDaObra().getDescricao(), fontePadrao));
        etapaDaObraCell.setBorder(Rectangle.BOX);
        etapaDaObraCell.setPadding(4);
        etapaDaObraCell.setColspan(1);
        
        dadosDaSolicitacao2.addCell(etapaDaObraLabelCell);
        dadosDaSolicitacao2.addCell(etapaDaObraCell);
        
        PdfPCell previsaoChegadaLabelCell = new PdfPCell(new Phrase("PREVISÃO PARA CHEGADA DO MATERIAL NA OBRA", fontePadraoNegrito));
        previsaoChegadaLabelCell.setBackgroundColor(cinzaClaro);
        previsaoChegadaLabelCell.setBorder(Rectangle.BOX);
        previsaoChegadaLabelCell.setPadding(4);
        previsaoChegadaLabelCell.setColspan(1);
        
        DayOfWeek dia = null;
        String diaSemana = null;
        String previsaoFormatada = null;
        
        DateTimeFormatter dfHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        if(sms.getPrevisaoChegada() != null) {
        	
        	LocalDateTime previsaoChegada = sms.getPrevisaoChegada();
            previsaoFormatada = previsaoChegada.format(dfHora);
        	dia = sms.getPrevisaoChegada().getDayOfWeek();
        	diaSemana = dia.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        	
        }else {
        	
        	diaSemana = "";
        	previsaoFormatada = "Previsão de Chegada não Informada";
        	
        }
        
        PdfPCell previsaoChegadaCell = new PdfPCell(new Phrase(diaSemana + ", " + previsaoFormatada, fontePadrao));
        previsaoChegadaCell.setBorder(Rectangle.BOX);
        previsaoChegadaCell.setPadding(4);
        previsaoChegadaCell.setColspan(1);
        
        dadosDaSolicitacao2.addCell(previsaoChegadaLabelCell);
        dadosDaSolicitacao2.addCell(previsaoChegadaCell);
        
        /* ================= IMAGEM ================= */
        
        PdfPCell imagemLabelCell = new PdfPCell(new Phrase("INSERIR FOTO OU ARQUIVO", label));
        imagemLabelCell.setBorder(Rectangle.BOX);
        imagemLabelCell.setBackgroundColor(cinzaClaro);
        imagemLabelCell.setPadding(4);
        imagemLabelCell.setColspan(1);
        
        dadosDaSolicitacao2.addCell(imagemLabelCell);
        
        String caminhoImagem = sms.getImagem();
        if (caminhoImagem != null) {

            String caminhoReal = servletContext.getRealPath(caminhoImagem);

            if (caminhoReal != null && new File(caminhoReal).exists()) {
                Image img = Image.getInstance(caminhoReal);
                img.scaleToFit(250, 160);
                PdfPCell imagemCell = new PdfPCell(img);
                imagemCell.setBorder(Rectangle.BOX);
                imagemCell.setPadding(4);
                imagemCell.setColspan(1);
                dadosDaSolicitacao2.addCell(imagemCell);
                
            } else {
            	PdfPCell imagemCell = new PdfPCell(new Phrase("Imagem não encontrada", value));
                imagemCell.setBorder(Rectangle.BOX);
                imagemCell.setPadding(4);
                imagemCell.setColspan(1);
                dadosDaSolicitacao2.addCell(imagemCell);
                
            }
        } else {
        	PdfPCell imagemCell = new PdfPCell(new Phrase("Sem Imagem", value));
            imagemCell.setBorder(Rectangle.BOX);
            imagemCell.setPadding(4);
            imagemCell.setColspan(1);
            dadosDaSolicitacao2.addCell(imagemCell);
            
        }
        
        PdfPCell observacaoLabelCell = new PdfPCell(new Phrase("OBSERVAÇÃO", label));
        observacaoLabelCell.setBorder(Rectangle.BOX);
        observacaoLabelCell.setBackgroundColor(cinzaClaro);
        observacaoLabelCell.setPadding(4);
        observacaoLabelCell.setColspan(1);
        
        PdfPCell observacaoCell = new PdfPCell(new Phrase(sms.getObservacao(), value));
        observacaoCell.setBorder(Rectangle.BOX);
        observacaoCell.setPadding(4);
        observacaoCell.setMinimumHeight(25);
        observacaoCell.setColspan(1);
        
        dadosDaSolicitacao2.addCell(observacaoLabelCell);
        dadosDaSolicitacao2.addCell(observacaoCell);
        
        document.add(dadosDaSolicitacao2);

        document.close();
        return baos.toByteArray();
    }
}
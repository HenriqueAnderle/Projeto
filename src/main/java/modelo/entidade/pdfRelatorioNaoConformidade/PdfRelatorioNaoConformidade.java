package modelo.entidade.pdfRelatorioNaoConformidade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.DayOfWeek;
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

import modelo.entidade.naoConformidade.NaoConformidade;
import modelo.entidade.relatorio.Relatorio;
import modelo.entidade.usuario.Usuario;
import modelo.entidade.vistoriador.Vistoriador;

public class PdfRelatorioNaoConformidade {

	public byte[] gerarRelatorioNaoConformidade(Usuario usuario, Relatorio relatorio, List<NaoConformidade> naoConformidades, List<Vistoriador> vistoriadores, ServletContext servletContext) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, baos);
        document.open();

        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);

        String caminhoLogo = servletContext.getRealPath("/img/logo.png");

        Image logo = Image.getInstance(caminhoLogo);
        logo.scaleToFit(55, 55);

        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setBorder(Rectangle.BOX);
        header.addCell(logoCell);

        document.add(header);

        /* ================= INFORMAÇÕES DO RELATÓRIO ================= */

        Font fontePadrao = new Font(Font.FontFamily.HELVETICA, 10);
        Font fontePadraoNegrito = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        BaseColor cinzaClaro = new BaseColor(230, 230, 230);
        BaseColor cinzaEscuro = new BaseColor(80, 80, 80);
        
        PdfPTable info = new PdfPTable(6);
        info.setWidths(new float[]{2.0f, 0.5f, 2.5f, 1.5f, 2.0f, 1.5f});
        info.setWidthPercentage(100);
        
        PdfPCell titleCell = new PdfPCell(new Phrase("RELATÓRIO DE VISTORIA DE OBRA", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE)));
        titleCell.setColspan(6);
        titleCell.setBackgroundColor(cinzaEscuro);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setBorderColor(cinzaEscuro);
        titleCell.setPadding(4);
        info.addCell(titleCell);

        PdfPCell relatorioNumeroCell = new PdfPCell(new Phrase("RELATÓRIO Nº ", fontePadraoNegrito));
        relatorioNumeroCell.setBackgroundColor(cinzaClaro);
        relatorioNumeroCell.setColspan(1);
        info.addCell(relatorioNumeroCell);
        
        PdfPCell idRelatorioCell = new PdfPCell(new Phrase(String.valueOf(relatorio.getIdRelatorio()), fontePadrao));
        idRelatorioCell.setColspan(1);
        info.addCell(idRelatorioCell);

        PdfPCell dataRelatorioTituloCell = new PdfPCell(new Phrase("DATA DO RELATÓRIO", fontePadraoNegrito));
        dataRelatorioTituloCell.setBackgroundColor(cinzaClaro);
        dataRelatorioTituloCell.setColspan(1);
        info.addCell(dataRelatorioTituloCell);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PdfPCell dataRelatorioCell = new PdfPCell(new Phrase(relatorio.getDataRelatorio().format(formatter), fontePadrao));
        dataRelatorioCell.setColspan(1);
        info.addCell(dataRelatorioCell);
        
        DayOfWeek diaDaSemana = relatorio.getDataRelatorio().getDayOfWeek();
        String diaFormatado = diaDaSemana.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("pt", "BR"));
        
        PdfPCell diaFormatadoCell = new PdfPCell(new Phrase(diaFormatado, fontePadrao));
        PdfPCell diaDaSemanaCell = new PdfPCell(new Phrase("DIA DA SEMANA", fontePadraoNegrito));
        
        diaDaSemanaCell.setBackgroundColor(cinzaClaro);
        diaDaSemanaCell.setColspan(1);
        diaFormatadoCell.setColspan(1);
        info.addCell(diaDaSemanaCell);
        info.addCell(diaFormatadoCell);
        
        PdfPCell obraCell = new PdfPCell(new Phrase(relatorio.getObra().getNome(), fontePadrao));
        obraCell.setColspan(4);
        PdfPCell obraTituloCell = new PdfPCell(new Phrase("OBRA", fontePadraoNegrito));
        obraTituloCell.setColspan(2);
        obraTituloCell.setBackgroundColor(cinzaClaro);
        info.addCell(obraTituloCell);
        info.addCell(obraCell);

        PdfPCell empresaCell = new PdfPCell(new Phrase(relatorio.getEmpresaExecutora(), fontePadrao));
        empresaCell.setColspan(4);
        PdfPCell empresaTituloCell = new PdfPCell(new Phrase("EMPRESA EXECUTORA", fontePadraoNegrito));
        empresaTituloCell.setColspan(2);
        empresaTituloCell.setBackgroundColor(cinzaClaro);
        info.addCell(empresaTituloCell);
        info.addCell(empresaCell);
        
        document.add(info);
        document.add(new Paragraph("\n"));
        
        PdfPTable condicao = new PdfPTable(6);
        condicao.setWidths(new float[]{2.5f, 2.0f, 1.0f, 1.0f, 1.5f, 2.0f});
        condicao.setWidthPercentage(100);
        
        PdfPCell condicaoClimaticaLabelCell = new PdfPCell(new Phrase("CONDIÇÃO CLIMÁTICA: ", fontePadraoNegrito));
        condicaoClimaticaLabelCell.setBackgroundColor(cinzaClaro);        
        condicao.addCell(condicaoClimaticaLabelCell);
        condicao.addCell(new Phrase(relatorio.getCondicaoClimatica().getDescricao(), fontePadrao));
        
        PdfPCell tempoLabelCell = new PdfPCell(new Phrase("TEMPO: ", fontePadraoNegrito));
        tempoLabelCell.setBackgroundColor(cinzaClaro);
        condicao.addCell(tempoLabelCell);
        condicao.addCell(new Phrase(relatorio.getTempo().getDescricao(), fontePadrao));
        
        String condicaoLabelRelatorio;
        if(relatorio.getCondicao() == true) {
        	condicaoLabelRelatorio = "Trabalhável";
        }else {
        	condicaoLabelRelatorio = "Inviável";
        }
        
        PdfPCell condicaoCell = new PdfPCell(new Phrase("CONDIÇÃO : ", fontePadraoNegrito));
        condicaoCell.setBackgroundColor(cinzaClaro);
        condicao.addCell(condicaoCell);
        condicao.addCell(new Phrase(condicaoLabelRelatorio, fontePadrao));

        
        document.add(condicao);
        document.add(new Paragraph("\n"));

        /* ================= VISTORIA ================= */

        // RESPONSÁVEL
        
        PdfPTable vistoriador = new PdfPTable(1);
        vistoriador.setWidthPercentage(100);
        
        PdfPCell vistoriadorTituloCell = new PdfPCell(new Phrase("RESPONSÁVEL PELA VISTORIA:", fontePadraoNegrito));
        vistoriadorTituloCell.setBackgroundColor(cinzaClaro);
        vistoriador.addCell(vistoriadorTituloCell);
        vistoriador.addCell(new Phrase(relatorio.getUsuario().getNome(), fontePadrao));
        
        document.add(vistoriador);
        document.add(new Paragraph("\n"));

        // PRESENTES
        
        PdfPTable presenteNaVistoria = new PdfPTable(1);
        presenteNaVistoria.setWidthPercentage(100);
        
        PdfPCell presenteNaVistoriaCell = new PdfPCell(new Phrase("PRESENTES NA VISTORIA:", fontePadraoNegrito));
        presenteNaVistoriaCell.setBackgroundColor(cinzaClaro);
        presenteNaVistoria.addCell(presenteNaVistoriaCell);

        PdfPCell presentesCell = null;
        
        String presentes = "";

        String responsavelVistoria = usuario.getNome();
        
        if (vistoriadores != null && !vistoriadores.isEmpty()) {

            presentes = vistoriadores.stream().map(v -> v.getNome()).collect(java.util.stream.Collectors.joining("\n"));
            
            presentesCell = new PdfPCell(new Phrase(responsavelVistoria + "\n" + presentes, fontePadrao));

        }else {
        	
            presentesCell = new PdfPCell(new Phrase(responsavelVistoria, fontePadrao));
        	
        }
        
        presenteNaVistoria.addCell(presentesCell);

        document.add(presenteNaVistoria);
        document.add(new Paragraph("\n"));

        /* ================= NÃO CONFORMIDADES ================= */

        for (int i = 0; i < naoConformidades.size(); i++) {

            NaoConformidade naoConformidade = naoConformidades.get(i);

            /* ---- BLOCO PRINCIPAL ---- */
            
            PdfPTable tabela = new PdfPTable(2);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new float[]{2, 4});

            /* ---- DADOS ---- */
            
            PdfPCell faixa = new PdfPCell(new Phrase("NÃO CONFORMIDADE: " + (i + 1), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE)));
            faixa.setColspan(2);
            faixa.setBackgroundColor(cinzaEscuro);
            faixa.setPadding(4);
            faixa.setBorder(Rectangle.NO_BORDER);
            
            tabela.addCell(faixa);
            
            tabela.setWidthPercentage(100);

            PdfPCell pavimentoCell = new PdfPCell(new Phrase("PAVIMENTO", fontePadraoNegrito));
            pavimentoCell.setBackgroundColor(cinzaClaro);
            tabela.addCell(pavimentoCell);
            tabela.addCell(new PdfPCell(new Phrase(naoConformidade.getPavimento().getDescricao(), fontePadrao)));

            PdfPCell etapaDaObraCell = new PdfPCell(new Phrase("ETAPA DA OBRA", fontePadraoNegrito));
            etapaDaObraCell.setBackgroundColor(cinzaClaro);
            tabela.addCell(etapaDaObraCell);
            tabela.addCell(new PdfPCell(new Phrase(naoConformidade.getEtapaDaObra().getDescricao(), fontePadrao)));

            PdfPCell tipoDeNaoConformidadeCell = new PdfPCell(new Phrase("TIPO DE NÃO CONFORMIDADE", fontePadraoNegrito));
            tipoDeNaoConformidadeCell.setBackgroundColor(cinzaClaro);
            tabela.addCell(tipoDeNaoConformidadeCell);
            tabela.addCell(new PdfPCell(new Phrase(naoConformidade.getTipoDeNaoConformidade().getDescricao(), fontePadrao)));
            
            PdfPCell imagemCell = new PdfPCell(new Phrase("IMAGEM", fontePadraoNegrito));
            imagemCell.setBackgroundColor(cinzaClaro);
            tabela.addCell(imagemCell);

            /* ---- IMAGEM COM VALIDAÇÃO ---- */
            
            PdfPCell imgCell;
            String caminhoImagem = naoConformidade.getImagem();

            if (caminhoImagem != null && !caminhoImagem.trim().isEmpty()) {

                String caminhoReal = servletContext.getRealPath("/uploads/" + caminhoImagem);

                if (caminhoReal != null && new File(caminhoReal).exists()) {
                    try {
                        Image img = Image.getInstance(caminhoReal);
                        img.scaleToFit(200, 140);
                        imgCell = new PdfPCell(img);
                    } catch (Exception e) {
                        imgCell = new PdfPCell(new Phrase("Erro ao carregar imagem", fontePadrao));
                    }
                } else {
                    imgCell = new PdfPCell(new Phrase("Imagem não encontrada", fontePadrao));
                }
            } else {
                imgCell = new PdfPCell(new Phrase("Sem imagem", fontePadrao));
            }

            imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            imgCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            tabela.addCell(imgCell);

            /* ---- OBSERVAÇÕES ---- */

            PdfPCell obsTitulo = new PdfPCell(new Phrase("OBSERVAÇÕES:", fontePadraoNegrito));
            obsTitulo.setColspan(2);
            obsTitulo.setBorder(Rectangle.BOX);
            obsTitulo.setBackgroundColor(cinzaClaro);

            PdfPCell obsTexto = new PdfPCell(new Phrase(naoConformidade.getObservacao(), fontePadrao));
            obsTexto.setColspan(2);
            obsTexto.setMinimumHeight(25);
            obsTexto.setBorder(Rectangle.BOX);

            tabela.addCell(obsTitulo);
            tabela.addCell(obsTexto);

            document.add(tabela);
            
            if(i + 1 < naoConformidades.size()) {
            document.add(new Paragraph("\n"));
            }
        }

        document.close();

        return baos.toByteArray();
    }
	
}

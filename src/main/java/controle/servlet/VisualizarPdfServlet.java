package controle.servlet;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/visualizar-pdf")
public class VisualizarPdfServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        byte[] pdf = (byte[]) request.getSession().getAttribute("pdfGerado");

        if (pdf != null) {
            response.setContentType("application/pdf");
            response.setContentLength(pdf.length);
            response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

            OutputStream os = response.getOutputStream();
            os.write(pdf);
            os.flush();
        } else {
            response.getWriter().write("PDF não encontrado na sessão.");
        }
    }
}
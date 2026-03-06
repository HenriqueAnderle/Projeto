package modelo.filtro;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import modelo.dao.usuario.UsuarioDAO;
import modelo.dao.usuario.UsuarioDAOImpl;
import modelo.entidade.usuario.Usuario;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private UsuarioDAO dao = new UsuarioDAOImpl();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        String context = req.getContextPath();

        // Remove o contexto da URL
        path = path.substring(context.length());

        // Libera recursos públicos e arquivos estáticos
        if (path.equals("/index.jsp") ||
        	    path.equals("/novo-login") ||
        	    path.equals("/logar-usuario") ||
        	    path.equals("/novo-usuario") ||
        	    path.equals("/inserir-usuario") ||
        	    path.startsWith("/css/") ||
        	    path.startsWith("/js/") ||
        	    path.startsWith("/img/") ||
        	    path.endsWith(".css") ||    // ← NOVO: captura /estatico/style.css
        	    path.endsWith(".js") ||     // ← NOVO
        	    path.endsWith(".png") ||    // ← NOVO: imagens comuns
        	    path.endsWith(".jpg") ||
        	    path.endsWith(".gif") ||
        	    path.endsWith(".ico")) {    // favicon

        	    chain.doFilter(request, response);
        	    return;
        	}

        HttpSession sessao = req.getSession(false);

        if (sessao != null && sessao.getAttribute("usuarioLogado") != null) {
            chain.doFilter(request, response);
            return;
        }

        // 🔐 Tenta lembrar pelo cookie
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if ("rememberMe".equals(cookie.getName())) {

                    String token = cookie.getValue();

                    try {
                        Usuario usuario = dao.buscarPorRememberToken(token);

                        if (usuario != null) {
                            HttpSession novaSessao = req.getSession();
                            novaSessao.setAttribute("usuarioLogado", usuario);
                            
                            if (path.equals("/") || path.equals("/index.jsp")) {
                                res.sendRedirect(req.getContextPath() + "/home.jsp");
                                return;
                            }
                            chain.doFilter(request, response);
                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        res.sendRedirect(context + "/index.jsp");
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
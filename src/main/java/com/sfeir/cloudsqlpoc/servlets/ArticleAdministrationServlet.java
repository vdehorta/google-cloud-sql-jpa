package com.sfeir.cloudsqlpoc.servlets;

import com.sfeir.cloudsqlpoc.entities.Article;
import com.sfeir.cloudsqlpoc.service.ArticleService;
import com.sfeir.cloudsqlpoc.service.ArticleServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleAdministrationServlet extends HttpServlet {

    private Logger logger = Logger.getLogger("ArticleAdministration logger");

    private static ArticleService articleService = new ArticleService();

    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        /*Connection c = null;
        try {
            DriverManager.registerDriver(new AppEngineDriver());
            c = DriverManager.getConnection("jdbc:google:rdbms://sfeir.com:test-google-cloud-sql:trial-cloudsql-instance-vivien/vega");
            String articleTitle = request.getParameter("articleTitle");
            String articleText = request.getParameter("articleText");
            if (articleTitle.equals("") || articleText.equals("")) {
                out.println("<html><head></head><body>Missing parameters !</body></html>");
                logger.log(Level.INFO, "Paramètre vide");
            } else {
                logger.log(Level.INFO, "Paramètres valides, requete à executer");
                String statement ="INSERT INTO article (title, text) VALUES(? , ? );";
                PreparedStatement stmt = c.prepareStatement(statement);
                stmt.setString(1, articleTitle);
                stmt.setString(2, articleText);
                int success = stmt.executeUpdate();
                if(success == 1) {
                    logger.log(Level.INFO, "Requête exécutée avec succès");
                    out.println("<html><head></head><body>Success!</body></html>");
                } else if (success == 0) {
                    logger.log(Level.INFO, "Requête exécutée en erreur");
                    out.println("<html><head></head><body>Failure!</body></html>");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "SQL EXCEPTION durant l'execution");
            e.printStackTrace();
        } finally {
            logger.log(Level.INFO, "Dans le finally");
            if (c != null) {
                logger.log(Level.INFO, "Fermeture de la connexion car non nulle");
                try {
                    c.close();
                } catch (SQLException ignore) {}
            }
        }*/

        String articleTitle = request.getParameter("articleTitle");
        String articleText = request.getParameter("articleText");
        if (articleTitle.equals("") || articleText.equals("")) {
            out.println("<html><head></head><body>Missing parameters !</body></html>");
            logger.log(Level.INFO, "Paramètre vide");
        } else {
            logger.log(Level.INFO, "Paramètres valides, requete à executer");

            try {
                articleService.createArticle(new Article(articleTitle, articleText));
                out.println("<html><head></head><body><h1>Article créé !<h1>");
                /*List<Article> articles = articleService.getArticles();
                if (!articles.isEmpty()) {
                    out.println("<h1>Liste des articles en base :</h1>");
                    for (Article a : articles) {
                        out.println("<h3>Article " + a.getId() + " : title="+a.getTitle()+", text="+a.getText()+"</h3>");
                    }
                }*/
                out.println("</body></html>");
            } catch (Exception e) {
                out.println("<html><head></head><body>Error !</body></html>");
            }
        }
    }
}

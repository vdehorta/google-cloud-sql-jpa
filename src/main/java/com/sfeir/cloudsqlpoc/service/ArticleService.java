package com.sfeir.cloudsqlpoc.service;

import com.sfeir.cloudsqlpoc.entities.Article;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for CRUD operations on Article bean
 * @author saroop
 *
 */
public class ArticleService {

    private static final Logger LOGGER = Logger.getLogger(ArticleService.class.getName());

    @PersistenceContext(unitName = "transactions-optional")
    EntityManager mgr;
    private String errorMsg;

    public ArticleService() {
        LOGGER.info("ArticleService instanciated");
    }

    /**
     * Creates a Article - with persistence
     * @param a
     */
    public void createArticle(Article a){
        LOGGER.info("Entering createArticle: ["
                + a.getTitle() + ","
                + a.getText() + "]");
        EntityManager mgr = EMF.get().createEntityManager();
        try {
            mgr.getTransaction().begin();
            mgr.persist(a);
            mgr.getTransaction().commit();

            LOGGER.info("Article persisted");
        } catch (Exception e) {
            LOGGER.info("msg="+e.getMessage()+"\n toString="+e.toString());
            /*errorMsg = "Error while creating article with title '" + a.getTitle() + "' and text '" + a.getText() + "'";
            LOGGER.severe(errorMsg);
            throw new ArticleServiceException(errorMsg);*/
        } finally {
            mgr.close();
        }
        LOGGER.info("Exiting createArticle");
    }

    /**
     * Gets a Article given an ID
     * @param id
     * @return Article bean
     */
    public Article getArticle(Long id) throws ArticleServiceException {
        LOGGER.info("Entering getArticle[" + id + "]");
        Article result = null;
        EntityManager mgr = EMF.get().createEntityManager();
        try {
            result = mgr.find(Article.class, id);
        } catch (Exception e) {
            String msg = "Error while creating article";
            LOGGER.severe(msg);
            throw new ArticleServiceException(msg);
        } finally {
            mgr.close();
        }
        if (result == null) {
            LOGGER.warning("No contacts returned");
        }
        LOGGER.info("Exiting getArticle");
        return result;
    }

    /**
     * Gets all Articles
     * @return List of Article beans
     */
    public List getArticles() throws ArticleServiceException {
        LOGGER.info("Entering getArticles");
        List<Article> result = null;
        EntityManager mgr = EMF.get().createEntityManager();
        try {
            Query q = mgr.createQuery("SELECT a from Article a");
            result = (List<Article>) q.getResultList();
            /*for (Article a:result) {
                System.out.println(a.getTitle());
            }*/
        } catch (Exception e) {
            errorMsg = "Error while getting articles";
            LOGGER.severe(errorMsg);
            throw new ArticleServiceException(errorMsg);
        } finally {
            mgr.close();
        }
        if (result == null) {
            LOGGER.warning("No articles returned");
        }
        LOGGER.info("Exiting getArticles");
        return result;
    }

    /**
     * Deletes an Article by Id
     */
    public void deleteArticle(Long id) throws ArticleServiceException {
        LOGGER.info("Entering deleteArticles");
        EntityManager mgr = EMF.get().createEntityManager();
        Article article = getArticle(id);
        try {
            mgr.getTransaction().begin();
            mgr.remove(article);
            mgr.getTransaction().commit();
        } catch (Exception e) {
            errorMsg = "Error while deleting article od Id " + id;
            LOGGER.severe(errorMsg);
            throw new ArticleServiceException(errorMsg);
        } finally {
            mgr.close();
        }
        LOGGER.info("Exiting deleteArticles");
    }
}
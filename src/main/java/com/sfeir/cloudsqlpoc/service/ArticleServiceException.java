package com.sfeir.cloudsqlpoc.service;

/**
 * Created with IntelliJ IDEA.
 * User: sfeir
 * Date: 29/01/13
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceException extends Exception {

    private String msg;

    ArticleServiceException(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}

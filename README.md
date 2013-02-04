POC non terminé pour l'utilisation de Google Cloud SQL avec utilisation de JPA.

Problèmes à résoudre :

1- Un problème est rencontré lors de l'exécution de l'application déployée sur Appengine, au moment d'instancier l'EntityManagerFactory. J'ai identifié 2 sources potentielles du problème :
- problème de configuration dans le fichier "persistence.xml" (d'après la trace d'erreur, provider manquant mais j'imagine que celui-ci est fourni par Appengine car il n'est jamais fourni dans les exemples que j'ai pu trouver).
- problème de conflit d'une dépendances Maven datanucleus qui serait fournie sur Appengine et qui est également fournie via le pom.xml (je n'ai pas réussi à modifier cela même en modifiant la scope des dépendances Maven suspectes).

	
********** Trace d'erreur obtenue *************

2013-02-04 10:33:02.726 /ArticleAdministration 500 90ms 0kb Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17
91.151.54.221 - - [04/Feb/2013:01:33:02 -0800] "POST /ArticleAdministration HTTP/1.1" 500 0 "http://cloud-sql-vivien-test-main.appspot.com/" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17" "cloud-sql-vivien-test-main.appspot.com" ms=91 cpu_ms=64 instance=00c61b117cd6137a9d1affa5f5301250d0d396b8
I 2013-02-04 10:33:02.698
com.sfeir.cloudsqlpoc.service.ArticleService <init>: ArticleService instanciated
I 2013-02-04 10:33:02.702
com.sfeir.cloudsqlpoc.servlets.ArticleAdministrationServlet doPost: Paramètres valides, requete à executer
I 2013-02-04 10:33:02.707
com.sfeir.cloudsqlpoc.service.ArticleService createArticle: Entering createArticle: [tritre,blablabla]
W 2013-02-04 10:33:02.723
Error for /ArticleAdministration
java.lang.ExceptionInInitializerError
	at com.sfeir.cloudsqlpoc.service.ArticleService.createArticle(ArticleService.java:36)
	at com.sfeir.cloudsqlpoc.servlets.ArticleAdministrationServlet.doPost(ArticleAdministrationServlet.java:77)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:637)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:717)
	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)
	at com.google.apphosting.utils.servlet.ParseBlobUploadFilter.doFilter(ParseBlobUploadFilter.java:102)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at com.google.apphosting.runtime.jetty.SaveSessionFilter.doFilter(SaveSessionFilter.java:35)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at com.google.apphosting.utils.servlet.TransactionCleanupFilter.doFilter(TransactionCleanupFilter.java:43)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)
	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)
	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)
	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)
	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)
	at com.google.apphosting.runtime.jetty.AppVersionHandlerMap.handle(AppVersionHandlerMap.java:266)
	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)
	at org.mortbay.jetty.Server.handle(Server.java:326)
	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)
	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:923)
	at com.google.apphosting.runtime.jetty.RpcRequestParser.parseAvailable(RpcRequestParser.java:76)
	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)
	at com.google.apphosting.runtime.jetty.JettyServletEngineAdapter.serviceRequest(JettyServletEngineAdapter.java:146)
	at com.google.apphosting.runtime.JavaRuntime$RequestRunnable.run(JavaRuntime.java:447)
	at com.google.tracing.TraceContext$TraceContextRunnable.runInContext(TraceContext.java:454)
	at com.google.tracing.TraceContext$TraceContextRunnable$1.run(TraceContext.java:461)
	at com.google.tracing.TraceContext.runInContext(TraceContext.java:703)
	at com.google.tracing.TraceContext$AbstractTraceContextCallback.runInInheritedContextNoUnref(TraceContext.java:338)
	at com.google.tracing.TraceContext$AbstractTraceContextCallback.runInInheritedContext(TraceContext.java:330)
	at com.google.tracing.TraceContext$TraceContextRunnable.run(TraceContext.java:458)
	at com.google.apphosting.runtime.ThreadGroupPool$PoolEntry.run(ThreadGroupPool.java:251)
	at java.lang.Thread.run(Thread.java:679)
Caused by: javax.persistence.PersistenceException: No resource files named META-INF/services/javax.persistence.spi.PersistenceProvider were found. Please make sure that the persistence provider jar file is in your classpath.
	at javax.persistence.Persistence.findAllProviders(Persistence.java:167)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:103)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:83)
	at com.sfeir.cloudsqlpoc.service.EMF.<clinit>(EMF.java:7)
	... 34 more
W 2013-02-04 10:33:02.723
Nested in java.lang.ExceptionInInitializerError:
javax.persistence.PersistenceException: No resource files named META-INF/services/javax.persistence.spi.PersistenceProvider were found. Please make sure that the persistence provider jar file is in your classpath.
	at javax.persistence.Persistence.findAllProviders(Persistence.java:167)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:103)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:83)
	at com.sfeir.cloudsqlpoc.service.EMF.<clinit>(EMF.java:7)
	at com.sfeir.cloudsqlpoc.service.ArticleService.createArticle(ArticleService.java:36)
	at com.sfeir.cloudsqlpoc.servlets.ArticleAdministrationServlet.doPost(ArticleAdministrationServlet.java:77)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:637)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:717)
	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)
	at com.google.apphosting.utils.servlet.ParseBlobUploadFilter.doFilter(ParseBlobUploadFilter.java:102)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at com.google.apphosting.runtime.jetty.SaveSessionFilter.doFilter(SaveSessionFilter.java:35)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at com.google.apphosting.utils.servlet.TransactionCleanupFilter.doFilter(TransactionCleanupFilter.java:43)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)
	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)
	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)
	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)
	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)
	at com.google.apphosting.runtime.jetty.AppVersionHandlerMap.handle(AppVersionHandlerMap.java:266)
	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)
	at org.mortbay.jetty.Server.handle(Server.java:326)
	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)
	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:923)
	at com.google.apphosting.runtime.jetty.RpcRequestParser.parseAvailable(RpcRequestParser.java:76)
	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)
	at com.google.apphosting.runtime.jetty.JettyServletEngineAdapter.serviceRequest(JettyServletEngineAdapter.java:146)
	at com.google.apphosting.runtime.JavaRuntime$RequestRunnable.run(JavaRuntime.java:447)
	at com.google.tracing.TraceContext$TraceContextRunnable.runInContext(TraceContext.java:454)
	at com.google.tracing.TraceContext$TraceContextRunnable$1.run(TraceContext.java:461)
	at com.google.tracing.TraceContext.runInContext(TraceContext.java:703)
	at com.google.tracing.TraceContext$AbstractTraceContextCallback.runInInheritedContextNoUnref(TraceContext.java:338)
	at com.google.tracing.TraceContext$AbstractTraceContextCallback.runInInheritedContext(TraceContext.java:330)
	at com.google.tracing.TraceContext$TraceContextRunnable.run(TraceContext.java:458)
	at com.google.apphosting.runtime.ThreadGroupPool$PoolEntry.run(ThreadGroupPool.java:251)
	at java.lang.Thread.run(Thread.java:679)
C 2013-02-04 10:33:02.725
Uncaught exception from servlet
java.lang.ExceptionInInitializerError
	at com.sfeir.cloudsqlpoc.service.ArticleService.createArticle(ArticleService.java:36)
	at com.sfeir.cloudsqlpoc.servlets.ArticleAdministrationServlet.doPost(ArticleAdministrationServlet.java:77)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:637)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:717)
	at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166)
	at com.google.apphosting.utils.servlet.ParseBlobUploadFilter.doFilter(ParseBlobUploadFilter.java:102)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at com.google.apphosting.runtime.jetty.SaveSessionFilter.doFilter(SaveSessionFilter.java:35)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at com.google.apphosting.utils.servlet.TransactionCleanupFilter.doFilter(TransactionCleanupFilter.java:43)
	at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157)
	at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388)
	at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216)
	at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182)
	at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765)
	at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418)
	at com.google.apphosting.runtime.jetty.AppVersionHandlerMap.handle(AppVersionHandlerMap.java:266)
	at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152)
	at org.mortbay.jetty.Server.handle(Server.java:326)
	at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542)
	at org.mortbay.jetty.HttpConnection$RequestHandler.headerComplete(HttpConnection.java:923)
	at com.google.apphosting.runtime.jetty.RpcRequestParser.parseAvailable(RpcRequestParser.java:76)
	at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404)
	at com.google.apphosting.runtime.jetty.JettyServletEngineAdapter.serviceRequest(JettyServletEngineAdapter.java:146)
	at com.google.apphosting.runtime.JavaRuntime$RequestRunnable.run(JavaRuntime.java:447)
	at com.google.tracing.TraceContext$TraceContextRunnable.runInContext(TraceContext.java:454)
	at com.google.tracing.TraceContext$TraceContextRunnable$1.run(TraceContext.java:461)
	at com.google.tracing.TraceContext.runInContext(TraceContext.java:703)
	at com.google.tracing.TraceContext$AbstractTraceContextCallback.runInInheritedContextNoUnref(TraceContext.java:338)
	at com.google.tracing.TraceContext$AbstractTraceContextCallback.runInInheritedContext(TraceContext.java:330)
	at com.google.tracing.TraceContext$TraceContextRunnable.run(TraceContext.java:458)
	at com.google.apphosting.runtime.ThreadGroupPool$PoolEntry.run(ThreadGroupPool.java:251)
	at java.lang.Thread.run(Thread.java:679)
Caused by: javax.persistence.PersistenceException: No resource files named META-INF/services/javax.persistence.spi.PersistenceProvider were found. Please make sure that the persistence provider jar file is in your classpath.
	at javax.persistence.Persistence.findAllProviders(Persistence.java:167)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:103)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:83)
	at com.sfeir.cloudsqlpoc.service.EMF.<clinit>(EMF.java:7)
	... 34 more


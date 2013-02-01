POC non terminé pour l'utilisation de Google Cloud SQL avec utilisation de JPA.

Problèmes à résoudre :

1- Un problème est rencontré lors de l'exécution de l'application déployée sur Appengine, au moment d'instancier l'EntityManagerFactory. J'ai identifié 2 sources potentielles du problème :
- problème de configuration dans le fichier "persistence.xml".
- problème de conflit d'une dépendances Maven datanucleus qui serait fournie sur Appengine et qui est également fournie via le pom.xml (je n'ai pas réussi à modifier cela même en modifiant la scope des dépendances Maven suspectes).
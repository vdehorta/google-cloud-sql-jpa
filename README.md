POC non termin� pour l'utilisation de Google Cloud SQL avec utilisation de JPA.

Probl�mes � r�soudre :

1- Un probl�me est rencontr� lors de l'ex�cution de l'application d�ploy�e sur Appengine, au moment d'instancier l'EntityManagerFactory. J'ai identifi� 2 sources potentielles du probl�me :
- probl�me de configuration dans le fichier "persistence.xml".
- probl�me de conflit d'une d�pendances Maven datanucleus qui serait fournie sur Appengine et qui est �galement fournie via le pom.xml (je n'ai pas r�ussi � modifier cela m�me en modifiant la scope des d�pendances Maven suspectes).
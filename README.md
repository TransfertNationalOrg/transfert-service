# transfert-api
About our application API

#PROPOSITION D'UN PLAN DE TRAVAIL

#I. Conception (Voir 2. Processus de transfert national)
1. Diagramme de classes
2. Diagrammes de cas d'utilisation
2.1. Transfert national (suivant le canal): Emettre(unitaire ou multiple), Servir, Extourner, Restituer, consulter l'historique
2.2. Accès à la console Back Office
3. Diagramme d'activités
3.1. Emission
3.1.1. Transfert national unitaire
3.1.2. Transferts national multiples
3.2. Réception (servir un transfert)
3.2.1. dans une agence (console Agent)
3.2.2. par Wallet (depuis une console d'agent)
3.2.3. GAB BOA

#II. Développements
1. Mettre en place l'API en suivant les étapes
1.1. les modèles avec cardinalité
1.2. les repositories
1.3. les services pour couvrir les exigences fonctionnelles
2. Partie utilisateur
2.1. utiliser un framework ou une librairie
2.1.1. console Agent
2.1.2. back office
3. Partie mobile : utilisation de Android Studio


#Les différents repositories:
1. transfert-api
2. transfert-console-agent
3. transfert-back-office
4. transfert-wallet

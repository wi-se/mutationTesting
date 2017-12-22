# mutationTesting
Le programe de mutation Testing permet de faire des mutations dans le code.
l'architecture est fait de manière à ce qu'elle soit configurable et multi platforme mais aussi scalable.
Cette application a besoin d'un JAR des tests unitaires qui contient les fichiers de type ".class"
* Pour que le l'application tourne il est nécessaire que l'utilisateur surchage le fichier de configuration application.properties (spécifier le maven.home, le chemin vers le jar)
* Il nécessaire de respecter une convention de nommage pour les fichier test a savoir 
    * L'arborescence des packages doit être la même que la classe à tester
    * Le nom des classes de tests doit reprendre le mêmes nom que les classe à tester plus un suffixe "Test"
* L'application extrait tous les fichiers ".class" et les met dans un répertoire défini dans le fichier de conf
* Elle ne mute que les classes ayant une classe test respectant la rège énumérée plus tôt
* A la fin de chaque mutation de la classe elle lance une commande maven qui permet de lancer les tests de la classe mutée
* l'application opère deux type de mutation 
    * La mutation des opérateurs selon un order défini dans l'application => méthodes qui ne retournent pas un booléen
    * La mutation des méthodes retournant un booléen en retournant soit "false" ou "true" aléatoirement 
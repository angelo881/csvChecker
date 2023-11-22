## Avvio
La classe di avvio è com.interlogica.main.Main
Basta eseguire una build con il comando "mvn clean install" e poi lanciare il JAR ottenuto tramite java -jar.

## Utilizzo
Il modulo è costituito da un controller REST che espone i servizi richiesti.
La documentazione delle API è disponibile alla URL http://localhost:8080/swagger-ui/index.html

## Database
Il databaser utilizza è H2. E' possibile visualizzare i dati importati direttamente con un semplice viewer come RazorSQL  o DBeaver. 
Il parametro **spring.datasource.url** contiene il path dove si vuole storicizzare il database. In alternativa è possibile usare il database in memory (riga commentata nel file application.properties).

## Servizi REST
I servizi sono documentati con Swagger-UI.
Sono presenti due servizi principali:

 * **validateNumber**: Questo servizio consente la validazione del numero telefonico per utilizzo esterno.
 * **consumeFile**: Questo servizio consente l'importazione del file .CSV e restituisce un report completo dei numeri validi, non validi o corretti.

## Web Form
All'indirizzo http://localhost:8080 è raggiungibile una piccola form che consente l'inserimento di un numero telefoni all'utente e fornisce il report sulla validazione del numero stesso e quindi se questo è stato corretto e come e quindi se valido o meno.
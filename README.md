# Scraper
#### Simple banking page web scraper
- Written using `Java 11` & `Spring Boot 2`
- GUI-less
- For HTML parsing `jsoup` library used
- No headless browser used

#### Providing credentials:
-  Providing credentials to your `Millenet` account is needed. Pass the credentials on startup, using following parameters: 
`-Dmillenium.millekod=YOUR_LOGIN`  
`-Dmillenium.password=YOUR_PASSWORD` 
`-Dmillenium.pesel=YOUR_PESEL`.  Alternatively you can provide your credentials in the source code, since they are stored within `application.properties` file. 

#### Running the project:
- Generate .jar file using `mvn package`
- Start the application using `java -jar scraper-1.0.jar -Dmillenium.millekod=YOUR_LOGIN -Dmillenium.password=YOUR_PASSWORD  -Dmillenium.pesel=YOUR_PESEL`
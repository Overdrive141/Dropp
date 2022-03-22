package com.dropp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		try{
           FileInputStream inputStream = new FileInputStream("src/main/resources/dropp_firebase_key.json");
           FirebaseOptions options = new FirebaseOptions.Builder()
                   .setCredentials(GoogleCredentials.fromStream(inputStream))
                   .build();

           FirebaseApp.initializeApp(options);
        } catch (Exception error) {
           error.printStackTrace();
       }
		SpringApplication.run(Application.class, args);
	}
	
	

}

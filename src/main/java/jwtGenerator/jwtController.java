package jwtGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class jwtController {
	
	@Autowired
	public user User1;
	public user User2;
	public boolean check=false;
	
	jwtController() {
	
	this.User1.setUsername("user1");
	this.User1.setPassword("123456");
	this.User2.setUsername("user2");
	this.User2.setPassword("123456");
	}
	
	@RequestMapping("/")
	public String generateToken() throws IOException, InterruptedException, ExecutionException {
		String UID = "User";
	    int uNo = 1;

	    if(check) {
	        FileInputStream serviceAccount = new FileInputStream("./src/main/resources/firebaseAdmin.json");
	        FirebaseOptions options = new FirebaseOptions.Builder()
	                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	                .build();

	        FirebaseApp.initializeApp(options);

	        String uid = UID + Integer.toString(uNo);
	        uNo++;
	        System.out.println(uNo);
	        String customToken = FirebaseAuth.getInstance().createCustomTokenAsync(uid).get();
	        System.out.println(customToken);
	        System.out.println(uid);
	        return customToken;
	    }
	    else
	    	return null;
	    }
	
	@PostMapping(value="/")
	public void authenticate(@RequestBody user testUser) {
		if(testUser.getUsername()==User1.getUsername()) {
			if(testUser.getPassword()==User1.getPassword()) {
				check=true;
			}
			else {
				check=false;
			}
		}
		else if(testUser.getUsername()==User2.getUsername()) {
			if(testUser.getPassword()==User2.getPassword()) {
				check=true;
			}
			else {
				check=false;
			}
		}
		else {
			check=false;
		}
	}
	
	
}

package jwtGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class JwtController {
	
	User user1 = new User();
	User user2 = new User();
	public static boolean check = false;

	public JwtController() {
		this.user1.setUsername("user1");
		this.user1.setPassword("123456");
		this.user2.setUsername("user2");
		this.user2.setPassword("123456");
	}

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public String generateToken() throws IOException, InterruptedException, ExecutionException {
		String UID = "User";
		int uNo = 1;
		if (check) 
		{
			FileInputStream serviceAccount = new FileInputStream("./src/main/resources/firebaseAdmin.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
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

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public void authenticate(@RequestBody User testUser) 
	{
		if (testUser.getUsername().equals(user1.getUsername())) 
		{
			if (testUser.getPassword().equals(user1.getPassword())) 
			{
				check = true;
			} 
			else 
			{
				check = false;
			}
		} 
		else if (testUser.getUsername().equals(user2.getUsername())) 
		{
			if (testUser.getPassword().equals(user2.getPassword())) 
			{
				check = true;
			} 
			else 
			{
				check = false;
			}
		} 
		else 
		{
			check = false;
		}
	}
}

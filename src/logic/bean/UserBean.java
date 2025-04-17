package logic.bean;

import java.util.regex.Pattern;

import logic.exceptions.EmailSyntaxException;
import logic.exceptions.FirstnameSyntaxException;
import logic.exceptions.PasswordSyntaxException;
import logic.exceptions.SurnameSyntaxException;
import logic.exceptions.UsernameSyntaxException;

public class UserBean {
	
	private String username;
	private String password;
	private String firstName;
	private String secondName;
	private String email;
	private String photo;
	
	public UserBean() {
		this.username = "";
		this.password = "";
		this.firstName = "";
		this.secondName = "";
		this.email = "";
		this.photo = "";
	}
	
	public UserBean(String username) {
		this.username = username;
	}

	public UserBean(String username, String photo, String email) {
		this.username = username;
		this.photo = photo;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setAndValidateUsername(String username) throws UsernameSyntaxException {
		this.validateUsername(username);
		this.username = username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setAndValidatePassword(String password) throws PasswordSyntaxException {
		password = this.validatePassword(password);
		this.password = password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setAndValidateFirstName(String firstName) throws FirstnameSyntaxException {
		this.validateName(firstName);
		this.firstName = firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}

	public void setAndValidateSecondName(String secondName) throws SurnameSyntaxException {
		secondName = this.validateSurname(secondName);
		this.secondName = secondName;
	}
	
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setAndValidateEmail(String email) throws EmailSyntaxException {
		this.validateEmail(email);
		this.email = email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	//metodo che controlla l'email nel momento del signUp
	private void validateEmail(String email) throws EmailSyntaxException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            throw new EmailSyntaxException("Email syntax error: please insert a correct email!"); 
        if(!pat.matcher(email).matches()) 
        	throw new EmailSyntaxException("Email syntax error: please insert a correct email!");
		
	}
	
	//metodo che controlla l'username nel momento del signUp
	private String validateUsername(String username) throws UsernameSyntaxException {
		if(username.length() < 3 || username.length() > 20 || !this.validateUsernameFormat(username))
			throw new UsernameSyntaxException("Username syntax error: Minlength 3 and maxlength 20! Characters allowed: letters, digits and \"_\" \".\"");
		return username;
	}
	
	//metodo che controlla la password nel momento del signUp
	private String validatePassword(String password) throws PasswordSyntaxException {
		if(password.length() < 8 || password.length() > 17 || !this.validatePasswordFormat(password)) 
			throw new PasswordSyntaxException("Password syntax error: format [at least one or more uppercase letters, one or more lowercase characters, one or more digit and one or more special characters, minlength of 8 and max length of 16]!");
		password = this.checkApostrophe(password);
		return password;
	}
	
	private void validateName(String firstName) throws FirstnameSyntaxException {
		if(firstName.length() < 3 || firstName.length() > 45 || !this.validateString(firstName)) 
			throw new FirstnameSyntaxException("Name syntax error: please insert only characters with minlength of 3 and maxlength of 45!");
	}
	
	private String validateSurname(String surname) throws SurnameSyntaxException {
		if(surname.length() < 3 || surname.length() > 45 || !this.validateStringWithApostrophe(surname)) 
			throw new SurnameSyntaxException("Surname syntax error: please insert only characters with minlength of 3 and maxlength of 45!");
		surname = this.checkApostrophe(surname);
		return surname;
	}
	
	private boolean validateStringWithApostrophe(String str) {
	   str = str.toLowerCase();
	   char[] charArray = str.toCharArray();
	   for (int i = 0; i < charArray.length; i++) {
		   char ch = charArray[i];
		   if (!((ch >= 'a' && ch <= 'z') || ch == 39 || ch == ' ')) {
			   return false;
		   }
	   }
	   return true;
	}
	
	private boolean validateString(String str) {
	   str = str.toLowerCase();
	   char[] charArray = str.toCharArray();
	   for (int i = 0; i < charArray.length; i++) {
		   char ch = charArray[i];
		   if (!(ch >= 'a' && ch <= 'z')) {
			   return false;
		   }
	   }
	   return true;
	}
	
	private boolean validateUsernameFormat(String username) {
		for(int i =0; i<username.length(); i++) {
            char c = username.charAt(i);
            if(!(Character.isUpperCase(c) || Character.isLowerCase(c) || Character.isDigit(c) || (c == 46) || (c == 95))) {
                return false;
            }
        }
		return true;
	}
	
	private boolean validatePasswordFormat(String password) {
        int upCount = 0;
        int loCount = 0;
        int digit = 0;
        int special = 0;
		for(int i =0; i<password.length(); i++){
            char c = password.charAt(i);
            if(Character.isUpperCase(c)){
                upCount++;
            }
            if(Character.isLowerCase(c)){
                loCount++;
            }
            if(Character.isDigit(c)){
                digit++;
            }
            if((c>=33 && c<=46)||c==64){
                special++;
            }
        }
        return ((special>=1) && (loCount>=1) && (upCount>=1) && (digit>=1)); 
	}
	
	private String checkApostrophe(String username) {
		String result = "";
		for(int i =0; i<username.length(); i++){
            char c = username.charAt(i);
            result = result.concat(String.valueOf(c));
            if(c == 39) {
               result = result.concat(String.valueOf(c));
            }
        }
		return result;
	}
	
}

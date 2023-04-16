package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

/**
 * This class is used to hash the password of the user.
 */
public class HashService {
	private static HashService hs = null;
	private HashService() {};
	/**
	 * This method is used to get the instance of the HashService class. It is a singleton class.
	 * @return The instance of the HashService class.
	 */
	public static HashService getInstance() {
		if (hs == null) {
			hs = new HashService();
		}
		return hs;
	}
	
	/**
	 * This method is used to hash the password of the user.
	 * @param password The password of the user.
	 * @param userID The ID of the user.
	 * @return The hashed password of the user.
	 */
	public String hashPassword(String password, String userID){
		String algorithm = "SHA-256";
		String hashedPass = null;
		try {
			/*
			 * Use SHA-256 algorithm
			 * Obtain bytes of hashed value
			 * Convert bytes to hexadecimal string
			 */
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(userID.getBytes(StandardCharsets.UTF_8));
			byte[] passwordBytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < passwordBytes.length; i++) {
				sb.append(String.format("%02X", passwordBytes[i]));
			}
			hashedPass = sb.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("Can't find algorithm: " + algorithm);
		}
		return hashedPass;
	}

	/**
	 * This method is used to generate a salt for the password.
	 * @return The salt for the password.
	 */
	public byte[] generateSalt() {
		byte[] bytes = new byte[10];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
}

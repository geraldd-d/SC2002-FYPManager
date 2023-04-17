package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

/**
 * This class is used to hash the password of the user.
 */
public class HashService {
	private HashService() {};
	/**
	 * This method is used to hash the password of the user.
	 * @param password The password of the user.
	 * @param userID The ID of the user.
	 * @return The hashed password of the user.
	 */
	public static String hashPassword(String password, String userID){
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
}

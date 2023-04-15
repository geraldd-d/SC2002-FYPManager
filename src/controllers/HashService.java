package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

public class HashService {
	private HashService() {};

	
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

package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.lang.StringBuilder;

public class HashService {
	private static HashService hs = null;
	private HashService() {};
	public static HashService getInstance() {
		if (hs == null) {
			hs = new HashService();
		}
		return hs;
	}
	
	public String hashPassword(String password, byte[] salt){
		String algorithm = "SHA-256";
		String hashedPass = null;
		try {
			/*
			 * Use SHA-256 algorithm
			 * Obtain bytes of hashed value
			 * Convert bytes to hexadecimal string
			 */
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(salt);
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
	public byte[] generateSalt() {
		byte[] bytes = new byte[10];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
}

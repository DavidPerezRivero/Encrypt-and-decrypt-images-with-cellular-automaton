package encryptText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import window.Window;

public class EncryptAndDecryptText {
	private final String UTF_8 = "UTF-8";

	public EncryptAndDecryptText() {
	}

	public Cipher getCipher(boolean option, Window window, String seed) {
		try {
			final String seedKey = seed;
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(seedKey.getBytes(UTF_8));
			final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
			final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
			if (option) {
				aes.init(Cipher.ENCRYPT_MODE, key);
			} else {
				aes.init(Cipher.DECRYPT_MODE, key);
			}
			return aes;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException
				| InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void readFile(Window window, File selectedFile) {
		try {
			// Leyendo fichero
			BufferedReader br = new BufferedReader(new FileReader(selectedFile));
			String radius = br.readLine();
			String rounds = br.readLine();
			String seed = br.readLine();
			br.close();
			// Instanciando descencriptador
			Cipher aes = getCipher(false, window, seed);
			// Descencriptando radio
			byte[] b = radius.getBytes();
			byte[] bytes = aes.doFinal(b);
			String radiusDecrypted = new String(bytes, UTF_8);
			window.getRadiusComboBox().setSelectedIndex(Integer.valueOf(radiusDecrypted));
			// Descencriptando rondas
			b = rounds.getBytes();
			bytes = aes.doFinal(b);
			String roundsDecrypted = new String(bytes, UTF_8);
			window.getRoundsComboBox().setSelectedIndex(Integer.valueOf(roundsDecrypted));
			// Descencriptando semilla
			b = seed.getBytes();
			bytes = aes.doFinal(b);
			String seedDecrypted = new String(bytes, UTF_8);
			window.setSeed(seedDecrypted);
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}

	public void writeFile(Window window, String path) {
		try {
			// Datos a encriptar
			String radius = String.valueOf(window.getRadiusComboBox().getSelectedIndex());
			String rounds = String.valueOf(window.getRoundsComboBox().getSelectedIndex());
			String seed = String.valueOf(window.getSeed());
			// Instanciando encifrador y variables para escribir fichero
			Cipher aes = getCipher(true, window, seed);
			FileWriter file = new FileWriter(path);
			PrintWriter pw = new PrintWriter(file);
			// Cifrando radius
			byte[] bytes = radius.getBytes(UTF_8);
			byte[] cifrado = aes.doFinal(bytes);
			String stringEncrypted = new String(cifrado);
			// Cifrando radius
			bytes = rounds.getBytes(UTF_8);
			cifrado = aes.doFinal(bytes);
			stringEncrypted = new String(cifrado);
			// Cifrando radius
			bytes = seed.getBytes(UTF_8);
			cifrado = aes.doFinal(bytes);
			stringEncrypted = new String(cifrado);
			// Escribir fichero
			pw.println(stringEncrypted);
			pw.println(stringEncrypted);
			pw.println(stringEncrypted);
			file.close();
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
}

package com.rabo.tppapi.TestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility Class for tests
 * @author Nishchal
 *
 */
public class TestUtils {

	private TestUtils() throws InstantiationException {
		throw new InstantiationException("Cannot instantiate the class");
	}

	/**
	 * Reads file and returns {@link InputStream} instance.
	 *
	 * @param fileName
	 *            name of the file
	 * @return {@code InputStream} instance
	 */
	public static InputStream readFile(final String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}

	/**
	 * converts a Java object into JSON representation
	 * 
	 * @param obj
	 *            input object
	 * @return string value
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reads the certificate from the given path
	 * 
	 * @param path
	 *            path of the file
	 * @return string value
	 * @throws IOException
	 *             Exception
	 */
	public static String readCertificate(String path) throws IOException {

		File file = ResourceUtils.getFile(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[65535];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[65535];
		}
		fis.close();

		return sb.toString();
	}

	/**
	 * Reads the signature from the given path
	 * 
	 * @param path
	 *            path of the file
	 * @return string value
	 * @throws IOException
	 *             Exception
	 */

	public static String readSignature(String path) throws IOException {

		File file = ResourceUtils.getFile(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[65535];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[65535];
		}
		fis.close();

		return sb.toString();
	}

}

package com.github.ZXSkelobrine.Clippur;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class Imgur {

	public static String upload(BufferedImage image) throws Exception {
		// Start the upload icon
		Tray.setUploading();
		// Create a ByteArrayOutputStream to store the image in for conversion.
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		// Write the image into the array
		ImageIO.write(image, "png", byteArray);
		// Create a byte array from the ByteArrayOutputStream
		byte[] byteImage = byteArray.toByteArray();
		// Convert the image to a string in Base64
		String dataImage = new String(Base64.encodeBase64(byteImage));
		// Create the necessary data string for uploading
		String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");
		// Create a url instance for the imgur upload api.
		URL url = new URL("https://api.imgur.com/3/image");
		// Open a connection to the upload api site.
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// Set doOutput and doInput to true.
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// Set the request type
		conn.setRequestMethod("POST");
		// Set the authorization to my client id.
		conn.setRequestProperty("Authorization", "Client-ID " + "d8ccfaff5f2da67");
		// Repeat above
		conn.setRequestMethod("POST");
		// Set the content type to: application/x-www-form-urlencoded
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// Start the connection.
		conn.connect();

		// Create a stringbuilder to process the page output (JSON)
		StringBuilder stb = new StringBuilder();
		// Create an output stream writer from the connection
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		// Write the data string to connection.
		wr.write(data);
		// Flush to confirm send
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		// Create string to store the current line.
		String line;
		// While there are still lines
		while ((line = rd.readLine()) != null) {
			// Append the lines to the StringBuilder
			stb.append(line).append("\n");
		}
		// Close the OutputStreamWriter
		wr.close();
		// Close the BufferedReader
		rd.close();
		// Show that the process has completed.
		Tray.setDone();
		// Return the data.
		return stb.toString();
	}
}

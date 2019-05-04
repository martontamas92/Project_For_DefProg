package business_model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCode {
	/*
	 * Author: Marton Tamás
	 * I have to secure this code to many fail opportunity
	 * It works so this is a stable version for generating QR code
	 * generateQRImage generates a QR code image to the sepcified path
	 * generateQRCode generates a byte array of the code so it can passed
	 */

	public static void generateQRImage(String text, int width, int height, String filePath) throws WriterException, IOException {

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

	}

	public byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qr = new QRCodeWriter();
		BitMatrix bitMatrix = qr.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream qrOS = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, text, qrOS);
		byte[] data = qrOS.toByteArray();
		return data;

	}
}

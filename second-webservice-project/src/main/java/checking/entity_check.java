package checking;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.zxing.WriterException;
import com.sun.research.ws.wadl.Application;

import business_model.Name;
import business_model.Neptun_Code;
import business_model.QrCode;
import model.Student;
/*
 * Author: Marton Tamás
 * Az entitások ellenõrzése végett lett létrehozva nincs szerepe
 */
@SpringBootApplication
final class entity_check {
	 private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

	public static void main(String[] args) {
		/*try {
            QrCode.generateQRImage("QR hello world", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }*/
		SpringApplication.run(Application.class, args);


	}

}

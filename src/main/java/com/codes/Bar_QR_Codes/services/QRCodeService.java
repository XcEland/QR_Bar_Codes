package com.codes.Bar_QR_Codes.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

@Service
public class QRCodeService {

    public void generateQRCode(String data, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", new File(filePath).toPath());
    }

    public String decodeQRCode(String filePath) throws NotFoundException, IOException {
        File qrCodeImage = new File(filePath);
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        
        // Get the decoded text
        String decodedText = result.getText();

        // Save the decoded text to a file
        String decodedDirPath = "C:/Users/Admin/Desktop/bk/Bar_QR_Codes/src/main/resources/qr/decoded";
        String originalFileName = qrCodeImage.getName().replace(".png", ""); // Remove the extension
        String decodedFileName = decodedDirPath + "/" + originalFileName + "_decoded.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(decodedFileName))) {
            writer.write(decodedText);
        }

        return decodedFileName; // Return the path of the saved file
    }
}


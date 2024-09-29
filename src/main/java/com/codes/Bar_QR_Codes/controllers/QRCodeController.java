package com.codes.Bar_QR_Codes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codes.Bar_QR_Codes.services.QRCodeService;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateQRCode(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("outputName") String outputName) {
        try {
            // Save the file temporarily
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Generate the QR code with the file path as data and custom output name
            String qrCodePath = "QRCode_" + outputName + ".png";
            String fullPath = new File("C:/Users/Admin/Desktop/bk/Bar_QR_Codes/src/main/resources/qr/encoded", qrCodePath).getAbsolutePath();
            qrCodeService.generateQRCode(tempFile.getAbsolutePath(), fullPath);

            return new ResponseEntity<>("QR Code generated: " + fullPath, HttpStatus.OK);
        } catch (IOException | WriterException e) {
            return new ResponseEntity<>("Error generating QR Code: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/decode")
    public ResponseEntity<String> decodeQRCode(@RequestParam("filePath") String filePath) {
        try {
            String decodedFilePath = qrCodeService.decodeQRCode(filePath);
            return new ResponseEntity<>("Decoded text saved at: " + decodedFilePath, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("QR Code not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Error decoding QR Code: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
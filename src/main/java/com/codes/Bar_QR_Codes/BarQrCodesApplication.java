package com.codes.Bar_QR_Codes;
import java.awt.image.BufferedImage;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
public class BarQrCodesApplication{

	public static void main(String[] args) {
		SpringApplication.run(BarQrCodesApplication.class, args);
	}

	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter(){
		return new BufferedImageHttpMessageConverter();
	}
}

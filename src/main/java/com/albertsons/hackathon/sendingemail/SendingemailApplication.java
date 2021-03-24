package com.albertsons.hackathon.sendingemail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.albertsons.hackathon.sendingemail.repository.PromoEmailRepository;

@SpringBootApplication
public class SendingemailApplication {

	@Autowired
	PromoEmailRepository promoEmailRepo;
	
    public static void main(String[] args) {
        SpringApplication.run(SendingemailApplication.class, args);
    }
    
}

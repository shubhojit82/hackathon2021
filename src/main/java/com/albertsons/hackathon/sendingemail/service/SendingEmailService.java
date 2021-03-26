package com.albertsons.hackathon.sendingemail.service;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;

import com.albertsons.hackathon.sendingemail.model.MailModel;

import java.io.IOException;

public interface SendingEmailService {

    void sendEmail(MailModel mailModel) throws MessagingException, IOException, TemplateException;
}

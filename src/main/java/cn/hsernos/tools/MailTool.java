package cn.hsernos.tools;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


@Component
public class MailTool {


    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void send(String subjct, String text, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subjct);
        message.setText(text);
        mailSender.send(message);

    }


    @Async
    @SneakyThrows
    public void sendHtml(String subjct, String text, String... to) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subjct);
        // html格式，第二个参数为true
        messageHelper.setText(text, true);
        // 附件
        // messageHelper.addAttachment
        mailSender.send(mimeMessage);
    }
}

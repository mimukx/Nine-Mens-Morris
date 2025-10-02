package sk.tuke.gamestudio.interfaces;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}

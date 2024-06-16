package br.com.matteusmoreno.utils;

import br.com.matteusmoreno.client.brasil_api.BrasilApiClient;
import br.com.matteusmoreno.client.brasil_api.BrasilApiResponse;
import br.com.matteusmoreno.client.viacep.ViaCepClient;
import br.com.matteusmoreno.client.viacep.ViaCepResponse;
import br.com.matteusmoreno.address.Address;
import br.com.matteusmoreno.supplier.Supplier;
import br.com.matteusmoreno.exception.exception_class.EmailTemplateReadException;
import br.com.matteusmoreno.exception.exception_class.InvalidCepException;
import br.com.matteusmoreno.address.AddressRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;

@ApplicationScoped
public class AppUtils {

    private final Mailer mailer;

    @Inject
    public AppUtils(Mailer mailer) {
        this.mailer = mailer;
    }

    public Integer ageCalculator(LocalDate birtDate) {
        LocalDate currentlyDate = LocalDate.now();

        return Period.between(birtDate, currentlyDate).getYears();
    }


    public void sendEmail(String templateName, String to, String subject, String name) {
        String templatePath = "src/main/resources/emails/" + templateName;
        try {
            String content = new String(Files.readAllBytes(Paths.get(templatePath)));
            content = content.replace("{name}", name);

            mailer.send(Mail.withText(to, subject, content));
        } catch (IOException e) {
            throw new EmailTemplateReadException("Error reading email template");
        }
    }
}

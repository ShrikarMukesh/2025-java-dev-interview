package org.elevens;


import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
class DomainNotValidException extends RuntimeException {

    public DomainNotValidException(String message) {
        super(message);
    }
}
class RegistrationService {

    public void validateEmail(String email) {
        if (!isDomainValid(email)) {
            throw new DomainNotValidException("Invalid domain");
        }
    }

    private boolean isDomainValid(String email) {
        List<String> validDomains = Arrays.asList("gmail.com", "yahoo.com", "outlook.com");
        if (validDomains.contains(email.substring(email.indexOf("@") + 1))) {
            return true;
        }
        return false;
    }
}
public class RegistrationServiceClient {
    public static void main(String[] args) {
        RegistrationService service = new RegistrationService();
        service.validateEmail("abc@gmail1.com");
    }
}
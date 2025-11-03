package electricity.billing.system;

public class SignupValidator {

    public static String validateSignupFields(String accountType, String username, String name, String password, String meter) {

        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }

        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty.";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }

        if (accountType.equals("Customer") && (meter == null || meter.trim().isEmpty())) {
            return "Meter number is required for Customer accounts.";
        }

        // Everything is fine
        return null;
    }
}

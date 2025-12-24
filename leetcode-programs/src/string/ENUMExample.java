package string;

enum CustomErrorConstants {

    EVENT_REJECTED(4001, "EVENT_REJECTED"),
    EVENT_SUCCESS(2001, "EVENT is SUCCESSful");

    private final int code;
    private final String message;

    CustomErrorConstants(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
public class ENUMExample {
    public static void main(String[] args) {
        System.out.println(CustomErrorConstants.EVENT_SUCCESS);
    }
}

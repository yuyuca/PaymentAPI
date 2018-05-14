package ygateway;

public class RequestValidator {
	public static boolean isInvalid(Integer amount) {
		if (amount == null) {
			return true;
		}
		if (amount < 0) {
			return true;
		}
		return false;
	}

}

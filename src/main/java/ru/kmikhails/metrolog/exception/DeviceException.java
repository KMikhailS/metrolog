package ru.kmikhails.metrolog.exception;

public class DeviceException extends RuntimeException {

	public DeviceException() {
		super();
	}

	public DeviceException(String message) {
		super(message);
	}

	public DeviceException(Exception cause) {
		super(cause);
	}

	public DeviceException(String message, Exception cause) {
		super(message, cause);
	}
}

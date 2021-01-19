package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if an uploaded image file has a too low resolution.
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = UploadedImageResolutionTooLowException.ERROR_MESSAGE)
public class UploadedImageResolutionTooLowException extends Exception {
    public static final String ERROR_MESSAGE = "Die Auflösung des Bildes ist zu gering. Die Höhe muss mindestens 512 Pixel betragen";

    public UploadedImageResolutionTooLowException() {
        super(ERROR_MESSAGE);
    }
}

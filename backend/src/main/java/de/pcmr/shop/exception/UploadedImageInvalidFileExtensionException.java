package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if an uploaded image file has an invalid file extension.
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = UploadedImageInvalidFileExtensionException.ERROR_MESSAGE)
public class UploadedImageInvalidFileExtensionException extends Exception {
    public static final String ERROR_MESSAGE = "Die hochgeladenen Datei hat eine ungültige Dateiendung. Erlaubt sind \"png\" und \"jpg\" ";

    public UploadedImageInvalidFileExtensionException() {
        super(ERROR_MESSAGE);
    }
}

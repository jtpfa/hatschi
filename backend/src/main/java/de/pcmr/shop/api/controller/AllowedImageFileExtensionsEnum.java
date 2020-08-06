package de.pcmr.shop.api.controller;

import java.util.List;
import java.util.stream.Collectors;

public enum AllowedImageFileExtensionsEnum {
    PNG("png"),
    JPG("jpg");

    private String string;

    AllowedImageFileExtensionsEnum(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static List<String> getStringList() {
        return List.of(values()).stream().map(AllowedImageFileExtensionsEnum::getString).collect(Collectors.toList());
    }
}

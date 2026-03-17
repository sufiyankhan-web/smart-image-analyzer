package com.project.dto;

public class UploadResponseDto {

    private String message;
    private ImageAnalysisResponseDto data;

    public UploadResponseDto() {
    }

    public UploadResponseDto(String message, ImageAnalysisResponseDto data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ImageAnalysisResponseDto getData() {
        return data;
    }

    public void setData(ImageAnalysisResponseDto data) {
        this.data = data;
    }
}

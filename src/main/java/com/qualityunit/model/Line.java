package com.qualityunit.model;

public class Line {
    private String serviceId;
    private String serviceVariationId;
    private String questionTypeId;
    private String questionCategoryId;
    private String questionSubCategoryId;
    private ResponseType responseType;

    public Line() {
    }

    public Line(String[] line, ResponseType responseType) {
        String[] service = line[1].split("\\.");
        String[] question = line[2].split("\\.");

        this.serviceId = service[0];
        this.serviceVariationId = service.length == 2 ? service[1] : null;
        this.questionTypeId = question[0];
        this.questionCategoryId = question.length == 2 ? question[1] : null;
        this.questionSubCategoryId = question.length == 3 ? question[2] : null;
        this.responseType = responseType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceVariationId() {
        return serviceVariationId;
    }

    public void setServiceVariationId(String serviceVariationId) {
        this.serviceVariationId = serviceVariationId;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionCategoryId() {
        return questionCategoryId;
    }

    public void setQuestionCategoryId(String questionCategoryId) {
        this.questionCategoryId = questionCategoryId;
    }

    public String getQuestionSubCategoryId() {
        return questionSubCategoryId;
    }

    public void setQuestionSubCategoryId(String questionSubCategoryId) {
        this.questionSubCategoryId = questionSubCategoryId;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return "Line{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceVariationId='" + serviceVariationId + '\'' +
                ", questionTypeId='" + questionTypeId + '\'' +
                ", questionCategoryId='" + questionCategoryId + '\'' +
                ", questionSubCategoryId='" + questionSubCategoryId + '\'' +
                ", responseType=" + responseType +
                ", ";
    }
}

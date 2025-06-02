package com.ricardo.scalable.ecommerce.platform.userService.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressCreationDto {

    @Min(1)
    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String commune;

    @NotBlank
    private String postalCode;

    private String landmark;

    public AddressCreationDto() {
    }

    public AddressCreationDto(Long userId, String title, String addressLine1, String addressLine2, String country, String city,
            String commune, String postalCode, String landmark) {
        this.userId = userId;
        this.title = title;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.city = city;
        this.commune = commune;
        this.postalCode = postalCode;
        this.landmark = landmark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

}

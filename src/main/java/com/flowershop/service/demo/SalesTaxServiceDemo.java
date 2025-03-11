package com.flowershop.service.demo;

import org.springframework.web.client.RestClient;

import java.util.List;

public class SalesTaxServiceDemo {

    // ENDPOINT that works in Postman
    // https://services.maps.cdtfa.ca.gov/api/taxrate/GetRateByAddress?address=450+n+st&city=sacramento&zip=95814
    private final String API_BASE_URL = "https://services.maps.cdtfa.ca.gov/api/taxrate/GetRateByAddress";
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    public TaxRate getTaxRate(){

        return restClient.get()
                .uri("?address=450+n+st&city=sacramento&zip=95814")
                .retrieve()
                .body(TaxRate.class);
    }

    public static void main(String[] args) {
        TaxRate taxRate = new SalesTaxServiceDemo().getTaxRate();
        System.out.println("Tax Rate is: " + taxRate.getTaxRateInfo().get(0).getRate()*100 + "%");
    }
}

//class TaxRate {
//
//    private List<TaxRateInfo> taxRateInfo;
//
//    public List<TaxRateInfo> getTaxRateInfo() {
//        return taxRateInfo;
//    }
//}
//
//class TaxRateInfo {
//
//    private Double rate;
//
//    public Double getRate() {
//        return rate;
//    }
//}

class GeocodeInfo {

    private List<String> matchCodes;
    private String formattedAddress;
    private String confidence;
    private String calcMethod;
    private Integer bufferDistance;

    public List<String> getMatchCodes() {
        return matchCodes;
    }

    public void setMatchCodes(List<String> matchCodes) {
        this.matchCodes = matchCodes;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getCalcMethod() {
        return calcMethod;
    }

    public void setCalcMethod(String calcMethod) {
        this.calcMethod = calcMethod;
    }

    public Integer getBufferDistance() {
        return bufferDistance;
    }

    public void setBufferDistance(Integer bufferDistance) {
        this.bufferDistance = bufferDistance;
    }

}

class TaxRate {

    private List<TaxRateInfo> taxRateInfo;
    private GeocodeInfo geocodeInfo;
    private String termsOfUse;
    private String disclaimer;

    public List<TaxRateInfo> getTaxRateInfo() {
        return taxRateInfo;
    }

    public void setTaxRateInfo(List<TaxRateInfo> taxRateInfo) {
        this.taxRateInfo = taxRateInfo;
    }

    public GeocodeInfo getGeocodeInfo() {
        return geocodeInfo;
    }

    public void setGeocodeInfo(GeocodeInfo geocodeInfo) {
        this.geocodeInfo = geocodeInfo;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

}

class TaxRateInfo {

    private Double rate;
    private String jurisdiction;
    private String city;
    private String county;
    private String tac;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

}
package com.prabhat.brothers;

/**
 * Created by USER on 2/24/2018.
 */

public class Users {

    private String userName;
    private String mobileNumber;
    private String userBio;
    private String userSkills;
    private String profileImageUrl;
    private String userEmail;
    private String currentMonth;
    private String paymentStatus;
    private String paymentAmount;
    private String voteWeight;

    public String getVoteWeight() {
        return voteWeight;
    }

    public void setVoteWeight(String voteWeight) {
        this.voteWeight = voteWeight;
    }

    public String getPaymentAmountTotal() {
        return paymentAmountTotal;
    }

    public void setPaymentAmountTotal(String paymentAmountTotal) {
        this.paymentAmountTotal = paymentAmountTotal;
    }

    private String paymentAmountTotal;

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getMonthsPending() {
        return monthsPending;
    }

    public void setMonthsPending(String monthsPending) {
        this.monthsPending = monthsPending;
    }

    private String monthsPending;

    public Users() {


    }

    public Users(String userName, String mobileNumber, String userEmail, String userBio, String userSkills, String profileImageUrl) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.userBio = userBio;
        this.userSkills = userSkills;
        this.profileImageUrl = profileImageUrl;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String  mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(String userSkills) {
        this.userSkills = userSkills;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

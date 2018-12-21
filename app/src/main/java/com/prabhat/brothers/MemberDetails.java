package com.prabhat.brothers;

/**
 * Created by HP on 04-May-18.
 */

public class MemberDetails {

    private String userName, userEmail, mobileNumber, userSkills, paymentStatus;
    private String monthsPending, userBio, paymentAmount, paymentAmountTotal, profileImageUrl;

    public MemberDetails() {
    }

    public MemberDetails(String userName, String userEmail, String mobileNumber, String userSkills, String paymentStatus, String monthsPending, String userBio, String paymentAmount, String paymentAmountTotal, String profileImageUrl) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.mobileNumber = mobileNumber;
        this.userSkills = userSkills;
        this.paymentStatus = paymentStatus;
        this.monthsPending = monthsPending;
        this.userBio = userBio;
        this.paymentAmount = paymentAmount;
        this.paymentAmountTotal = paymentAmountTotal;
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(String userSkills) {
        this.userSkills = userSkills;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMonthsPending() {
        return monthsPending;
    }

    public void setMonthsPending(String monthsPending) {
        this.monthsPending = monthsPending;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentAmountTotal() {
        return paymentAmountTotal;
    }

    public void setPaymentAmountTotal(String paymentAmountTotal) {
        this.paymentAmountTotal = paymentAmountTotal;
    }
}

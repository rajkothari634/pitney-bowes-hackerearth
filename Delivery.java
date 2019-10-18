package com.example.pitneybowes.Classes;

public class Delivery {
    public String Id;
    public String address;
    public String dateOfOrder;
    public String receiverEmailId;
    public String receiverPhoneNumber;
    public String receiverName;
    public String deliveryStatus;//(Completed or cancelled)
    public String deliveryPrice;
    public String modeOfPayment;
    public String paymentStatus;
    public String deliveryRating;
    public String deliveryBoyId;
    public String deliveryPriority;//(1 to 5)
    public String deliveryCompanyName;
    public String expOrCol = "collapsed";
    public String qrCodeSendStatus = "Not_Done";
    public String lat;
    public String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getQrCodeSendStatus() {
        return qrCodeSendStatus;
    }

    public void setQrCodeSendStatus(String qrCodeSendStatus) {
        this.qrCodeSendStatus = qrCodeSendStatus;
    }

    public String getExpOrCol() {
        return expOrCol;
    }

    public void setExpOrCol(String expOrCol) {
        this.expOrCol = expOrCol;
    }

    public String getDeliveryCompanyName() {
        return deliveryCompanyName;
    }

    public void setDeliveryCompanyName(String deliveryCompanyName) {
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public Delivery() {
    }

    public Delivery(String id, String address, String dateOfOrder, String receiverEmailId, String receiverPhoneNumber, String receiverName, String deliveryStatus, String deliveryPrice, String modeOfPayment, String paymentStatus, String deliveryRating, String deliveryBoyId, String deliveryPriority, String deliveryCompanyName) {
        Id = id;
        this.address = address;
        this.dateOfOrder = dateOfOrder;
        this.receiverEmailId = receiverEmailId;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
        this.deliveryPrice = deliveryPrice;
        this.modeOfPayment = modeOfPayment;
        this.paymentStatus = paymentStatus;
        this.deliveryRating = deliveryRating;
        this.deliveryBoyId = deliveryBoyId;
        this.deliveryPriority = deliveryPriority;
        this.deliveryCompanyName = deliveryCompanyName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getReceiverEmailId() {
        return receiverEmailId;
    }

    public void setReceiverEmailId(String receiverEmailId) {
        this.receiverEmailId = receiverEmailId;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(String deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryBoyId() {
        return deliveryBoyId;
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }

    public String getDeliveryPriority() {
        return deliveryPriority;
    }

    public void setDeliveryPriority(String deliveryPriority) {
        this.deliveryPriority = deliveryPriority;
    }
}

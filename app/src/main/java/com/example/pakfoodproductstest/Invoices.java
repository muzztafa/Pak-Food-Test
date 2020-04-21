package com.example.pakfoodproductstest;

public class Invoices {
    private String date;
    private String invoiceNo;
    private String shopsName;
    private String status;
    private String mobileNo;
    private String total_bill;

    private String paidDate;
    private String paidAmount;

    private String invoiceGenTime;
    private String invoicePayTime;

    private String receivedBy;

    public Invoices() {
    }



    public Invoices(String date, String invoiceNo, String shopsName, String status, String mobileNo, String total_bill) {
        this.date = date;
        this.invoiceNo = invoiceNo;
        this.shopsName = shopsName;
        this.status = status;
        this.mobileNo = mobileNo;

        this.total_bill = total_bill;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getDate() {
        return date;
    }

    public String getInvoiceGenTime() {
        return invoiceGenTime;
    }

    public void setInvoiceGenTime(String invoiceGenTime) {
        this.invoiceGenTime = invoiceGenTime;
    }

    public String getInvoicePayTime() {
        return invoicePayTime;
    }

    public void setInvoicePayTime(String invoicePayTime) {
        this.invoicePayTime = invoicePayTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getShopsName() {
        return shopsName;
    }

    public void setShopsName(String shopsName) {
        this.shopsName = shopsName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    public String getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }



    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }
}

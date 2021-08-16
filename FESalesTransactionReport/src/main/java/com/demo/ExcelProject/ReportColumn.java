package com.demo.ExcelProject;

public class ReportColumn {

	String transactionRefNo;
	String personalSaleId;
	String processingDate;
	String agentCode;
	String leadGenId;
	String transactionDate;
	String transactionUnit;
	String transactionAmount;
	String salesChargePercentage;
	String salesChargeAmount;
	String asnbSchemeCode;
	String uhAccountNo;
	String uhName;
	String uhIdType;
	String uhIdNo;
	String fundShortName;
	String filler;

	public ReportColumn(String processingDate, String agentCode, String personalSaleId,
						String leadGenId, String transactionRefNo, String transactionDate,
						String transactionUnit, String transactionAmount, String salesChargePercentage,
						String salesChargeAmount, String asnbSchemeCode, String uhAccountNo,
						String uhName, String uhIdType, String uhIdNo, String fundShortName,
						String filler) {
		super();

		this.transactionRefNo = transactionRefNo;
		this.personalSaleId = personalSaleId;
		this.processingDate = processingDate;
		this.agentCode = agentCode;
		this.leadGenId = leadGenId;
		this.transactionDate = transactionDate;
		this.transactionUnit = transactionUnit;
		this.transactionAmount = transactionAmount;
		this.salesChargePercentage = salesChargePercentage;
		this.salesChargeAmount = salesChargeAmount;
		this.asnbSchemeCode = asnbSchemeCode;
		this.uhAccountNo = uhAccountNo;
		this.uhName = uhName;
		this.uhIdType = uhIdType;
		this.uhIdNo = uhIdNo;
		this.fundShortName = fundShortName;
		this.filler= filler;

	}

	public String getTransactionRefNo() {
		return transactionRefNo;
	}

	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}

	public String getPersonalSaleId() {
		return personalSaleId;
	}

	public void setPersonalSaleId(String personalSaleId) {
		this.personalSaleId = personalSaleId;
	}

	public String getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(String processingDate) {
		this.processingDate = processingDate;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getLeadGenId() {
		return leadGenId;
	}

	public void setLeadGenId(String leadGenId) {
		this.leadGenId = leadGenId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionUnit() {
		return transactionUnit;
	}

	public void setTransactionUnit(String transactionUnit) {
		this.transactionUnit = transactionUnit;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getSalesChargePercentage() {
		return salesChargePercentage;
	}

	public void setSalesChargePercentage(String salesChargePercentage) {
		this.salesChargePercentage = salesChargePercentage;
	}

	public String getSalesChargeAmount() {
		return salesChargeAmount;
	}

	public void setSalesChargeAmount(String salesChargeAmount) {
		this.salesChargeAmount = salesChargeAmount;
	}

	public String getAsnbSchemeCode() {
		return asnbSchemeCode;
	}

	public void setAsnbSchemeCode(String asnbSchemeCode) {
		this.asnbSchemeCode = asnbSchemeCode;
	}

	public String getUhAccountNo() {
		return uhAccountNo;
	}

	public void setUhAccountNo(String uhAccountNo) {
		this.uhAccountNo = uhAccountNo;
	}

	public String getUhName() {
		return uhName;
	}

	public void setUhName(String uhName) {
		this.uhName = uhName;
	}

	public String getUhIdType() {
		return uhIdType;
	}

	public void setUhIdType(String uhIdType) {
		this.uhIdType = uhIdType;
	}

	public String getUhIdNo() {
		return uhIdNo;
	}

	public void setUhIdNo(String uhIdNo) {
		this.uhIdNo = uhIdNo;
	}

	public String getFundShortName() {
		return fundShortName;
	}

	public void setFundShortName(String fundShortName) {
		this.fundShortName = fundShortName;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}
}

package com.demo.ExcelProject;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;
import java.text.DecimalFormat;

public class FESalesTransactionReport {

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	public static void main(String[] args) {

		ArrayList<ReportColumn> a = new ArrayList();
		ArrayList<ReportColumn> b = new ArrayList();
		String totalAmount = "";

		try {

			String output = readConfigFile.main("app.output");

			String date = "";
			String month = "";
			String year = "";
			String monthStr = "";
			String configMonth = readConfigFile.main("app.month");
			String configYear = readConfigFile.main("app.year");
			String agentCodeConfig = readConfigFile.main("app.agentCode");
			String agentCodePadded = "";
			agentCodePadded = StringUtils.rightPad(agentCodeConfig, 4, "0") ;
			String transactionCode = readConfigFile.main("app.transactionCode");
			String subTransactionCode = readConfigFile.main("app.subTransactionCode");
			String frequency = readConfigFile.main("app.frequency");

			//--------------------
			//TO GENERATE FILENAME
			//--------------------

			Calendar cal = Calendar.getInstance();
			int currentDate = cal.get(Calendar.DATE) ;
			int prevMonth = cal.get(Calendar.MONTH) ; // beware of month indexing from zero
			String prevMonthStr = new DateFormatSymbols().getMonths()[prevMonth - 1];
			SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			System.out.println("date: " + dateOnly.format(cal.getTime()));
			System.out.println("month (config): " + configMonth);
			System.out.println("previous month: " + prevMonth);

			//if month in config file is not empty, it will generate report based on config file month.
			//if month in config file is empty, it will generate report for previous month

			if(configMonth.length() > 0){
				//config month got value. generate report for this month
				month = configMonth;
				monthStr = new DateFormatSymbols().getMonths()[Integer.parseInt(configMonth) - 1];
				if(configYear.equals("")){
					year = Integer.toString(cal.get(Calendar.YEAR));
				}else{
					year = configYear;
				}

			}else{
				//config month is empty. generate report for previous month
				month = Integer.toString(prevMonth);
				monthStr = prevMonthStr;

				if(month.equals("12")){
					year = Integer.toString(cal.get(Calendar.YEAR) - 1);
				}else{
					year = Integer.toString(cal.get(Calendar.YEAR));
				}

			}

			if(currentDate < 10){
				date = "0"+Integer.toString(currentDate);
			}else{
				date = Integer.toString(currentDate);
			}

			if(month.length() <2){
				month = "0"+month;
			}

			//--------------------
			//GET DATA
			//--------------------

			totalAmount =  getTotalAmount(year, month, agentCodeConfig, transactionCode);
			a =  getData(year, month, agentCodeConfig, transactionCode);

			//------------------------
			//GENERATE BODY CONTENT
			//------------------------

			FileWriter myWriter = new FileWriter(output+"/"+agentCodePadded+transactionCode
					+subTransactionCode+frequency+date+month+year+".txt");

			//write header
			String header = "";
			header = "0"+date+month+year+StringUtils.rightPad(Integer.toString(a.size()), 20)
					+StringUtils.rightPad(totalAmount, 30)+StringUtils.rightPad("", 100)+"XXXEOR\n";
			myWriter.write(header);

			//write content
			for(ReportColumn i : a) {
				myWriter.write("1" + i.getProcessingDate() + i.getAgentCode()
						+ i.getPersonalSaleId()	+ i.getLeadGenId() + i.getTransactionRefNo()
						+ "02" + i.getTransactionDate() + i.getTransactionUnit()
						+ i.getTransactionAmount() + i.getSalesChargePercentage()
						+ i.getSalesChargeAmount() + i.getAsnbSchemeCode()
						+ i.getUhAccountNo() + i.getUhName() + i.getUhIdType()
						+ i.getUhIdNo() + i.getFundShortName()
						+ StringUtils.rightPad(i.getFiller(), 85)+ "XXXEOR\n");
			}

			//write footer
			String footer = "";
			footer = "9"+StringUtils.rightPad("", 158)+"XXXEOF";
			myWriter.write(footer);

			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private static ArrayList<ReportColumn> getData(String year, String month, String agentCodeConfig, String transactionCode){

		int j = 1;
		Double totalAmount = 0.0;
		ArrayList<ReportColumn> a = new ArrayList();
		Connection conn = null;

		try {

			String dbURL = readConfigFile.main("app.dbURL");
			String user = readConfigFile.main("app.user");
			String pass = readConfigFile.main("app.password");

			conn = DriverManager.getConnection(dbURL, user, pass);

			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();

				//declare the statement object
				Statement sqlStatement = conn.createStatement();

				//declare the result set
				ResultSet rs = null;

				//Build the query string, making sure to use column aliases
				String queryString = "select * FROM AS_MonthlyTransaction \n" +
						"where Transaction_Date like '%"+month+year+"' \n"+
						"and Agent_Code like '"+agentCodeConfig+"%' \n"+
						"and "+transactionCode+" = CASE\n" +
						"\tWHEN ASNB_Scheme_Code in ('ASN', 'ASN2', 'ASN3', 'AASSGD', 'AASSGK', 'AASSGS', 'ASNE05', 'ASNS02', 'ASNI03') THEN  '01'\n" +
						"    WHEN ASNB_Scheme_Code in ('ASD', 'ASW', 'ASB', 'ASM', 'AS1M', 'ASB2') THEN  '02'\n" +
						"ELSE '03'\n" +
						"END \n"+
						"order by Transaction_Date \n";

				//print the query string to the screen
				System.out.println("\nQuery string:");
				System.out.println(queryString);

				//execute the query
				rs=sqlStatement.executeQuery(queryString);

				//loop through the result set and call method to print the result set row
				while (rs.next())
				{
					//printResultSetRow(rs);

					String processingDate= rs.getString("Process_Date");
					String agentCode= rs.getString("Agent_Code");
					String personalSaleId= rs.getString("Personal_Sale_ID");
					String leadGenId= rs.getString("Lead_Gen_ID");
					String transactionRefNo= rs.getString("Transaction_Ref_No");
					String transactionDate= rs.getString("Transaction_Date");
					String transactionUnit= rs.getString("Transaction_Unit");
					String transactionAmount= rs.getString("Transaction_Amount");
					String salesChargePercentage= rs.getString("Sales_Charge_Percentage");
					String salesChargeAmount= rs.getString("Sales_Charge_Amount");
					String asnbSchemeCode= rs.getString("ASNB_Scheme_Code");
					String uhAccountNo= rs.getString("UH_AccountNo");
					String uhName= rs.getString("UH_Name");
					String uhIdType= rs.getString("UH_IDType");
					String uhIdNo= rs.getString("UH_IDNo");
					String fundShortName= rs.getString("fund_short_name");
					String filler= "";


					try {

						a.add(new ReportColumn(processingDate, agentCode, personalSaleId,
								leadGenId, transactionRefNo, transactionDate, transactionUnit,
								transactionAmount, salesChargePercentage, salesChargeAmount,
								asnbSchemeCode, uhAccountNo, uhName, uhIdType, uhIdNo,
								fundShortName, filler));

						j  = j+1;

						//totalAmount = totalAmount + FESalesTransactionReport.getTotal(transactionAmount) ;
						totalAmount = totalAmount + Double.parseDouble(transactionAmount);

					}catch (Exception e) {
						e.printStackTrace();
					}

				}

				//close the result set
				rs.close();

				//close the database connection
				conn.close();
			}

		} catch (SQLException ex) {
			System.err.println("Error connecting to the database");
			ex.printStackTrace(System.err);
			System.exit(0);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return a;
	}

	private static String getTotalAmount(String year, String month, String agentCodeConfig, String transactionCode){

		int j = 1;
		ArrayList<ReportColumn> a = new ArrayList();
		Connection conn = null;
		double sumAmount  = 0.0;
		String totalAmount="";


		try {

			String dbURL = readConfigFile.main("app.dbURL");
			String user = readConfigFile.main("app.user");
			String pass = readConfigFile.main("app.password");

			conn = DriverManager.getConnection(dbURL, user, pass);

			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();

				//declare the statement object
				Statement sqlStatement = conn.createStatement();

				//declare the result set
				ResultSet rs = null;

				//Build the query string, making sure to use column aliases
				String queryString = "select sum(cast(Transaction_Amount as decimal(38,2))) as totalAmount FROM AS_MonthlyTransaction \n" +
						"where Transaction_Date like '%072021' \n" +
						"and Agent_Code like 'MBB%' \n" +
						"and 02 = CASE\n" +
						"\tWHEN ASNB_Scheme_Code in ('ASN', 'ASN2', 'ASN3', 'AASSGD', 'AASSGK', 'AASSGS', 'ASNE05', 'ASNS02', 'ASNI03') THEN  '01'\n" +
						"    WHEN ASNB_Scheme_Code in ('ASD', 'ASW', 'ASB', 'ASM', 'AS1M', 'ASB2') THEN  '02'\n" +
						"ELSE '03'\n" +
						"END ";

				//print the query string to the screen
				System.out.println("\nQuery string:");
				System.out.println(queryString);

				//execute the query
				rs=sqlStatement.executeQuery(queryString);

				//loop through the result set and call method to print the result set row
				while (rs.next())
				{
					//printResultSetRow(rs);

					totalAmount= rs.getString("totalAmount");

					try {

						sumAmount = sumAmount + Double.parseDouble(totalAmount);

						j  = j+1;

					}catch (Exception e) {
						e.printStackTrace();
					}

					totalAmount = df2.format(sumAmount);

				}

				//close the result set
				rs.close();

				//close the database connection
				conn.close();
			}

		} catch (SQLException ex) {
			System.err.println("Error connecting to the database");
			ex.printStackTrace(System.err);
			System.exit(0);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return totalAmount;
	}
}
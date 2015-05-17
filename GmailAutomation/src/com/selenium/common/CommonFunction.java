package com.selenium.common;

import com.thoughtworks.selenium.Selenium;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommonFunction {
    static WebDriver driver;
    private static final String KILL = "taskkill /F /IM ";
    public static Selenium selenium;
    public static String FRAMEWORKBASEBASRPATH = "test-output";
    private WebElement option1;

    public static void Explicitwait(int time) {
        long ms = time;
        try {
            Thread.sleep(ms * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static WebDriver getWebDriverInstance() {
        try {
            if (!(driver == null)) {
                CloseWebDriverInstance();
            }
        } catch (Exception e) {

        }
        // File file = new File("C:/GenericFramework/chromedriver.exe");
        // System.setProperty("webdriver.chrome.driver",
        // file.getAbsolutePath());
        DesiredCapabilities ieCapabilities = new DesiredCapabilities();
          ieCapabilities.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
          ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
          ieCapabilities.setCapability("ignoreProtectedModeSettings", true); ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
          ieCapabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true); driver = new InternetExplorerDriver(ieCapabilities);
        driver = new InternetExplorerDriver(ieCapabilities);

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
        caps.setJavascriptEnabled(true);
        caps.setCapability("ignoreProtectedModeSettings", true);
        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        caps.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        System.setProperty("webdriver.chrome.driver", "chromedriver-win.exe");
        driver = new ChromeDriver(caps);

        //selenium = new WebDriverBackedSelenium(driver, Environment.BROWSER_BASE_URL);
        return driver;
    }

    public static void CloseWebDriverInstance()

    {
        selenium.close();
    }

    public static void stopEverything() {
        selenium.stop();
        killTheServer();
    }

    public static ArrayList<Map<String, String>> ReadDataFile(String testDataSheetPath) throws IOException {
    	@SuppressWarnings("deprecation")
        Sheet testDataSheet = null;
        try{
        	testDataSheet =  new XSSFWorkbook("TestData/LoginDetails.xlsx").getSheet("CERT");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
       
        Row nextDataRow;
        Row firstDataRow;
        String key, value;
        String isDataFileEnds = new String();
        String isDataColumnEnds = new String();
        String BLANK_SPACE = "";
        ArrayList<Map<String, String>> dataForTestCase = new ArrayList<Map<String, String>>();
        int noOfRows = testDataSheet.getPhysicalNumberOfRows();
        nextDataRow = testDataSheet.getRow(1);
        for (int rowCount = 1; rowCount <= noOfRows; rowCount++) {
            Map<String, String> data = new HashMap<String, String>();
            try {
                isDataFileEnds = nextDataRow.getCell(0).toString().replace('"', ' ').trim();
            } catch (Exception e) {
                isDataFileEnds = BLANK_SPACE;
            }
            if (isDataFileEnds.equalsIgnoreCase("END") || isDataFileEnds.equalsIgnoreCase(BLANK_SPACE)) {
                break;
            } else {
                if (!(nextDataRow.getCell(0).toString().replace('"', ' ').trim().equalsIgnoreCase("Y"))) {
                    nextDataRow = testDataSheet.getRow(rowCount + 1);
                } else {
                    int colCount;
                    int noOfColls = nextDataRow.getPhysicalNumberOfCells();
                    firstDataRow = testDataSheet.getRow(0);
                    for (colCount = 1; colCount <= noOfColls; colCount++) {
                        try {
                            isDataColumnEnds = firstDataRow.getCell(colCount).toString().replace('"', ' ').trim();
                        } catch (Exception e) {
                            isDataColumnEnds = BLANK_SPACE;
                        }
                        if (isDataColumnEnds.equalsIgnoreCase(BLANK_SPACE)) {
                            dataForTestCase.add(data);
                            break;
                        } else {
                            key = (firstDataRow.getCell(colCount).toString().replace('"', ' ').trim());
                            value = (nextDataRow.getCell(colCount).toString().trim());
                            if (value.contains("~")) {
                                value = value.replace("~", " ");
                            }
                        }
                        if (!(key == "")) {
                            data.put(key, value.trim());
                        }
                    }
                    nextDataRow = testDataSheet.getRow(rowCount + 1);
                }
            }
        }
        return dataForTestCase;
    }
    
    
    
    
    
    

    public static void killTheServer() {

        try {

            Runtime.getRuntime().exec(KILL + "chromedriver-win.exe");
            Runtime.getRuntime().exec(KILL + "IEDriverServer.exe");

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
/*
    public static Function<WebDriver, WebElement> presenceOfElementLocated(final By locator) {
        return new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        };
    }*/

   /* public static void WaitForElementtoLoad(final By locator, int timeOutPeriod, int explicitWaitTime) {

        WebDriverWait wait = new WebDriverWait(driver, timeOutPeriod);
        WebElement element = wait.until(presenceOfElementLocated(locator));
        CommonFunction.Explicitwait(explicitWaitTime);
    }
*/
    /* WebElement select = driver.findElement(By.id("selection")); */

    /*
     * List<WebElement> options = select.findElements(By.tagName("option1")); for (WebElement option1 : options) { if("RDA".equals(option1.getText()))
     * option1.click(); }
     */

    /*public static ArrayList<Object[]> createTestCaseFlows() throws Throwable {
        ArrayList<Map<String, String>> myEntries = CommonFunction.ReadDataFile(CommonFunction.FRAMEWORKBASEBASRPATH + "/TestDataPurchase.xlsx");
        ArrayList<Object[]> applicationFlows = new ArrayList<Object[]>();
        for (Iterator iterator = myEntries.iterator(); iterator.hasNext();) {
            Map<String, String> singleApplicationFlow = (Map<String, String>) iterator.next();
            String actvnTypeCodeDealer = DataBaseOperation.getActivationTypeCode(singleApplicationFlow.get("dealerTerminalNumber"));
            String actvnTypeCodeCustomer = DataBaseOperation.getActivationTypeCode(singleApplicationFlow.get("customerTerminalNumber"));
            String subscriptionTypeCodeDealer = LmcSmcPage.getSubscriptionTypeOfTerminal(singleApplicationFlow.get("dealerTerminalNumber"));
            String subscriptionTypeCodeCustomer = LmcSmcPage.getSubscriptionTypeOfTerminal(singleApplicationFlow.get("customerTerminalNumber"));

            for (String duration : ApplicationFlows.Duration) {
                singleApplicationFlow.put("Duration", duration);
                for (String Subscription : ApplicationFlows.Subscription) {
                    singleApplicationFlow.put("Subscription", Subscription);
                    for (String paymentType : ApplicationFlows.paymentType) {
                        singleApplicationFlow.put("paymentType", paymentType);
                        for (String LoginType : ApplicationFlows.LoginType) {

                            String actvnTypeCode = new String();
                            String subscrpnTypeCode = new String();

                            if (!(LoginType.contains("Customer"))) {
                                actvnTypeCode = actvnTypeCodeDealer;
                                subscrpnTypeCode = subscriptionTypeCodeDealer;
                            } else {
                                actvnTypeCode = actvnTypeCodeCustomer;
                                subscrpnTypeCode = subscriptionTypeCodeCustomer;
                            }

                            if ((actvnTypeCode.equalsIgnoreCase("63") && Subscription.contains("Ultimate"))
                                    || (actvnTypeCode.equalsIgnoreCase("62") && Subscription.contains("Select"))
                                    || ((singleApplicationFlow.get("paymentType").equalsIgnoreCase("CTD")) && (!LoginType.contains("Dealer")))
                                    || (subscrpnTypeCode.equalsIgnoreCase("CELLULAR") && Subscription.contains("Dual"))
                                    || (subscrpnTypeCode.equalsIgnoreCase("DUAL") && !Subscription.contains("Dual"))
                                    || (!(duration.contains("1 Year")) && singleApplicationFlow.get("paymentType").equalsIgnoreCase("CreditCard"))) {

                                applicationFlows.add(new Object[] { addDataToHashMap(singleApplicationFlow, "LoginType", LoginType) });

                            }
                        }
                    }
                }

            }
            System.out.println(applicationFlows.size());
        }
        return applicationFlows;
    }*/

    /*public static Map<String, String> addDataToHashMap(Map<String, String> map, String key, String Value) {
        Iterator<E> iterator = map.keySet().iterator();
        Map<String, String> appendeMap = new HashMap<String, String>();
        ;
        while (iterator.hasNext()) {
            String newkey = new String();
            newkey = iterator.next().toString();
            appendeMap.put(newkey, map.get(newkey));
        }

        appendeMap.put(key, Value);
        return (appendeMap);
    }
*/
    public static void capture_Screen_Shot(String FilePath) throws Throwable {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(FilePath));
    }

    

    public static int ConvertStringtoInteger(String stringval) {
        return (Integer.parseInt(stringval));
    }

    public static int ConvertMonthtoInteger(String stringval) {
        if (stringval.equalsIgnoreCase("Jan"))
            return 0;
        else if (stringval.equalsIgnoreCase("feb"))
            return 1;
        else if (stringval.equalsIgnoreCase("mar"))
            return 2;
        else if (stringval.equalsIgnoreCase("apr"))
            return 3;
        else if (stringval.equalsIgnoreCase("may"))
            return 4;
        else if (stringval.equalsIgnoreCase("jun"))
            return 5;
        else if (stringval.equalsIgnoreCase("jul"))
            return 6;
        else if (stringval.equalsIgnoreCase("aug"))
            return 7;
        else if (stringval.equalsIgnoreCase("sep"))
            return 8;
        else if (stringval.equalsIgnoreCase("oct"))
            return 9;
        else if (stringval.equalsIgnoreCase("nov"))
            return 10;
        else if (stringval.equalsIgnoreCase("dec"))
            return 11;

        else
            return -1;

    }

    public static int absoluteDaysDiff(GregorianCalendar calendar, GregorianCalendar calendar1) {
        final long MSECS_PER_DAY = 86400000;
        float value = 0;

        try {
            long diff = (calendar1.getTimeInMillis() - calendar.getTimeInMillis());
            value = Math.abs(diff / MSECS_PER_DAY);

            if (value < 0) {
                value *= -1;
            }

        } catch (Exception e) {
            value = -999999999;
        }
        return (int) value;
    }

    public static int generate_random_number(String aStart, String aEnd) throws Exception {
        boolean isRandomNumberGenerated = false;
        Random random = new Random();
        int randomNumber = 0;
        Integer start, end;
        start = Integer.parseInt(aStart);
        end = Integer.parseInt(aEnd);
        try {
            if (start > end) {
                throw new IllegalArgumentException("Start cannot exceed End.");
            }

            long range = (long) end - (long) start + 1;

            long fraction = (long) (range * random.nextDouble());
            randomNumber = (int) (fraction + start);
            isRandomNumberGenerated = true;
        } catch (Throwable throwable) {
        }

        return randomNumber;
    }

   /* public static void writeTheTeminalNumberToDataTable(String FileName, int rowNumber, String columnName, String cellValue)

    {

        try {
            InputStream inp = new FileInputStream(FileName);

            Workbook wb = (Workbook) WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheet(Environment.testEnvironment);
            int columnCount;

            Row row = sheet.getRow(rowNumber);
            Row firstRow = sheet.getRow(0);
            columnCount = row.getPhysicalNumberOfCells();

            for (int j = 0; j < columnCount; j++) {
                Cell cell = firstRow.getCell(j);
                if (cell.toString().trim().equalsIgnoreCase(columnName)) {
                    cell = row.getCell(j);
                    if (cell == null)
                        cell = row.createCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(cellValue);
                    break;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(FileName);
            ((org.apache.poi.ss.usermodel.Workbook) wb).write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*public static double getDiffOfSourceAndDestinationTerminalEndDates(String source, String destination) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss z");

        Date todaysDate = new Date();
        Date sourceDate = sdf.parse(source);
        Date destDate = sdf.parse(destination);
        float dateDiff = (sourceDate.getTime() - todaysDate.getTime()) + (destDate.getTime() - todaysDate.getTime());
        float noOfDays = dateDiff / (1000 * 60 * 60 * 24);
        return Math.floor(noOfDays);

    }

    @Test
    public static int getSizeOFDataTable(String FileName) throws IOException

    {
        Sheet testDataSheet = new XSSFWorkbook(FileName).getSheet(Environment.testEnvironment);
        Row row = testDataSheet.getRow(0);
        int i = 1;
        while (!row.getCell(0).toString().trim().equalsIgnoreCase("END")) {
            i++;
            row = testDataSheet.getRow(i);
        }

        return i - 1;
    }

    public static void acceptSecurityPopUp() {
        int flag = 1;
        int counter = 0;

        while (flag == 1 && counter < 100) {
            try {
                driver.switchTo().alert().accept();
                flag = 0;
            } catch (Exception e) {

            }
            counter++;
        }
    }

    public static Iterator<Object[]> readGivenFile(String functionality ,String filename) throws Exception {
        
        String completePath = getCompletePath(functionality);
        ArrayList<Map<String, String>> tempMyEntries = CommonFunction.ReadDataFile(completePath);
        ArrayList<Object[]> myEntries = new ArrayList<Object[]>();
        for (Iterator<Map<String, String>> iterator = tempMyEntries.iterator(); iterator.hasNext();) {
            Map<String, String> map = (Map<String, String>) iterator.next();
            myEntries.add(new Object[] { map });
        }

        return myEntries.iterator();
    }
    */
    public static Iterator<Object[]> readGivenFile(String filename) throws Exception {    
        
        ArrayList<Map<String, String>> tempMyEntries = CommonFunction.ReadDataFile("TestData/" + filename);
        ArrayList<Object[]> myEntries = new ArrayList<Object[]>();
        for (Iterator<Map<String, String>> iterator = tempMyEntries.iterator(); iterator.hasNext();) {
            Map<String, String> map = (Map<String, String>) iterator.next();
            myEntries.add(new Object[] { map });
        }

        return myEntries.iterator();
    }

   /* private static String getCompletePath(String functionalityToBeTested) throws IOException {
        File file = new File("TestData");
        if (functionalityToBeTested.equals("PositionReceiverDeactivation")){
            functionalityToBeTested = file.getCanonicalPath() + File.separator + POSITION_RECEIVER_DATA_FILE_NAME;
            return functionalityToBeTested ;
        }
        return "";
    }

    public static void signIn(WebDriver driver) throws IOException, FileNotFoundException, Throwable {
        if (Environment.testEnvironment.equalsIgnoreCase("TAL")) {
            driver.get("https://itapp31.tal.deere.com/" + ApplicationURL.DeleteCookiesURL);
            SignInPage signInPage = PageFactory.initElements(driver, SignInPage.class);
            signInPage.SighIntoApplication(Environment.adminuid, Environment.adminpasw);
            driver.get(Environment.BROWSER_BASE_URL + ApplicationURL.DeleteCookiesURL);
        } else {
            driver.get(Environment.BROWSER_BASE_URL + ApplicationURL.DeleteCookiesURL);

            SignInPage signInPage = PageFactory.initElements(driver, SignInPage.class);
            signInPage.SighIntoApplication(Environment.adminuid, Environment.adminpasw);
        }
    }
    
    public static SignInPage fetchSignInAfterLoginToTestEnvironment(WebDriver driver) throws IOException, FileNotFoundException, Throwable {
        SignInPage signInPage = PageFactory.initElements(driver, SignInPage.class);
        if (Environment.testEnvironment.equalsIgnoreCase("TAL")) {
            driver.get("https://itapp31.tal.deere.com/" + ApplicationURL.DeleteCookiesURL);
            signInPage.SighIntoApplication(Environment.adminuid, Environment.adminpasw);
            driver.get(Environment.BROWSER_BASE_URL + ApplicationURL.DeleteCookiesURL);
        } else {
            driver.get(Environment.BROWSER_BASE_URL + ApplicationURL.DeleteCookiesURL);
            signInPage.SighIntoApplication(Environment.adminuid, Environment.adminpasw);
        }
        return signInPage ;
    }
    
    public static String expectedSubscriptionEndDateAfterActivatingDemoActivation() throws Throwable {
        Calendar date = Calendar.getInstance();  
        date.setTime(new Date());  
        Format f = new SimpleDateFormat("MMM dd, yyyy");  
        date.add(Calendar.YEAR,3);
        String expectedSubscriptionEndDate = f.format(date.getTime());
        return expectedSubscriptionEndDate ;
    }
    */
    
    public static boolean checkWhetherTestShouldBeExcuted(String testCaseName) throws IOException {
    	String filePath = "TestData\\TestData.xlt";
		Vector paramData = new Vector();
		TestData td = new TestData(filePath);
		td.getDatafromXL();
		paramData = td.getParamData();

		for (int i = 0; i < paramData.size(); i++) {
			if (paramData.get(i).equals(testCaseName)) {
				String str = (String) paramData.get(i - 1);
				if(str.equals("Y")){
					return true; 
				}
			}
		}
		return false;
	}
    
    
}
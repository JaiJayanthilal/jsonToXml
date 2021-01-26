package org.converter.jsontoxml;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple ConverterFactory.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
	{
		ConverterFactory cf = new ConverterFactory();

		String inputFile = getClass().getClassLoader().getResource("inputTestCase1.json").getPath();

		try {
			File f = new File("src/test/resources/outputTestCase1.xml");
			System.out.println(f.getPath());
			String outputFile = f.getAbsolutePath();
			cf.convertJSONtoXML(inputFile, outputFile);

			//String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><object><object name=\"organization\"><String name=\"name\">RiskSense</String><String name=\"type\">Inc</String><Integer name=\"building_number\">4</Integer><BigDecimal name=\"floating\">-17.4</BigDecimal><null name=\"null_test\"/><object name=\"jai\"><String name=\"lastName\">jayanthilal</String></object><array name=\"array_example2\"><String>2red</String><String>2green</String><String>2blue</String><String>2black</String></array></object><Boolean name=\"security_related\">true</Boolean><array name=\"array_example0\"><String>red</String><String>green</String><String>blue</String><String>black</String></array><array name=\"array_example1\"><Integer>1</Integer><String>red</String><array><object><Boolean name=\"nested\">true</Boolean></object></array><object><Boolean name=\"obj\">true</Boolean></object></array></object>";
			String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
					"<object>\r\n" + 
					"  <object name=\"organization\">\r\n" + 
					"    <String name=\"name\">RiskSense</String>\r\n" + 
					"    <String name=\"type\">Inc</String>\r\n" + 
					"    <Integer name=\"building_number\">4</Integer>\r\n" + 
					"    <BigDecimal name=\"floating\">-17.4</BigDecimal>\r\n" + 
					"    <null name=\"null_test\"/>\r\n" + 
					"    <object name=\"jai\">\r\n" + 
					"      <String name=\"lastName\">jayanthilal</String>\r\n" + 
					"    </object>\r\n" + 
					"    <array name=\"array_example2\">\r\n" + 
					"      <String>2red</String>\r\n" + 
					"      <String>2green</String>\r\n" + 
					"      <String>2blue</String>\r\n" + 
					"      <String>2black</String>\r\n" + 
					"    </array>\r\n" + 
					"  </object>\r\n" + 
					"  <Boolean name=\"security_related\">true</Boolean>\r\n" + 
					"  <array name=\"array_example0\">\r\n" + 
					"    <String>red</String>\r\n" + 
					"    <String>green</String>\r\n" + 
					"    <String>blue</String>\r\n" + 
					"    <String>black</String>\r\n" + 
					"  </array>\r\n" + 
					"  <array name=\"array_example1\">\r\n" + 
					"    <Integer>1</Integer>\r\n" + 
					"    <String>red</String>\r\n" + 
					"    <array>\r\n" + 
					"      <object>\r\n" + 
					"        <Boolean name=\"nested\">true</Boolean>\r\n" + 
					"      </object>\r\n" + 
					"    </array>\r\n" + 
					"    <object>\r\n" + 
					"      <Boolean name=\"obj\">true</Boolean>\r\n" + 
					"    </object>\r\n" + 
					"  </array>\r\n" + 
					"</object>\r\n" + 
					"";
			String computed = cf.strResult;
			if (toTest.equalsIgnoreCase(computed)) {
				System.out.println("Sucess");
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void testApp2()
	{

		ConverterFactory cf = new ConverterFactory();

		String inputFile = getClass().getClassLoader().getResource("inputTestCase2.json").getPath();
		try {
			File f = new File("src/test/resources/outputTestCase2.xml");
			System.out.println(f.getPath());
			String outputFile = f.getAbsolutePath();
			cf.convertJSONtoXML(inputFile, outputFile);

			String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
					"<object>\r\n" + 
					"  <array name=\"\">\r\n" + 
					"    <Integer>1</Integer>\r\n" + 
					"    <String>test</String>\r\n" + 
					"  </array>\r\n" + 
					"</object>\r\n" + 
					"";
			String computed = cf.strResult;
			if (toTest.equalsIgnoreCase(computed)) {
				System.out.println("Sucess");
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void testApp3()
	{

		ConverterFactory cf = new ConverterFactory();

		String inputFile = getClass().getClassLoader().getResource("inputTestCase3.json").getPath();
		try {
			File f = new File("src/test/resources/outputTestCase3.xml");
			System.out.println(f.getPath());
			String outputFile = f.getAbsolutePath();
			cf.convertJSONtoXML(inputFile, outputFile);

			String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
					"<object>\r\n" + 
					"  <null name=\"nullCheck\"/>\r\n" + 
					"</object>\r\n" + 
					"";
			String computed = cf.strResult;
			if (toTest.equalsIgnoreCase(computed)) {
				System.out.println("Sucess");
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    
    public void testApp4()
	{

		ConverterFactory cf = new ConverterFactory();

		String inputFile = getClass().getClassLoader().getResource("inputTestCase4.json").getPath();
		try {
			File f = new File("src/test/resources/outputTestCase4.xml");
			System.out.println(f.getPath());
			String outputFile = f.getAbsolutePath();
			cf.convertJSONtoXML(inputFile, outputFile);

			String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
					"<Null/>\r\n" + 
					"";
			String computed = cf.strResult;
			if (toTest.equalsIgnoreCase(computed)) {
				System.out.println("Sucess");
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    public void testApp5()
	{

		ConverterFactory cf = new ConverterFactory();

		String inputFile = getClass().getClassLoader().getResource("inputTestCase5.json").getPath();
		try {
			File f = new File("src/test/resources/outputTestCase5.xml");
			System.out.println(f.getPath());
			String outputFile = f.getAbsolutePath();
			cf.convertJSONtoXML(inputFile, outputFile);

			String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
					"<Boolean>false</Boolean>\r\n" + 
					"";
			String computed = cf.strResult;
			if (toTest.equalsIgnoreCase(computed)) {
				System.out.println("Sucess");
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    public void testApp6()
	{

		ConverterFactory cf = new ConverterFactory();

		String inputFile = getClass().getClassLoader().getResource("inputTestCase6.json").getPath();
		try {
			File f = new File("src/test/resources/outputTestCase6.xml");
			System.out.println(f.getPath());
			String outputFile = f.getAbsolutePath();
			cf.convertJSONtoXML(inputFile, outputFile);

			String toTest = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
					"<object>\r\n" + 
					"  <array name=\"fibs\">\r\n" + 
					"    <Integer>0</Integer>\r\n" + 
					"    <Integer>1</Integer>\r\n" + 
					"    <Integer>1</Integer>\r\n" + 
					"    <Integer>2</Integer>\r\n" + 
					"    <Integer>3</Integer>\r\n" + 
					"    <String>fibs</String>\r\n" + 
					"    <Boolean>true</Boolean>\r\n" + 
					"  </array>\r\n" + 
					"</object>\r\n" + 
					"";
			String computed = cf.strResult;
			if (toTest.equalsIgnoreCase(computed)) {
				System.out.println("Sucess");
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}

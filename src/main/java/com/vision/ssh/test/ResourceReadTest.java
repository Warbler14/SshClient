package com.vision.ssh.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ResourceReadTest {

    
    public static void main(String[] args) {
    	Path relativePath = Paths.get("");
        String path = relativePath.toAbsolutePath().toString();
        System.out.println(path);
    	
    	File file = new File("test/test-application-prod.properties");
    	System.out.println( file.exists());
    	
    	ResourceReadTest test = new ResourceReadTest();
    	
    	try {
			test.printResource("application-prod.properties");
			
			test.printResource("/com/vision/ssh/test/application-prod.properties");
			
			printResource(ResourceReadTest.class, "application-prod.properties");
			
			printResource(ResourceReadTest.class, "/com/vision/ssh/test/application-prod.properties");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
    
    public void printResource(String path) throws IOException {
    	InputStream inputStream = getClass().getResourceAsStream(path);
        printInputStream(inputStream);
        inputStream.close();
    }
    
    public static void printResource(Class<?> clazz, String path) throws IOException {
        InputStream inputStream = clazz.getResourceAsStream(path);
        printInputStream(inputStream);
        inputStream.close();
    }
    
    public static void printInputStream(InputStream inputStream) {
    	Scanner scanner = new Scanner (inputStream);
        while(scanner.hasNext()) {
        	System.out.println(scanner.nextLine());        	
        }
        scanner.close();
    }
}

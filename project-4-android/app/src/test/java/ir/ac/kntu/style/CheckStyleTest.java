package ir.ac.kntu.style;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CheckStyleTest {

    @Test
    public void testCheckStyleIndentation() throws CheckstyleException {

        /*
         * Files
         */
        File ROOT = new File("src/main/");
        System.out.println("Root is set to \"" + ROOT.getAbsolutePath() + "\".");

        List<File> files = new ArrayList<>();
        listFiles(files, ROOT, "java");
        System.out.println("Found " + files.size() + " Java source files.");

        /*
         * Listener
         */
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(sos, AbstractAutomaticBean.OutputStreamOptions.NONE);

        /*
         * Configuration
         */
        InputSource inputSource = new InputSource(this.getClass().getResourceAsStream("config.xml"));
        Configuration configuration = ConfigurationLoader.loadConfiguration(inputSource, new PropertiesExpander(System.getProperties()), ConfigurationLoader.IgnoredModulesOptions.OMIT);

        /*
         * Create checker
         */
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(configuration);
        checker.addListener(listener);

        /*
         * Process
         */
        int errors = 0;
        try {
            errors = checker.process(files);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Found " + errors + " check style errors.");
        assertEquals(0, errors, errors + " check style errors found. " + sos);

        /*
         * Clean up
         */
        checker.destroy();
    }

    @Test
    public void testCheckStyleNaming() throws CheckstyleException {

        /*
         * Files
         */
        File ROOT = new File("src/main/");
        System.out.println("Root is set to \"" + ROOT.getAbsolutePath() + "\".");

        List<File> files = new ArrayList<>();
        listFiles(files, ROOT, "java");
        System.out.println("Found " + files.size() + " Java source files.");

        /*
         * Listener
         */
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(sos, AbstractAutomaticBean.OutputStreamOptions.NONE);

        /*
         * Configuration
         */
        InputSource inputSource = new InputSource(this.getClass().getResourceAsStream("config.xml"));
        Configuration configuration = ConfigurationLoader.loadConfiguration(inputSource, new PropertiesExpander(System.getProperties()), ConfigurationLoader.IgnoredModulesOptions.OMIT);

        /*
         * Create checker
         */
        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(configuration);
        checker.addListener(listener);

        /*
         * Process
         */
        int errors = 0;
        try {
            errors = checker.process(files);
        } catch (CheckstyleException ex) {
            Logger.getLogger(CheckStyleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Found " + errors + " check style errors.");
        assertEquals(0, errors, errors + " check style errors found. " + sos);

        /*
         * Clean up
         */
        checker.destroy();
    }

    private static void listFiles(List<File> files, File folder, String extension) {
        if (folder.canRead()) {
            if (folder.isDirectory()) {
                for (File f : Objects.requireNonNull(folder.listFiles())) {
                    listFiles(files, f, extension);
                }
            } else if (folder.toString().endsWith("." + extension)) {
                files.add(folder);
            }
        }
    }

    public static void main(String[] args) throws CheckstyleException {
        CheckStyleTest checkStyleTest = new CheckStyleTest();
        checkStyleTest.testCheckStyleNaming();
    }
}

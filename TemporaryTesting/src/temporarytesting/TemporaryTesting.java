/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package temporarytesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author vavasthi
 */
public class TemporaryTesting {
    private final static String exceptionWordPattern_ = "("
                    + "all|"
                    + "another|"
                    + "a|"
                    + "an|"
                    + "and|"
                    + "any|"
                    + "anybody|"
                    + "anyone|"
                    + "anything|"
                    + "both|"
                    + "each|"
                    + "either|"
                    + "everybody|"
                    + "everyone|"
                    + "everything|"
                    + "few|"
                    + "he|"
                    + "her|"
                    + "hers|"
                    + "herself|"
                    + "him|"
                    + "himself|"
                    + "his|"
                    + "i|"
                    + "it|"
                    + "its|"
                    + "itself|"
                    + "many|"
                    + "me|"
                    + "mine|"
                    + "more|"
                    + "most|"
                    + "much|"
                    + "my|"
                    + "myself|"
                    + "neither|"
                    + "no one|"
                    + "nobody|"
                    + "none|"
                    + "nothing|"
                    + "one|"
                    + "other|"
                    + "others|"
                    + "our|"
                    + "ours|"
                    + "ourselves|"
                    + "several|"
                    + "she|"
                    + "some|"
                    + "somebody|"
                    + "someone|"
                    + "something|"
                    + "that|"
                    + "the|"
                    + "their|"
                    + "theirs|"
                    + "them|"
                    + "themselves|"
                    + "these|"
                    + "they|"
                    + "this|"
                    + "those|"
                    + "us|"
                    + "we|"
                    + "what|"
                    + "whatever|"
                    + "which|"
                    + "whichever|"
                    + "who|"
                    + "whoever|"
                    + "whom|"
                    + "whomever|"
                    + "whose|"
                    + "you|"
                    + "your|"
                    + "yours|"
                    + "yourself|"
                    + "yourselves)" + "\\b";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String str0 = "93what 93 where93 94 93where94";
        System.out.println(str0.replaceAll("\\b[0-9]+\\b", ""));
        String str1 = "This, is, a,new, String";
        StringTokenizer st = new StringTokenizer(str1, ", ");        
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
        System.out.println("============================");
        Pattern p = Pattern.compile("[\\p{Punct}]");

        Matcher m = p.matcher("One day! when I was walking. I found your pants? just kidding...");
        System.out.println(new String("One day! when I was walking. I found your pants? just kidding...").replaceAll("[\\p{Punct}]", ""));
        int count = 0;
        while (m.find()) {
            count++;
            System.out.println("\nMatch number: " + count);
            System.out.println("start() : " + m.start());
            System.out.println("end()   : " + m.end());
            System.out.println("group() : " + m.group());
        }        
        System.out.println("============================");
        String test1 = "<This is an interesting stirng>";
        String test2 = "<this is an interesting stirng>";
        String test3 = "this ee";
        if (test2.matches(".*" + exceptionWordPattern_ +".*")) {
            System.out.println(test2 + " matches.");
        }
        else {
            
            System.out.println(test2 + " does not match.");
        }
        if (test3.matches(".*" + exceptionWordPattern_ +".*")) {
            System.out.println(test3 + " matches.");
        }
        else {
            
            System.out.println(test3 + " does not match.");
        }
        System.out.println(test1.replaceAll("[<>]", ""));
        String sd = new String("27 Mar 91 17:35:40 -500");
        if (sd.matches("\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+[+-]\\d{1,4}?")) {
            try {
                System.out.println("String matches");
                
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss X");
                Date d = sdf.parse(sd);
                System.out.println(d);
            } catch (ParseException ex) {
                Logger.getLogger(TemporaryTesting.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }

    }
    
}

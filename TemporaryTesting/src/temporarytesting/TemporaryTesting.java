/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package temporarytesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

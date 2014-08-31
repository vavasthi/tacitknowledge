
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.ConfusionMatrix;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.JointClassification;
import com.aliasi.classify.JointClassifier;
import com.aliasi.classify.JointClassifierEvaluator;
import com.aliasi.classify.LMClassifier;

import com.aliasi.lm.NGramProcessLM;

import com.aliasi.util.AbstractExternalizable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;


import com.aliasi.util.Files;

public class ClassifyNews {
    private static File TRAINING_DIR
        = new File("/home/vavasthi/amazon/instantvideo/train");

    private static File TESTING_DIR
        = new File("/home/vavasthi/amazon/instantvideo/test");

    private static String[] CATEGORIES = null;

    static Set<String> set = new HashSet<String>();
    
    private static int NGRAM_SIZE = 6;

    static private void populateCategories() {
        
        {
            String[] l = TRAINING_DIR.list(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        
                        File f = new File(dir, name);
                        return f.isDirectory();
                    }
                
                });
            for (String s : l) {
                set.add(s);
            }
        }
        CATEGORIES = set.toArray(new String[set.size()]);
    }
        
    public static void main(String[] args)
        throws ClassNotFoundException, IOException {

        populateCategories();
        DynamicLMClassifier<NGramProcessLM> classifier
            = DynamicLMClassifier.createNGramProcess(CATEGORIES,NGRAM_SIZE);

        for(int i=0; i<CATEGORIES.length; ++i) {
            File classDir = new File(TRAINING_DIR,CATEGORIES[i]);
            if (!classDir.isDirectory()) {
                String msg = "Could not find training directory="
                    + classDir
                    + "\nHave you unpacked 4 newsgroups?";
                System.out.println(msg); // in case exception gets lost in shell
                throw new IllegalArgumentException(msg);
            }

            String[] trainingFiles = classDir.list();
            for (int j = 0; j < trainingFiles.length; ++j) {
                File file = new File(classDir,trainingFiles[j]);
                String text = Files.readFromFile(file,"ISO-8859-1");
                System.out.println("Training on " + CATEGORIES[i] + "/" + trainingFiles[j]);
                Classification classification
                    = new Classification(CATEGORIES[i]);
                Classified<CharSequence> classified
                    = new Classified<CharSequence>(text,classification);
                classifier.handle(classified);
            }
        }
        //compiling
        System.out.println("Compiling");
        @SuppressWarnings("unchecked") // we created object so know it's safe
        JointClassifier<CharSequence> compiledClassifier
            = (JointClassifier<CharSequence>)
            AbstractExternalizable.compile(classifier);

        boolean storeCategories = true;
        JointClassifierEvaluator<CharSequence> evaluator
            = new JointClassifierEvaluator<CharSequence>(compiledClassifier,
                                                         CATEGORIES,
                                                         storeCategories);
        for(int i = 0; i < CATEGORIES.length; ++i) {
            File classDir = new File(TESTING_DIR,CATEGORIES[i]);
            if (classDir.isDirectory()) {
                
                String[] testingFiles = classDir.list();
                for (int j=0; j<testingFiles.length;  ++j) {
                    String text
                        = Files
                        .readFromFile(new File(classDir,testingFiles[j]),"ISO-8859-1");
                    System.out.print("Testing on " + CATEGORIES[i] + "/" + testingFiles[j] + " ");
                    Classification classification
                        = new Classification(CATEGORIES[i]);
                    Classified<CharSequence> classified
                        = new Classified<CharSequence>(text,classification);
                    evaluator.handle(classified);
                    JointClassification jc =
                        compiledClassifier.classify(text);
                    String bestCategory = jc.bestCategory();
                    String details = jc.toString();
                    System.out.println("Got best category of: " + bestCategory);
                    System.out.println(jc.toString());
                    System.out.println("---------------");
                }
            }
        }
        ConfusionMatrix confMatrix = evaluator.confusionMatrix();
        System.out.println("Total Accuracy: " + confMatrix.totalAccuracy());

        System.out.println("\nFULL EVAL");
        System.out.println(evaluator);
    }
}

import com.aliasi.util.Files;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;

import com.aliasi.lm.NGramProcessLM;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class PolarityBasic {

    File mPolarityDir;
    String[] mCategories;
    int[] cases;
    int[] correctCases;
    DynamicLMClassifier<NGramProcessLM> mClassifier;

    PolarityBasic(String[] args) {
        System.out.println("\nBASIC POLARITY DEMO");
        mPolarityDir = new File(args[0]);
        System.out.println("\nData Directory=" + mPolarityDir);
        mCategories = mPolarityDir.list();
	cases = new int[mCategories.length];
	correctCases = new int[mCategories.length];
        int nGram = 8;
        mClassifier 
            = DynamicLMClassifier
            .createNGramProcess(mCategories,nGram);
    }

    void run() throws ClassNotFoundException, IOException {
        train();
        evaluate();
    }

    boolean isTrainingFile(File file) {
        //        return file.getName().charAt(2) != '9';  // test on fold 9
        StringTokenizer st = new StringTokenizer(file.getName(), "-");
        String numStr = st.nextToken();
        numStr = st.nextToken();
        return numStr.charAt(numStr.length() - 1) == '9';  // test on fold 9
    }


    void train() throws IOException {
        int numTrainingCases = 0;
        int numTrainingChars = 0;
        System.out.println("\nTraining.");
        for (int i = 0; i < mCategories.length; ++i) {
            String category = mCategories[i];
            Classification classification
                = new Classification(category);
            File file = new File(mPolarityDir,mCategories[i]);
            File[] trainFiles = file.listFiles();
            for (int j = 0; j < trainFiles.length; ++j) {
                File trainFile = trainFiles[j];
                if (isTrainingFile(trainFile)) {
                    System.out.println("Training on " + trainFiles[j]);
                    ++numTrainingCases;
                    ++cases[i];
                    String review = Files.readFromFile(trainFile,"ISO-8859-1");
                    numTrainingChars += review.length();
                    Classified<CharSequence> classified
                        = new Classified<CharSequence>(review,classification);
                    mClassifier.handle(classified);
                }
            }
        }
        System.out.println("  # Training Cases=" + numTrainingCases);
        System.out.println("  # Training Chars=" + numTrainingChars);
    }

    void evaluate() throws IOException {
        System.out.println("\nEvaluating.");
        int numTests = 0;
        int numCorrect = 0;
        for (int i = 0; i < mCategories.length; ++i) {
            String category = mCategories[i];
            File file = new File(mPolarityDir,mCategories[i]);
            File[] trainFiles = file.listFiles();
            for (int j = 0; j < trainFiles.length; ++j) {
                File trainFile = trainFiles[j];
                if (!isTrainingFile(trainFile)) {
                    String review = Files.readFromFile(trainFile,"ISO-8859-1");
                    ++numTests;
                    Classification classification
                        = mClassifier.classify(review);
                    if (classification.bestCategory().equals(category)) {

                        ++numCorrect;
                        ++correctCases[i];
		    }
                }
            }
        }
        System.out.println("  # Test Cases=" + numTests);
        System.out.println("  # Correct=" + numCorrect);
        System.out.println("  % Correct=" 
                           + ((double)numCorrect)/(double)numTests);

        for (int i = 0; i < mCategories.length; ++i) {

          System.out.println(mCategories[i] + " " + cases[i] + " " + correctCases[i] + " " + correctCases[i] / cases[i]);
        }
    }

    public static void main(String[] args) {
        try {
            new PolarityBasic(args).run();
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }
    }

}


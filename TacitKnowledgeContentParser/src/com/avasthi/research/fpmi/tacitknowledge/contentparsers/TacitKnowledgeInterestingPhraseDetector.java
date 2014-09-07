/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.ScoredObject;
import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import com.aliasi.util.Files;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeInterestingPhraseDetector {

    TokenizedLM model;
    private static int NGRAM = 20;
    private static int MIN_COUNT = 5;
    private static int MAX_NGRAM_REPORTING_LENGTH = 2;
    private static int NGRAM_REPORTING_LENGTH = 2;
    private static int MAX_COUNT = 500;

    public TacitKnowledgeInterestingPhraseDetector() {
        TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
        model = new TokenizedLM(tokenizerFactory, NGRAM);
    }
    /**
     * @param args the command line arguments
     */

    public void report() throws IOException {

        SortedSet<ScoredObject<String[]>> coll 
            = model.collocationSet(NGRAM_REPORTING_LENGTH,
                                   MIN_COUNT,
                                   MAX_COUNT);

        model.sequenceCounter().prune(3);
        report(coll);
    } 

    public void incrementalTrain(String text) 
        throws IOException {

        model.handle(text);
    }

    private void report(SortedSet<ScoredObject<String[]>> nGrams) {
        for (ScoredObject<String[]> nGram : nGrams) {
            double score = nGram.score();
            String[] toks = nGram.getObject();
            report_filter(score,toks);
        }
    }
    
    private void report_filter(double score, String[] toks) {
        String accum = "";
        for (int j=0; j<toks.length; ++j) {
            //if (nonCapWord(toks[j])) return;
            accum += " "+toks[j];
        }
        System.out.println("Score: "+score+" with :"+accum);
    }

    private boolean nonCapWord(String tok) {
        if (!Character.isUpperCase(tok.charAt(0)))
            return true;
        for (int i = 1; i < tok.length(); ++i) 
            if (!Character.isLowerCase(tok.charAt(i))) 
                return true;
        return false;
    }

}

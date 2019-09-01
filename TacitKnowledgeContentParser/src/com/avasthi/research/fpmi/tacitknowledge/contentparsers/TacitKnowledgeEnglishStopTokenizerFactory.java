/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import com.aliasi.tokenizer.StopTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import java.io.ObjectInput;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author vavasthi
 */
public class TacitKnowledgeEnglishStopTokenizerFactory
        extends StopTokenizerFactory
        implements Serializable {

    static final long serialVersionUID = 4616272325206021322L;

    public TacitKnowledgeEnglishStopTokenizerFactory(TokenizerFactory factory) {
        super(factory, STOP_SET);
    }

    /**
     * The set of stop words, all lowercased.
     */
    static final Set<String> STOP_SET = new HashSet<String>();

    static {
        STOP_SET.add("a");
        STOP_SET.add("b");
        STOP_SET.add("c");
        STOP_SET.add("d");
        STOP_SET.add("e");
        STOP_SET.add("f");
        STOP_SET.add("g");
        STOP_SET.add("h");
        STOP_SET.add("i");
        STOP_SET.add("j");
        STOP_SET.add("k");
        STOP_SET.add("l");
        STOP_SET.add("m");
        STOP_SET.add("n");
        STOP_SET.add("o");
        STOP_SET.add("p");
        STOP_SET.add("q");
        STOP_SET.add("r");
        STOP_SET.add("s");
        STOP_SET.add("t");
        STOP_SET.add("u");
        STOP_SET.add("v");
        STOP_SET.add("w");
        STOP_SET.add("x");
        STOP_SET.add("y");
        STOP_SET.add("z");
        STOP_SET.add("0");
        STOP_SET.add("1");
        STOP_SET.add("2");
        STOP_SET.add("3");
        STOP_SET.add("4");
        STOP_SET.add("5");
        STOP_SET.add("6");
        STOP_SET.add("7");
        STOP_SET.add("8");
        STOP_SET.add("9");
        STOP_SET.add("you");
        STOP_SET.add("your");
        STOP_SET.add("what");
        STOP_SET.add("when");
        STOP_SET.add("how");
        STOP_SET.add("where");
        STOP_SET.add("what");
        STOP_SET.add("which");
        STOP_SET.add("who");
        STOP_SET.add("be");
        STOP_SET.add("had");
        STOP_SET.add("it");
        STOP_SET.add("only");
        STOP_SET.add("she");
        STOP_SET.add("was");
        STOP_SET.add("about");
        STOP_SET.add("because");
        STOP_SET.add("has");
        STOP_SET.add("its");
        STOP_SET.add("of");
        STOP_SET.add("some");
        STOP_SET.add("we");
        STOP_SET.add("after");
        STOP_SET.add("been");
        STOP_SET.add("have");
        STOP_SET.add("last");
        STOP_SET.add("on");
        STOP_SET.add("such");
        STOP_SET.add("were");
        STOP_SET.add("all");
        STOP_SET.add("but");
        STOP_SET.add("he");
        STOP_SET.add("more");
        STOP_SET.add("one");
        STOP_SET.add("than");
        STOP_SET.add("when");
        STOP_SET.add("also");
        STOP_SET.add("by");
        STOP_SET.add("her");
        STOP_SET.add("most");
        STOP_SET.add("or");
        STOP_SET.add("that");
        STOP_SET.add("which");
        STOP_SET.add("an");
        STOP_SET.add("can");
        STOP_SET.add("his");
        STOP_SET.add("mr");
        STOP_SET.add("other");
        STOP_SET.add("the");
        STOP_SET.add("who");
        STOP_SET.add("any");
        STOP_SET.add("co");
        STOP_SET.add("if");
        STOP_SET.add("mrs");
        STOP_SET.add("out");
        STOP_SET.add("their");
        STOP_SET.add("will");
        STOP_SET.add("and");
        STOP_SET.add("corp");
        STOP_SET.add("in");
        STOP_SET.add("ms");
        STOP_SET.add("over");
        STOP_SET.add("there");
        STOP_SET.add("with");
        STOP_SET.add("are");
        STOP_SET.add("could");
        STOP_SET.add("inc");
        STOP_SET.add("mz");
        STOP_SET.add("s");
        STOP_SET.add("they");
        STOP_SET.add("would");
        STOP_SET.add("as");
        STOP_SET.add("for");
        STOP_SET.add("into");
        STOP_SET.add("no");
        STOP_SET.add("so");
        STOP_SET.add("this");
        STOP_SET.add("up");
        STOP_SET.add("at");
        STOP_SET.add("from");
        STOP_SET.add("is");
        STOP_SET.add("not");
        STOP_SET.add("says");
        STOP_SET.add("to");
        STOP_SET.add("my");
        STOP_SET.add("com");
        STOP_SET.add("net");
        STOP_SET.add("edu");
    }

}

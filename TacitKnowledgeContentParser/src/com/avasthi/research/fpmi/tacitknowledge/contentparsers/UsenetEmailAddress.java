/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avasthi.research.fpmi.tacitknowledge.contentparsers;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author vavasthi
 */
public class UsenetEmailAddress {

    @Override
    public String toString() {
        return "UsenetEmailAddress{" + "name_=" + name_ + ", address_=" + address_ + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.address_);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsenetEmailAddress other = (UsenetEmailAddress) obj;
        if (!Objects.equals(this.address_, other.address_)) {
            return false;
        }
        return true;
    }

    private String name_;
    private String address_;

    public UsenetEmailAddress(String str) {
        // There are multiple format of email address. We will try to parse 
        // these one by one. The first one is with <>
        {

            String ltGtString = new String("(.*) *<(.*)>.*");
            Pattern ltGtPattern = Pattern.compile(ltGtString);
            Matcher ltGtMatcher
                    = ltGtPattern.matcher(str);
            if (ltGtMatcher.find()) {
                name_ = str.replaceAll("(.*) *<(.*)>.*", "$1").trim();
                address_ = str.replaceAll("(.*) *<(.*)>.*", "$2").trim();
                return;
            }
        }
        // If the code did not return in above block. It means it could be of 
        // the format "email (name)". We need to parse this.
        {
            String bracketString = new String("(.*) *\\((.*)\\)");
            Pattern bracketPattern = Pattern.compile(bracketString);
            Matcher bracketMatcher = bracketPattern.matcher(str);
            if (bracketMatcher.find()) {
                name_ = str.replaceAll(bracketString, "$2").trim();
                address_ = str.replaceAll(bracketString, "$1").trim();
            }
        }
    }

    public String getName() {
        return name_;
    }

    public String getAddress() {
        return address_;
    }
}

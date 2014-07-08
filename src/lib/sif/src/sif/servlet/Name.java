/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package sif.servlet;

import java.io.Serializable;

/** A Name is a unique name that can be embedded into a web
 * page as ASCII characters. It is used instead of
 * String because strings have weird behavior because
 * they are made of chars.
 */
public class Name implements Serializable {
    byte[] bytes;
    int hash_code;
    /** Construct a Name from the raw byte data. */
    Name(byte[] c) {
        bytes = c;
        hash_code = new String(bytes).hashCode();
    }
    /** Return the int value of a hex char. */
    private static int dehex(char c) {
        if (c >= '0' && c <= '9') return (int)c - (int)'0'; 
        else if (c >= 'A' && c <= 'F') return ((int)c - (int)'A') + 10;
        else if (c >= 'a' && c <= 'f') return ((int)c - (int)'a') + 10;
        else throw new IllegalArgumentException("Invalid hexadecimal character: " + c);
    }
    /** Decode a Name from a string of hexadecimal characters. The string
     * must have even length.
     * @param s
     */
    public Name(String s) {
        if (s == null || s.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid name encoding: " + s);
        }
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < s.length()-1; i = i + 2) {
            char hc = s.charAt(i);
            char lc = s.charAt(i+1);
            bytes[i/2] = (byte)(dehex(hc)*16 + dehex(lc));	
        }
        hash_code = new String(bytes).hashCode();
    }
    public int hashCode() {
        return hash_code;
    }
    public boolean equals(Object o) {
        if (o instanceof Name) {
            Name n = (Name)o;
            if (bytes.length != n.bytes.length) return false;
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != n.bytes[i]) return false;
            }
            return true;				
        } else {
            return false;
        }
    }
    public String toHex() {
        String r = "";
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            int h = (b & 0xF0) >> 4;
            int l = (b & 0x0F);
            char hc, lc;
            if (h < 10) hc = (char)(h + (int)'0');
            else hc = (char)(h - 10 + (int)'a');
            if (l < 10) lc = (char)(l + (int)'0');
            else lc = (char)(l - 10 + (int)'a');
            r = r + hc + lc;		
        }
        return r;		
    }
}

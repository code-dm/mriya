package com.codingdm.mriya.antlr;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wudongming1
 * @email dongming1.wu@genscript.com
 * @Date 5/27/2020 1:45 PM
 **/
@Slf4j
public class DdlParserUtils {

    public static String nameWithoutQuotes(String name){
        return withoutQuotes(name.substring(name.indexOf(".") + 1));
    }

    public static String withoutQuotes(String possiblyQuoted) {
        if (possiblyQuoted.length() < 2) {
            // Too short to be quoted ...
            return possiblyQuoted;
        }
        if (possiblyQuoted.startsWith("`") && possiblyQuoted.endsWith("`")) {
            return possiblyQuoted.substring(1, possiblyQuoted.length() - 1);
        }
        if (possiblyQuoted.startsWith("'") && possiblyQuoted.endsWith("'")) {
            return possiblyQuoted.substring(1, possiblyQuoted.length() - 1);
        }
        if (possiblyQuoted.startsWith("\"") && possiblyQuoted.endsWith("\"")) {
            return possiblyQuoted.substring(1, possiblyQuoted.length() - 1);
        }
        return possiblyQuoted;
    }
}

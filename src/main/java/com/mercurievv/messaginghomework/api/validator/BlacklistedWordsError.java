package com.mercurievv.messaginghomework.api.validator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/31/2017
 * Time: 7:04 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
public class BlacklistedWordsError {
    public final String message = "Blacklisted messages found";
    public final List<String> blacklistedWords;

    public BlacklistedWordsError(List<String> blacklistedWords) {
//            super("Blacklisted messages found: " + blacklistedWords.stream().collect(Collectors.joining(", ", "(", ")")));
        this.blacklistedWords = blacklistedWords;
    }

}

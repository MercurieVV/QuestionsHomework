package com.mercurievv.messaginghomework.api.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/31/2017
 * Time: 5:39 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Service
public class QuestionBlacklistValidator {
    private final Set<String> blacklist;

    public QuestionBlacklistValidator(@Value("${app.blacklist}") String blacklistWordsList) {
        blacklist = splitToWords(blacklistWordsList);
    }

    public HashSet<String> blacklistedWords(String message) {
        HashSet<String> wordsSet = splitToWords(message);
        wordsSet.retainAll(blacklist);
        return wordsSet;
    }

    private HashSet<String> splitToWords(String message) {
        String[] words = message
                .replace('.', ' ')
                .replace(',', ' ')
                .replaceAll("\\s+", " ")
                .split("\\s");
        return new HashSet<>(Arrays.asList(words));
    }
}

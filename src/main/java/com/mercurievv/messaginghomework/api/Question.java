package com.mercurievv.messaginghomework.api;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/29/2017
 * Time: 12:42 AM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Question {
    public String id;
    public String user;
    public String countryCode;
    public String message;
}

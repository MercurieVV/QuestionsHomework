package com.mercurievv.messaginghomework.api;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/23/2017
 * Time: 12:11 AM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Controller
public class MessagesController {
    @AllArgsConstructor
    @NoArgsConstructor(force = true)
    public static class Message {
        public String id;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/messages")
    @ResponseBody
    public List<Message> getMessages(){
        return Arrays.asList(new Message("iddd"));
    }
}

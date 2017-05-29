package com.mercurievv.messaginghomework.api;

import com.mercurievv.messaginghomework.db.DbHelper;
import com.mercurievv.messaginghomework.db.Questions;
import com.mercurievv.messaginghomework.external.FreeGeoIpApi;
import com.mercurievv.messaginghomework.web.CountryCodeResolver;
import com.querydsl.core.QueryResults;
import com.querydsl.sql.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.mercurievv.messaginghomework.db.QQuestions.*;

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 5/23/2017
 * Time: 12:11 AM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
@Controller
public class QuestionsController {
    private final DbHelper dbHelper;
    private final CountryCodeResolver countryCodeResolver;

    @Autowired
    public QuestionsController(DbHelper dbHelper, CountryCodeResolver countryCodeResolver) {
        this.dbHelper = dbHelper;
        this.countryCodeResolver = countryCodeResolver;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/questions")
    @ResponseBody
    public List<Question> getQuestion(@RequestParam(name = "countryCode", required = false) String countryCode){
        SQLQuery<Questions> query = dbHelper.getQueryFactory()
                .selectFrom(questions);
        if(countryCode != null)
            query = query.where(questions.countryCode.eq(countryCode));
        QueryResults<Questions> results = query
                .fetchResults();
        return mapDbToWebResponse(results);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/questions")
    @ResponseBody
    public Question postQuestion(@RequestBody Question question, HttpServletRequest request){
        String countryCode = countryCodeResolver.getCountryCodeByIpCached(request.getRemoteAddr());
        dbHelper.getQueryFactory()
                .insert(questions)
                .columns(questions.userName, questions.countryCode, questions.text)
                .values(question.user, countryCode, question.message)
                .execute();
        return question;
    }

    private List<Question> mapDbToWebResponse(QueryResults<Questions> results) {
        return results.getResults()
                .stream()
                .map(qQuestions -> new Question(
                        String.valueOf(qQuestions.getId())
                        , qQuestions.getUserName()
                        , qQuestions.getCountryCode()
                        , qQuestions.getText()
                ))
                .collect(Collectors.toList());
    }
}

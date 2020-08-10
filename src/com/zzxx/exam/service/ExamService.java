package com.zzxx.exam.service;

import com.zzxx.exam.Controller.ClientContext;
import com.zzxx.exam.entity.*;


import javax.print.DocFlavor;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExamService {
    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    private EntityContext entityContext;

    private ExamInfo examInfo;

    public User login(String id,String pwd) throws IdOrPwdException {
        User user = entityContext.getUserById(Integer.parseInt(id));
        if(user != null){
            if (user.getPassword().equals(pwd)){
                return user;
            }
        }
        throw new IdOrPwdException("账号/密码错误!");
    }

    public List<String> rule(){
        List<String> list = entityContext.getRule();
        return list;
    }
    //开始考试,初始化考试信息
    public ExamInfo start(User user){
        examInfo = entityContext.getExamInfo();
        examInfo.setUser(user);
        return examInfo;
    }

    public QuestionInfo showFirstQuestion(){
        return paper.get(0);
    }

    private ClientContext context;

    private int index = 0;
    public QuestionInfo next(){
        if(index == paper.size() - 1) return paper.get(paper.size() - 1);
        return paper.get(++index);
    }

    public QuestionInfo pre(){
        if(index == 0) return paper.get(0);
        return paper.get(--index);
    }

    //随机生成试卷
    private List<QuestionInfo> paper;
    public void getPaper(){
        int count = 1;
        paper = new ArrayList<>();
        for(int i = Question.LEVEL1; i <= Question.LEVEL10; i++){
            int mark = 0;
            for(int k = 0; k < 2; k++){
                int ran = (int)(Math.random() * entityContext.getQuestionByLevel(i).size());
                if(ran != mark){
                    paper.add(new QuestionInfo(count++,entityContext.getQuestionByLevel(i).get(ran)));
                }else{
                    k--;
                }
                mark = ran;
            }
        }
    }

    //返回当前试题信息
    public QuestionInfo currentQuestionInfo(){
        System.out.println(index);
        System.out.println(paper.get(index).getUserAnswers());
        System.out.println(paper.get(index).getQuestion().getAnswers());
        return paper.get(index);
    }

    //返回成绩
    public int score(){
        int score = 0;
        for(QuestionInfo q:paper){
            if(compareList(q.getUserAnswers(),q.getQuestion().getAnswers())){
                score += 5;
            }
        }
        return score;
    }

    public boolean compareList(List<Integer> list1, List<Integer> list2){
        if(list1.size() != list2.size()) return false;
        Collections.sort(list1);
        Collections.sort(list2);
        for(int i = 0; i < list1.size(); i++){
            if(!list1.get(i).equals(list2.get(i))){
                return false;
            }
        }
        return true;
    }

}

package com.zzxx.exam.Controller;

import com.zzxx.exam.entity.ExamInfo;
import com.zzxx.exam.entity.User;
import com.zzxx.exam.service.ExamService;
import com.zzxx.exam.service.IdOrPwdException;
import com.zzxx.exam.ui.*;

import java.util.List;

public class ClientContext {
    private LoginFrame loginFrame;
    private MenuFrame menuFrame;
    private WelcomeWindow welcomeWindow;
    private MsgFrame msgFrame;
    private ExamFrame examFrame;
    private ExamInfo examInfo;
    private User  user;

    public void login(){
        String id = loginFrame.getIdField().getText();
        String pwd = loginFrame.getPwdField().getText();
        try {
            user = service.login(id,pwd);
            menuFrame.changeInfo(user.getName());
            loginFrame.setVisible(false);
            menuFrame.setVisible(true);
        } catch (IdOrPwdException e) {
            loginFrame.UpdateMessage(e.getMessage());
        }
    }

    public void start(){
        service.getPaper();
        examInfo = service.start(user);
        examFrame.changeExamInfo(examInfo);
        examFrame.changeQuestion(service.showFirstQuestion());
        t.start();
        menuFrame.setVisible(false);
        examFrame.setVisible(true);
    }


   public void setOp(Integer i){
        if(!service.currentQuestionInfo().getUserAnswers().contains(i))
        {
            service.currentQuestionInfo().getUserAnswers().add(i);
        }
    }

   public void removeOp(Integer i){
       System.out.println(service.currentQuestionInfo().toString());
       service.currentQuestionInfo().getUserAnswers().remove(i);
   }

    public void next(){
        examFrame.changeQuestion(service.next());
        examFrame.setOp(service.currentQuestionInfo().getUserAnswers());
    }
    public void pre(){
        examFrame.changeQuestion(service.pre());
        examFrame.setOp(service.currentQuestionInfo().getUserAnswers());

    }

    public void rule(){
        List<String> list =  service.rule();
        msgFrame.clear();
        for(String s:list){
            msgFrame.showMsg(s);
        }
        menuFrame.setVisible(false);
        msgFrame.setVisible(true);
    }

    public void back(){
        msgFrame.setVisible(false);
        menuFrame.setVisible(true);
    }

    public void exit(){
        loginFrame.clearId();
        loginFrame.clearPwd();
        menuFrame.setVisible(false);
        loginFrame.setVisible(true);
    }

    public void getScore(){
        int score = service.score();
        msgFrame.clear();
        msgFrame.showMsg("考试成绩: " + score);
        examFrame.setVisible(false);
        msgFrame.setVisible(true);
    }

    //倒计时线程

        Thread t = new Thread(()-> {
                long time = System.currentTimeMillis() + examInfo.getTimeLimit()*60*1000;
                long now = 0;
                while(now >= 0){
                    now = time - System.currentTimeMillis();
                    long h = now / 1000 / 60 / 60;
                    long m = now / 1000 / 60 % 60;
                    long s = now / 1000 % 60 % 60;
                    examFrame.updateTime(h,m,s);
                }
            }
        );



    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void setMenuFrame(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }

    public void setMsgFrame(MsgFrame msgFrame) {
        this.msgFrame = msgFrame;
    }

    public void setExamFrame(ExamFrame examFrame) {
        this.examFrame = examFrame;
    }

    public void startShow(){
        loginFrame.setVisible(true);
    }

    public void setService(ExamService service) {
        this.service = service;
    }

    private ExamService service = new ExamService();

}

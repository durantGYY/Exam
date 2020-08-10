package com.zzxx.exam;

import com.zzxx.exam.Controller.ClientContext;
import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.service.ExamService;
import com.zzxx.exam.ui.*;

public class Main {
    public static void main(String[] args) {
        ClientContext context = new ClientContext();
        ExamService service = new ExamService();
        LoginFrame loginFrame = new LoginFrame();
        ExamFrame examFrame = new ExamFrame();
        EntityContext entityContext = new EntityContext();
        MsgFrame msgFrame = new MsgFrame();
        MenuFrame menuFrame = new MenuFrame();

        //注入依赖
        context.setService(service);
        service.setEntityContext(entityContext);
        context.setLoginFrame(loginFrame);
        loginFrame.setContext(context);
        context.setExamFrame(examFrame);
        examFrame.setContext(context);
        context.setMenuFrame(menuFrame);
        menuFrame.setContext(context);
        context.setMsgFrame(msgFrame);
        msgFrame.setContext(context);
        context.setWelcomeWindow(new WelcomeWindow());

        context.startShow();
    }
}

package com.zzxx.exam.test;

import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.QuestionInfo;
import com.zzxx.exam.ui.MsgFrame;
import org.junit.Test;

import java.util.Map;

public class EntityTest {
    @Test
    public void test01(){

    }

    @Test
    public void test02(){
        EntityContext enc = new EntityContext();

    }

    @Test
    public void test03(){
        QuestionInfo questionInfo = new QuestionInfo();
        System.out.println(questionInfo.getQuestion());
    }
}

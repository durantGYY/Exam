package com.zzxx.exam.entity;

import java.io.*;
import java.util.*;

/**
 * 实体数据管理, 用来读取数据文件放到内存集合当中
 */
public class EntityContext {
    private Map<Integer,User> users = new HashMap<>();
    private Map<Integer, List<Question>> questions = new HashMap<>();
    private ExamInfo examInfo = new ExamInfo();

    public List<String> getRule() {
        return rule;
    }

    private List<String> rule = new ArrayList<>();

    public EntityContext() {
        loadUsers("src/com/zzxx/exam/util/user.txt");
        loadQuestions("src/com/zzxx/exam/util/corejava.txt");
        loadRule("src/com/zzxx/exam/util/rule.txt");
        loadExamInfo("src/com/zzxx/exam/util/config.properties");
    }

    public void loadExamInfo(String filename){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            Properties p = new Properties();
            p.load(br);
            examInfo.setQuestionCount(Integer.parseInt(p.getProperty("QuestionNumber")));
            examInfo.setTimeLimit(Integer.parseInt(p.getProperty("TimeLimit")));
            examInfo.setTitle(p.getProperty("PaperTitle"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExamInfo getExamInfo() {
        return examInfo;
    }

    public void loadRule(String filename){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line;
            while ((line = br.readLine()) != null){
                rule.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers(String filename){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String user;
            while ((user = br.readLine()) != null){
                if(user.startsWith("1")){
                    String[] s = user.split(":");
                    User u = new User(s[1],Integer.parseInt(s[0]),s[2]);
                    u.setEmail(s[4]);
                    u.setPhone(s[3]);
                    users.put(u.getId(),u);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadQuestions(String filename){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String s;
            int count = 1;
            while ((s = br.readLine()) != null){
                Question q = new Question();
                q.setId(count++);
                String[] ss = s.split(",");
                String[] sss = ss[0].split("=");
                String[] ssss = sss[1].split("/");
                List<Integer> list = new ArrayList<>();
                if(ssss.length > 1)
                {
                    q.setType(Question.MULTI_SELECTION);
                }else {
                    q.setType(Question.SINGLE_SELECTION);
                }
                for(String i:ssss){
                    list.add(Integer.parseInt(i));
                }
                q.setAnswers(list);
                q.setScore(Integer.parseInt(ss[1].split("=")[1]));
                q.setLevel(Integer.parseInt(ss[2].split("=")[1]));
                String s1 = br.readLine();
                q.setTitle(s1);
                List<String> op = new ArrayList<>();
                for(int i = 0; i < 4; i++){
                    op.add(br.readLine());
                }
                q.setOptions(op);
                List<Question> listQuestion = questions.get(q.getLevel());
                if(listQuestion == null){
                    listQuestion = new ArrayList<>();
                }
                listQuestion.add(q);
                questions.put(q.getLevel(),listQuestion);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(Integer i) {
        return users.get(i);
    }

    public Map<Integer, List<Question>> getQuestions() {
        return questions;
    }

    public List<Question> getQuestionByLevel(Integer i){
        return questions.get(i);
    }

}

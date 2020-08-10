package com.zzxx.exam.ui;

import com.zzxx.exam.Controller.ClientContext;
import com.zzxx.exam.entity.ExamInfo;
import com.zzxx.exam.entity.QuestionInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ExamFrame extends JFrame {
    // 选项集合, 方便答案读取的处理
    private Option[] options = new Option[4];

    public ExamFrame() {
        init();
    }

    private void init() {
        setTitle("指针信息在线测评");
        setSize(600, 380);
        setContentPane(createContentPane());
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {// 正在关闭的时候
                System.exit(0);
            }
        });
    }

    private JPanel createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(6, 6, 6, 6));
        ImageIcon icon = new ImageIcon(getClass().getResource("pic/exam_title.png"));

        pane.add(BorderLayout.NORTH, new JLabel(icon));

        pane.add(BorderLayout.CENTER, createCenterPane());

        pane.add(BorderLayout.SOUTH, createToolsPane());

        return pane;
    }
    private JLabel examInfo;

    public void changeExamInfo(ExamInfo examInfo) {
        this.examInfo.setText(examInfo.toString());
    }


    private JPanel createCenterPane() {
        JPanel pane = new JPanel(new BorderLayout());
        // 注意!
        examInfo = new JLabel("姓名:XXX 考试:XXX 考试时间:XXX", JLabel.CENTER);
        pane.add(BorderLayout.NORTH, examInfo);

        pane.add(BorderLayout.CENTER, createQuestionPane());
        pane.add(BorderLayout.SOUTH, createOptionsPane());
        return pane;
    }
    //设置每道题的答案勾选
    public void setOp(List<Integer> list){
        for(int i = 0; i < options.length; i++){
            options[i].setSelected(false);
        }
        for(int i = 0; i < list.size(); i++){
            options[list.get(i)].setSelected(true);
        }
    }



    private JPanel createOptionsPane() {
        JPanel pane = new JPanel();
        Option a = new Option(0, "A");
        Option b = new Option(1, "B");
        Option c = new Option(2, "C");
        Option d = new Option(3, "D");
        options[0] = a;
        options[1] = b;
        options[2] = c;
        options[3] = d;
        pane.add(a);
        pane.add(b);
        pane.add(c);
        pane.add(d);
        options[0].addActionListener( e ->{
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if(checkBox.isSelected()){
                context.setOp(0);
            }else if(!checkBox.isSelected()){
                context.removeOp(0);
            }
        });
        options[1].addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if(checkBox.isSelected()){
                context.setOp(1);
            }else if(!checkBox.isSelected()){
                context.removeOp(1);
            }
        });
        options[2].addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if(checkBox.isSelected()){
                context.setOp(2);
            }else if(!checkBox.isSelected()){
                context.removeOp(2);
            }
        });
        options[3].addActionListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if(checkBox.isSelected()){
                context.setOp(3);
            }else if(!checkBox.isSelected()){
                context.removeOp(3);
            }
        });
        return pane;
    }



    //改变题目
    public void changeQuestion(QuestionInfo questionInfo){
        questionArea.setText("");
        questionArea.setText(questionInfo.getQuestionIndex()+ "." + questionInfo.getQuestion().toString());
        questionCount.setText("题目:20 的第 " + questionInfo.getQuestionIndex() + " 题");
    }

    private JScrollPane createQuestionPane() {
        JScrollPane pane = new JScrollPane();
        pane.setBorder(new TitledBorder("题目"));// 标题框

        // 注意!
        questionArea = new JTextArea();
        questionArea.setText("问题\nA.\nB.");
        questionArea.setLineWrap(true);// 允许折行显示
        questionArea.setEditable(false);// 不能够编辑内容
        // JTextArea 必须放到 JScrollPane 的视图区域中(Viewport)
        pane.getViewport().add(questionArea);
        return pane;
    }

    private JPanel createToolsPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(0, 10, 0, 10));
        // 注意!
        questionCount = new JLabel("题目:20 的 1题");

        timer = new JLabel("剩余时间:222秒");

        pane.add(BorderLayout.WEST, questionCount);
        pane.add(BorderLayout.EAST, timer);
        pane.add(BorderLayout.CENTER, createBtnPane());
        return pane;
    }



    public void setContext(ClientContext context) {
        this.context = context;
    }

    private ClientContext context;
    private JPanel createBtnPane() {
        JPanel pane = new JPanel(new FlowLayout());
        prev = new JButton("上一题");
        next = new JButton("下一题");
        JButton send = new JButton("交卷");

        pane.add(prev);
        pane.add(next);
        pane.add(send);

        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                context.pre();
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                context.next();
            }
        });

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                context.getScore();
            }
        });

        return pane;
    }

    /**
     * 使用内部类扩展了 JCheckBox 增加了val 属性, 代表答案值
     */
    class Option extends JCheckBox {
        int value;

        public Option(int val, String txt) {
            super(txt);
            this.value = val;
        }
    }

    private JTextArea questionArea;

    private JButton next;

    private JButton prev;

    private JLabel questionCount;

    private JLabel timer;

    public void updateTime(long h, long m, long s) {
        String time ="剩余时间: " +  h + ":" + m + ":" + s;
        if (m < 5) {
            timer.setForeground(new Color(0xC85848));
        } else {
            timer.setForeground(Color.blue);
        }
        timer.setText(time);
    }
}
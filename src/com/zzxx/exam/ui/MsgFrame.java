package com.zzxx.exam.ui;

import com.zzxx.exam.Controller.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主菜单界面
 */
public class MsgFrame extends JFrame {
    private JTextArea ta;
    private ClientContext context;

    public void setContext(ClientContext context) {
        this.context = context;
    }

    public MsgFrame() {
        init();
    }

    private void init() {
        setSize(600, 400);
        setContentPane(createContentPane());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                context.back();
            }
        });
    }

    private JScrollPane createContentPane() {
        JScrollPane jsp = new JScrollPane();
        ta = new JTextArea();
        jsp.add(ta);
        jsp.getViewport().add(ta);
        return jsp;
    }

    public void showMsg(String file) {
        ta.append(file);
        ta.append("\n");
        ta.setLineWrap(true);// 允许折行显示
        ta.setEditable(false);// 不能够编辑内容
    }

    public void clear(){
        ta.setText("");
    }

    private JPanel createBackButton(){
        JPanel pen = new JPanel(new BorderLayout());
        JButton button = new JButton("back",new ImageIcon(this.getClass().getResource("pic/exam.png")));
        // button.setIcon(ico);
        // 设置文本相对于图标的垂直位置
        button.setVerticalTextPosition(JButton.BOTTOM);
        // 设置文本相对于图标的水平位置
        button.setHorizontalTextPosition(JButton.CENTER);
        pen.add(button);
        return pen;
    }


}

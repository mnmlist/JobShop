package show;

import tabusearch.Solution;

import javax.swing.*;

public class ShowFrame{
    public ShowFrame(Solution solution){
        JFrame  jf = new JFrame();
        Gantt gantt = new Gantt(solution,8,11);
        jf.setBounds(200, 200, 1000, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(gantt);
        jf.setVisible(true);
    }
}
class Gantt extends JPanel{
    private int starttime=8;
    private int endtime=11;
    public Gantt(Solution solution,int starttime,int endtime){
        this.starttime=starttime;
        this.endtime=endtime;

    }
}

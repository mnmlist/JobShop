package show;

import tabusearch.Job;
import tabusearch.Machine;
import tabusearch.Operation;
import tabusearch.Solution;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class ShowFrame {
    public ShowFrame(Solution solution) {
        JFrame jf = new JFrame();
        Gantt gantt = new Gantt(solution);
        jf.setBounds(200, 200, 1000, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(gantt);
        jf.setVisible(true);
    }
}

class Gantt extends JPanel {
    private int starttime = 8 * 60;
    private int endtime = 11 * 60;
    private int blank;
    private int numOfJZJ;
    private int numOfZW;
    private float hJZJ;
    private float wZW;
    private Solution solution;
    private Point lt;
    private Point lb;
    private Point rt;
    private Point rb;

    public Gantt(Solution solution) {
        this.solution = solution;
        numOfJZJ = solution.getNumberOfJobs();
        numOfZW = Tools.getNumOfZW();
        init();
    }

    private void init() {
        this.starttime = 8 * 60;
        this.endtime = 11 * 60;
        this.blank = 20;
        blank = 20;
        hJZJ = 20f;
        wZW = 1.6f;
        lt = new Point(100, 70);
        lb = new Point(lt.x, (int) (lt.y + numOfJZJ * (hJZJ + 10)));
        rb = new Point((int) (lb.x + (endtime - starttime) * wZW), lb.y);
        rt = new Point(rb.x, lt.y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawAxis(g2d);
        drawItem(g2d);
    }

    public void drawItem(Graphics2D g) {
        float[][] sol = solution.getSol();
        LinkedList<Operation>[] A = solution.getA();
        for (int i = 0; i < sol.length - 1; i++) {
            int maxNumofJob = A[i].size();
            for (int j = 0; j < maxNumofJob; j++) {
                Operation o = A[i].get(j);
                String name = Tools.getZWName(o.getMachine());
                if(j==0&&name.equals("")){
                    continue;
                }
                float time = sol[i][j];
                System.out.print(time+"  ");
                int x = (int) (rt.x-time*wZW);
                int y = (int) (lt.y + tranI(i) * (hJZJ + 10) + hJZJ / 2 + (hJZJ + 10) / 2);
                int width = (int) (o.getDuration()*wZW);
                int heigth = (int) hJZJ;
                if (name.equals("")) {
                    g.setColor(Color.gray);
                    g.fillRect(x-width, y-heigth, width+1 , heigth );
                    g.setColor(Color.BLACK);
                } else {
                    g.drawRect(x-width, y-heigth, width, heigth);
                    int strx = x - width / 2 - getStringWidth(name, g.getFont()) / 2;
                    int stry = y - heigth / 2 + getStringHeight(name, g.getFont()) / 2;
                    g.drawString(name, strx, stry);
                }
            }
            System.out.println();
        }

    }



    private void drawAxis(Graphics2D g) {
        setBackground(Color.white);
        g.setColor(Color.BLACK);
        int strx,stry;
        String strname;

        g.drawLine(lb.x, lb.y, lt.x, lt.y);
        for (int i = 0; i < numOfJZJ; i++) {
            int x = lt.x;
            int y = (int) (lt.y + i * (hJZJ + 10) + (hJZJ + 10) / 2);
            g.drawLine(x, y, x + 5, y);
            String name = "F" + (i + 1);
            g.drawString(name, x - 20, y);
        }
        strname="Aircraft";
        strx=50;
        stry=100;
      //  g.rotate(90,getStringWidth(strname,g.getFont())/2,getStringHeight(strname,g.getFont())/2);
        g.drawString(strname,50,50);
      //  g.rotate(-90,getStringWidth(strname,g.getFont())/2,getStringHeight(strname,g.getFont())/2);

        g.drawLine(lb.x, lb.y, rb.x, rb.y);
        for (int i = 0; i <= endtime - starttime; i += blank) {
            int x = (int) (lb.x + i * wZW);
            int y = lb.y;
            g.drawLine(x, y, x, y - 5);
            String name = getTime(i);
            g.drawString(name, x - 10, y + 15);
        }
        strname="Time";
        strx=lb.x+(rb.x-lb.x)/2-getStringWidth(strname,g.getFont())/2;
        stry=lb.y+30;
        g.drawString(strname,strx,stry);
    }
    private int tranI(int num){
        LinkedList<Operation>[] A=solution.getA();
        int times[][]=new int[A.length][2];
        for(int i=0;i<A.length;i++){
            String mName=Tools.getZWName(A[i].getFirst().getMachine());
            if(mName.equals("")){
                times[i][0]=i;
                times[i][1]=A[i].getFirst().getDuration();
            }
            else {
                times[i][0]=i;
                times[i][1]=0;
            }
        }
        for(int i=0;i<times.length-1;i++){
            for(int j=i+1;j<times.length;j++){

                if(times[i][1]<=times[j][1]){
                    int[] temp=times[i];
                    times[i]=times[j];
                    times[j]=temp;
                }
            }
        }
        int order=0;
        for(int i=0;i<times.length;i++){
            if(times[i][0]==num){
                order=i;
            }
        }
        return order;
    }
    private String getTime(int i) {
        String time = "";
        int hour = (i + starttime) / 60;
        int min = (i + starttime) % 60;
        if (min == 0) {
            time = hour + ":" + min + "0";
        } else {
            time = hour + ":" + min;
        }
        return time;

    }
    private static AffineTransform atf = new AffineTransform();

    private static FontRenderContext frc = new FontRenderContext(atf, true,
            true);

    public static int getStringHeight(String str, Font font) {
        if (str == null || str.isEmpty() || font == null) {
            return 0;
        }
        return (int) font.getStringBounds(str, frc).getWidth();

    }

    public static int getStringWidth(String str, Font font) {
        if (str == null || str.isEmpty() || font == null) {
            return 0;
        }
        return (int) font.getStringBounds(str, frc).getWidth();
    }
}

class Tools {

    public static int getNumOfZW() {
        return 6;
    }

    public static String getZWName(Machine m) {
        String name = "";
        switch (m.getId()) {
            case 0:
                name = "B1";
                break;
            case 1:
                name = "A1";
                break;
            case 2:
                name = "A2";
                break;
            case 3:
                name = "A3";
                break;
            case 4:
                name = "A4";
                break;
            case 5:
                name = "A5";
                break;

        }
        return name;
    }

    public static String getJobName(Job j) {
        String name = "JZJ" + j.getId();
        return name;
    }
}
package tabusearch;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of our project in which we apply tabu search to the Job Shop
 * Scheduling Problem.
 * <p>
 * This class includes methods to generate the results as mentioned in the final
 * paper/presentation.
 *
 * @author Thiebout Dewitte
 * @version 1.0
 */
public class Main {

    /**
     * Main method
     */
    public static void main(String args[]) throws FileNotFoundException {
        //  opendeurdagKulak();
/*		String filepath="./testinstances/orb02.txt";
        left(filepath);
		sbp(filepath);*/
        outFile1();
    }

    public static void outFile() {

        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("test1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("问题");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("job");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("machine");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("最优解");
        cell.setCellStyle(style);

        String tests[] = {
                 "ft06"
                //  ,
                // "la01",
                // "la06",
                //"ft10",
               //  "ft20"
        };
        List<Solver> solvers = new ArrayList<>();
        // solvers.add(new SBPSolver());

        solvers.add(new LeftSolver());
        //  solvers.add(new TS2());

        // solvers.add(new TS());

        for (int i = 0; i < solvers.size(); i++) {
            Solver solver = solvers.get(i);
            cell = row.createCell((short) 4 * i + 4);
            cell.setCellValue(solver.name + "_INIT");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4 * i + 5);
            cell.setCellValue(solver.name + "_OPT");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4 * i + 6);
            cell.setCellValue(solver.name + "_TIME");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4 * i + 7);
            cell.setCellValue(solver.name + "_K");
            cell.setCellStyle(style);
        }
        for (int i = 0; i < tests.length; i++) {
            row = sheet.createRow((int) i + 1);
            Problem p = Parser.parseInstance("./testinstances/" + tests[i] + ".txt");
            row.createCell((short) 0).setCellValue(tests[i]);
            row.createCell((short) 1).setCellValue(p.getNumberOfJobs());
            row.createCell((short) 2).setCellValue(p.getNumberOfMachines());
            row.createCell((short) 3).setCellValue(p.getOptimalCost());
            for (int j = 0; j < solvers.size(); j++) {
                Solver solver = solvers.get(j);
                solver.setP(p);
                solver.excute();
                row.createCell((short) 4 * j + 4).setCellValue(solver.initcost);
                row.createCell((short) 4 * j + 5).setCellValue(solver.lastcost);
                row.createCell((short) 4 * j + 6).setCellValue(solver.time);
                row.createCell((short) 4 * j + 7).setCellValue(solver.K);
                solver.print();
            }
            System.out.println(tests[i] + "  complete");
        }
        String pre = tests[0] + "_" + solvers.get(0).name + "_";
        String outfile = "";
        if (outfile.equals("")) {
            int num = 1;
            outfile = pre + num + ".xls";
            File file = new File("./outtest/" + outfile);
            while (file.exists()) {
                num++;
                outfile = pre + num + ".xls";
                file = new File("./outtest/" + outfile);
            }
        }
        try {
            FileOutputStream fout = new FileOutputStream("./outtest/" + outfile);
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void outFile1() {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("test1");
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("问题");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("job");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("machine");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("最优解");
        cell.setCellStyle(style);

        String tests[] = {
                "jsp5"
        };
        List<Solver> solvers = new ArrayList<>();
         solvers.add(new SBPSolver());

      //  solvers.add(new LeftSolver());

        for (int i = 0; i < solvers.size(); i++) {
            Solver solver = solvers.get(i);
            cell = row.createCell((short) 4 * i + 4);
            cell.setCellValue(solver.name + "_INIT");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4 * i + 5);
            cell.setCellValue(solver.name + "_OPT");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4 * i + 6);
            cell.setCellValue(solver.name + "_TIME");
            cell.setCellStyle(style);
            cell = row.createCell((short) 4 * i + 7);
            cell.setCellValue(solver.name + "_K");
            cell.setCellStyle(style);
        }
        for (int i = 0; i < tests.length; i++) {
            row = sheet.createRow((int) i + 1);
            Problem p = Parser.parseInstance("./jsp/" + tests[i] + ".txt");
            row.createCell((short) 0).setCellValue(tests[i]);
            row.createCell((short) 1).setCellValue(p.getNumberOfJobs());
            row.createCell((short) 2).setCellValue(p.getNumberOfMachines());
            row.createCell((short) 3).setCellValue(p.getOptimalCost());
            for (int j = 0; j < solvers.size(); j++) {
                Solver solver = solvers.get(j);
                solver.setP(p);
                solver.excute();
                row.createCell((short) 4 * j + 4).setCellValue(solver.initcost);
                row.createCell((short) 4 * j + 5).setCellValue(solver.lastcost);
                row.createCell((short) 4 * j + 6).setCellValue(solver.time);
                row.createCell((short) 4 * j + 7).setCellValue(solver.K);
                solver.print();
            }
            System.out.println(tests[i] + "  complete");
        }
        for(int i=0;i<TabuSearch.listK.size();i++){
            System.out.println(TabuSearch.listK.get(i)+" "+TabuSearch.listCost.get(i));
            row = sheet.createRow((int) i + 2);
            row.createCell((short) 0).setCellValue(TabuSearch.listK.get(i));
            row.createCell((short) 1).setCellValue(TabuSearch.listCost.get(i));
        }
        String pre = tests[0] + "_" + solvers.get(0).name + "_";
        String outfile = "";
        if (outfile.equals("")) {
            int num = 1;
            outfile = pre + num + ".xls";
            File file = new File("./outtest/" + outfile);
            while (file.exists()) {
                num++;
                outfile = pre + num + ".xls";
                file = new File("./outtest/" + outfile);
            }
        }
        try {
            FileOutputStream fout = new FileOutputStream("./outtest/" + outfile);
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*	public static void sbp(String filepath){
            Problem p = Parser
                    .parseInstance(filepath);
            long time1=System.currentTimeMillis();
            Solution sbp=SBP.getInitSol(p);
            System.out.println("**********SBP************:\n"+sbp);
            long time2=System.currentTimeMillis();
            System.out.println("________SBP time\n"+(time2-time1)/1000.0f);
            Solution optS = TabuSearch.tabuSearch(sbp);
            System.out.println("**********TABU************\n"+optS);
            long time4=System.currentTimeMillis();
            System.out.println("________TABU time\n"+(time4-time2)/1000.0f);
        }
        public static void left(String filepath){
            Problem p = Parser
                    .parseInstance(filepath);
            long time2=System.currentTimeMillis();
            Solution leftS = TabuSearch.getInitialSolutionOnlyLeft(p);
            System.out.println("**********LEFT************:\n"+leftS);
            long time3=System.currentTimeMillis();
            System.out.println("________LEFT time\n"+(time3-time2)/1000.0f);
            Solution optS = TabuSearch.tabuSearch(leftS);
            System.out.println("**********TABU************\n"+optS);
            long time4=System.currentTimeMillis();
            System.out.println("________TABU time\n"+(time4-time3)/1000.0f);
        }*/
    public static void opendeurdagKulak() {
        String filepath = true ? "./testinstances/ft06.txt" :
                "./jsp/jsp3.txt";
        Problem p2 = Parser
                .parseInstance(filepath);

        Solution init = TabuSearch.getInitialSolutionOnlyLeft(p2);
        init.getCost();
        TabuSearch.its(init, 0);




/*
        Problem p1 = Parser.parseInstance(filepath);

        Solution init1 = TabuSearch.getInitialSolutionOnlyLeft(p2);
        init1.getCost();
        TabuSearch.its(init1, 0);*/

/*        Problem p = Parser
                .parseInstance("D:/Develop/Project/IdeaProject/JobShopTest/testinstances/ft06.txt");
        Solution s = TabuSearch.getInitialSolutionOnlyLeft(p);
        Solution s1 = TabuSearch.getInitialSolutionOnlyLeft(p);
        System.out.println(s);
        System.out.println(s1);*/
    }

    /**
     * Tabu search one Test instance.
     */
    public static void tabuSearchOneTestInstance(String path) {
        Problem p = Parser.parseInstance(path);
        Solution s = TabuSearch.tabuSearch(p);
        System.out.println(s.printSolution());
    }

    /**
     * Generate the content for table 1 in the final report.
     * <p>
     * It takes as input all testinstances in .txt files in a directory. The
     * output will be written to a file, instead of on the console. This file
     * can be put into a table environment in LaTeX.
     */
    public static void makeTable1() throws FileNotFoundException {

        File dir = new File("testinstances");
        File[] directoryListing = dir.listFiles();
        PrintStream out = new PrintStream(new FileOutputStream("table1.txt"));
        System.setOut(out);

        if (directoryListing != null) {
            for (File file : directoryListing) {
                Solution s, s2, s3, s4, s5;
                Problem p = Parser.parseInstance(file.toString());
                Problem p2 = Parser.parseInstance(file.toString());
                Problem p3 = Parser.parseInstance(file.toString());
                Problem p4 = Parser.parseInstance(file.toString());
                Problem p5 = Parser.parseInstance(file.toString());
                try {
                    long startTime = System.nanoTime();
                    s = TabuSearch.getInitialSolution(p);
                    s2 = TabuSearch.getInitialSolution(p2);
                    s3 = TabuSearch.getInitialSolution(p3);
                    s4 = TabuSearch.getInitialSolution(p4);
                    s5 = TabuSearch.getInitialSolution(p5);
                    long elapsedTimeNano = (System.nanoTime() - startTime); // in
                    // nanoseconds
                    double elapsedTime = ((double) elapsedTimeNano) / 1E9;
                    float min = Float.min(s.getCost(), s2.getCost());
                    min = Float.min(min, s3.getCost());
                    min = Float.min(min, s4.getCost());
                    min = Float.min(min, s5.getCost());

                    startTime = System.nanoTime();
                    s = TabuSearch.getInitialSolutionOnlyLeft(p);
                    s2 = TabuSearch.getInitialSolutionOnlyLeft(p2);
                    s3 = TabuSearch.getInitialSolutionOnlyLeft(p3);
                    s4 = TabuSearch.getInitialSolutionOnlyLeft(p4);
                    s5 = TabuSearch.getInitialSolutionOnlyLeft(p5);
                    long elapsedTime2Nano = (System.nanoTime() - startTime); // in
                    // nanoseconds
                    double elapsedTime2 = ((double) elapsedTime2Nano) / 1E9;
                    float min2 = Float.min(s.getCost(), s2.getCost());
                    min2 = Float.min(min2, s3.getCost());
                    min2 = Float.min(min2, s4.getCost());
                    min2 = Float.min(min2, s5.getCost());

                    System.out.println(file.toString().replace(".txt", "")
                            + " & " + p.getNumberOfJobs() + " & "
                            + p.getNumberOfMachines() + " & "
                            + Math.round(p.getOptimalCost()) + " & "
                            + Math.round(min) + " & " + elapsedTime + " & "
                            + Math.round(min2) + " & " + elapsedTime2 + "\\\\");
                } catch (Exception e) {
                    System.out.println("error");

                }
            }
        }
    }

    /**
     * Generate the content for table 2 in the final report.
     * <p>
     * It takes as input all testinstances in .txt files in a directory. The
     * output will be written to a file, instead of on the console. This file
     * can be put into a table environment in LaTeX.
     */
    public static void makeTable2() throws FileNotFoundException {
        File dir = new File("testinstances");
        File[] directoryListing = dir.listFiles();

        PrintStream out = new PrintStream(new FileOutputStream("table2.txt"));
        System.setOut(out);

        if (directoryListing != null) {
            String res = "";
            for (File file : directoryListing) {
                try {
                    float best = Integer.MAX_VALUE;
                    double longestTime = Double.MIN_VALUE;
                    float sumCosts = 0;
                    double sumTimes = 0;

                    Problem p = Parser.parseInstance(file.toString());
                    float optimum = p.getOptimalCost();
                    String name = file.toString().replace(".txt", "");

                    res = name.replace("testinstances/", "") + " & "
                            + p.getNumberOfJobs() + " & "
                            + p.getNumberOfMachines() + " & "
                            + Math.round(p.getOptimalCost());

                    for (int i = 0; i < 5; i++) {
                        long startTime = System.nanoTime();
                        Solution s = TabuSearch.tabuSearch(p);
                        long elapsedTimeNano = (System.nanoTime() - startTime);
                        double elapsedTime = ((double) elapsedTimeNano) / 1E9;
                        float c = s.getCost();

                        if (c < best)
                            best = c;
                        if (elapsedTime > longestTime) {
                            longestTime = elapsedTime;
                        }
                        sumCosts += c;
                        sumTimes += elapsedTime;

                    }

                    res += " & " + Math.round(best);
                    if (optimum != -1) {
                        float delta = best - optimum;
                        res += " & " + delta / optimum * 100; // Delta Z %
                    }

                    res += " & " + sumCosts / 5; // Z_av
                    res += " & " + sumTimes / 5; // T_av
                    res += " & " + longestTime; // T_max
                    res += " \\\\"; // new line in LaTeX table
                } catch (Exception e) {
                } finally {
                    System.out.println(res);
                }
            }
        }
    }

    /**
     * Tabu search the instance used in the final presentation.
     */
    public static void finalPresentationInstance() {
        Problem p = Parser
                .parseInstance("/Users/thieboutdewitte/Documents/Kulak/Bach2/GegevensstructEnAlg/Project/Implementation/JobShopScheduling/TestInstanceDewitte.txt");
        System.out.println(p);
        Solution s = TabuSearch.getInitialSolution(p);
        System.out.println(s);
        Solution optS = TabuSearch.tabuSearch(p);
        System.out.println(optS);
    }

}

abstract class Solver {
    public Solution init;
    public Solution last;
    public float initcost;
    public float lastcost;
    public float time;
    public Problem p;
    public String name;
    public int K;

    public void setP(Problem p) {
        this.p = p;
    }

    public float getInitcost() {
        return init.getCost();
    }

    public float getLastcost() {
        return last.getCost();
    }

    public float getTime() {
        return time;
    }

    public abstract void excute();

    public void print() {

        System.out.println();
        System.out.println(name + "    " + lastcost + "    " + time + "   " + K);
    }
}

class SBPSolver extends Solver {
    public SBPSolver() {
        name = "SBP";
    }

    @Override
    public void excute() {
        long time1 = System.currentTimeMillis();
        init = SBP.getInitSol(p);
        long time2 = System.currentTimeMillis();
        last = TabuSearch.tabuSearch(init);
        long time3 = System.currentTimeMillis();
        initcost = init.getCost();
        lastcost = last.getCost();
        time = (time3 - time2) / 1000.0f;
        K = last.K;
    }
}

class LeftSolver extends Solver {
    public LeftSolver() {
        name = "LEFT";
    }

    @Override
    public void excute() {
        long time1 = System.currentTimeMillis();
        init = TabuSearch.getInitialSolutionOnlyLeft(p);
        long time2 = System.currentTimeMillis();
        last = TabuSearch.tabuSearch(init);
        long time3 = System.currentTimeMillis();
        initcost = init.getCost();
        lastcost = last.getCost();
        time = (time3 - time2) / 1000.0f;
        K = last.K;
    }
}

/*
    动态tabulist
 */
class TS1 extends Solver {
    public TS1() {
        name = "TS2";
    }

    @Override
    public void excute() {
        long time1 = System.currentTimeMillis();
        init = TabuSearch.getInitialSolutionOnlyLeft(p);
        long time2 = System.currentTimeMillis();
        last = TabuSearch.its(init, 1);
        long time3 = System.currentTimeMillis();
        initcost = init.getCost();
        lastcost = last.getCost();
        time = (time3 - time2) / 1000.0f;
        K = last.K;
    }
}

class TS2 extends Solver {
    public TS2() {
        name = "TS2";
    }

    @Override
    public void excute() {
        long time1 = System.currentTimeMillis();
        init = TabuSearch.getInitialSolutionOnlyLeft(p);
        long time2 = System.currentTimeMillis();
        last = TabuSearch.its(init, 2);
        long time3 = System.currentTimeMillis();
        initcost = init.getCost();
        lastcost = last.getCost();
        time = (time3 - time2) / 1000.0f;
        K = last.K;
    }
}

/*
    静态tabulist
 */
class TS0 extends Solver {
    public TS0() {
        name = "TS0";
    }

    @Override
    public void excute() {
        long time1 = System.currentTimeMillis();
        init = TabuSearch.getInitialSolutionOnlyLeft(p);
        long time2 = System.currentTimeMillis();
        last = TabuSearch.its(init, 20);
        long time3 = System.currentTimeMillis();
        initcost = init.getCost();
        lastcost = last.getCost();
        time = (time3 - time2) / 1000.0f;
        K = last.K;
    }
}

class TS extends Solver {
    public TS() {
        name = "TS";
    }

    @Override
    public void excute() {
        long time1 = System.currentTimeMillis();
        init = TabuSearch.getInitialSolutionOnlyLeft(p);
        long time2 = System.currentTimeMillis();
        last = TabuSearch.ts(init);
        long time3 = System.currentTimeMillis();
        initcost = init.getCost();
        lastcost = last.getCost();
        time = (time3 - time2) / 1000.0f;
        K = last.K;
    }
}
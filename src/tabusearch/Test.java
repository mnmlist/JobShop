package tabusearch;

import java.util.*;

public class Test {
    public static void main(String[] args){
        List<Test> list = new LinkedList<>();
        Set<Test> set=new HashSet<>();
        Map<Integer,Test> map=new HashMap<>();
        Map<Test,Integer> map2=new HashMap<>();
        Test t1=new Test(1);
        Test t2=new Test(1);
        Test t3=new Test(1);
        set.add(t1);
        set.add(t2);
        set.add(t3);
        list.add(t1);
        list.add(t1);
        list.add(t1);
        map.put(1,t1);
        map.put(2,t1);
        map.put(3,t1);
        map2.put(t1,1);
        map2.put(t2,2);
        map2.put(t3,3);
        System.out.println();
    }
    private int id;
    public Test(int id){
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Test)obj).getId()==getId();
    }
}

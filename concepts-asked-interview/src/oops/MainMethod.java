package oops;

import java.util.TreeMap;

abstract class Notifiaction{
    void print(){
        System.out.println("Print");
    }
    public abstract void send();
}
class MainMethod {
    public static void main(String[] args) {
        //
        TreeMap<String, String> stringTreeMap = new TreeMap<>();
        stringTreeMap.put("shrikar" ,"dev");
        stringTreeMap.put("dinesh" ,"test");
        System.out.println(stringTreeMap);
    }
}

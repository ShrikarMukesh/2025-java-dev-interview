package collection.arraylist;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
interface SendEmail{
     String sender = "ANZ" ;
}
public class Basic {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> arrayList = new CopyOnWriteArrayList();
        arrayList.add(12);
        arrayList.add(12);
        arrayList.add(12);
        arrayList.add(12);
        Iterator iterator =  arrayList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            arrayList.add(89);
        }
        System.out.println(arrayList);
    }
}

package collection.hashmap2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Account{
    int id;
    String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Account(int id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(number, account.number);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, number);
//    }
}
public class HashCodeVSEqualContact {
    public static void main(String[] args) {
        Account account1 = new Account(6756, "SBI8987");
        Account account2 = new Account(45, "SBI8987");
        Map<String, Account> accountMap = new HashMap<>();
        accountMap.put(account1.getNumber(), account1);
        accountMap.put(account2.getNumber(), account2);
        System.out.println(accountMap);
    }
}

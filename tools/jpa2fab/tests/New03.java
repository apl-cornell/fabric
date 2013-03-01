import java.io.*;
import javax.persistence.Entity;

@Entity
public class New03 extends A {
    public New03(int num) {
        super(num);
    }

    public void foo() {
        New03 t = new New03(42);
    }
}

@Entity
class A {
    int num;
    public A(int num) {
       this.num = num; 
    }
}

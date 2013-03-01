import java.io.*;
import javax.persistence.Entity;

@Entity
public class New02 {
    int num;
    public New02(int num) {
       this.num = num; 
    }

    public void foo() {
        New02 t = new New02(42);
    }
}

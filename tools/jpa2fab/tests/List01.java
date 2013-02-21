import java.io.*;
import java.util.*;
import javax.persistence.*;

@Entity
public class List01 implements Serializable {
    @Version
    int id;

    @OneToMany(mappedBy="foo")
    List<Bar> bars;

    public List01() {
        bars = new ArrayList<Bar>();
    }
}
@Entity
class Bar {
    @ManyToOne
    List01 foo;
    Bar(List01 foo) {
        this.foo = foo;
    }
}

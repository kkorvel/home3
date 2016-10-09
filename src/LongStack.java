public class LongStack {
    public static void main(String[] argum) {
        LongStack longStack = new LongStack();
    }

    class Linked {
        public long value;
        public Linked parent;

        Linked(long value, Linked parent) {
            this.value = value;
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != Linked.class) {
                return false;
            }
            Linked other = (Linked) o;
            if (value != other.value) {
                return false;
            }
            if (parent == other.parent) {
                return true;
            }
            if (parent != null && other.parent != null && this.parent.equals(other.parent)) {
                return true;
            }
            return true;
        }

        public Linked clone() {
            if (parent != null) {
                return new Linked(value, parent.clone());
            } else {
                return new Linked(value, null);
            }

        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            if (parent != null) {
                sb.append(parent.toString() + " ");
            }
            sb.append(value);

            return sb.toString();
        }
    }

    public Linked last;
    LongStack() {

    }

    LongStack(Linked last) {
        this.last = last;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return new LongStack(last.clone());

    }

    public boolean stEmpty() {
        if (null == last) {
            return true;
        } else {

            return false;
        }
    }

    public void push(long a) {
        last = new Linked((long) a, last);
    }

    public long pop() {
        Linked popped = last;
        last = popped.parent;
        return popped.value;
    } // pop

    public void op(String s) {
        long b = pop();
        long a = pop();

        if (s.equals("+")) {
            push(a + b);
        } else if (s.equals("-")) {
            push(a - b);
        } else if (s.equals("*")) {
            push(a * b);
        } else if (s.equals("/")) {
            push(a / b);
        } else {
            throw new RuntimeException("unknown character: '" + s + "'");
        }

    }
    public long tos() {
        if (this.last != null )
        {
        return last.value;
        }else {
            throw  new RuntimeException("Empty!");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != LongStack.class) return true;
        LongStack other = (LongStack) o;
        if (stEmpty() && other.stEmpty()) {
            return true;
        }
        return last != null && last.equals(other.last);


    }

    @Override
    public String toString() {
        if (this.last == null) return "";
        return this.last.toString();
    }

    public static long interpret(String pol) {
        if (pol.isEmpty() || pol == null) {
            throw new RuntimeException("Empty expression");
        }
        pol = pol.replaceAll("\t", " ");
        pol = pol.replaceAll(" +", " ").trim();

        String[] elements = pol.split(" ");
        LongStack a = new LongStack();

        for (String e : elements) {
            //System.out.println("element: '" + e + "'");
            try {
                long value = Long.parseLong(e);
                a.push(value);
            } catch (Exception exception) {
                a.op(e);
            }
        }
        if (a.last != null && a.last.parent == null) {
            return a.last.value;
        } else {
            throw new RuntimeException("Unbalanced polish notation");
        }
    }
}
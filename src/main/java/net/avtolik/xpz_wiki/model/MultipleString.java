package net.avtolik.xpz_wiki.model;

public class MultipleString {

    String value1;
    String value2;
    String value3;

    MultipleString(String val1, String val2, String val3) {
        this.value1 = val1;
        this.value2 = val2;
        this.value3 = val3;
    }
    MultipleString(String val1, String val2) {
        this.value1 = val1;
        this.value2 = val2;
    }
    MultipleString(String val) {
         this.value1 = val;
    }

    @Override
    public String toString() {
        if (value2 == null & value3 == null) 
            return value1;

        return "[" + value1 + ", " + value2 + ", " + value3 + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MultipleString other = (MultipleString) obj;
        if (value1 == null) {
            if (other.value1 != null)
                return false;
        } else if (!value1.equals(other.value1))
            return false;
        if (value2 == null) {
            if (other.value2 != null)
                return false;
        } else if (!value2.equals(other.value2))
            return false;
        if (value3 == null) {
            if (other.value3 != null)
                return false;
        } else if (!value3.equals(other.value3))
            return false;
        return true;
    }

    
}

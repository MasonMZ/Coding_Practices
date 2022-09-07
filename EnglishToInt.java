/** Class that convert English strings (lower case) to representing
 integers by adding characters scaled by powers of 27. */

public class EnglishToInt {
    public static int to_int(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        } else {
            int res = 0;
            for (int i = 0; i < s.length(); i += 1) {
                res += char_to_num(s.charAt(i)) * Math.pow(27,  (s.length() - i - 1));
            }
            return res;
        }
    }

    public static int char_to_num(Character s) {
        String template = "abcdefghijklmnopqrstuvwxyz";
        for (int k = 0; k < template.length(); k += 1) {
            if (s == template.charAt(k)) {
                return k + 1;
            }
        }
        return 0; // no character found
    }

    public static void main(String[] args) {
        System.out.println("a: " + to_int("a"));
        System.out.println("z: " + to_int("z"));
        System.out.println("aa: " + to_int("aa"));
        System.out.println("bee: " + to_int("bee"));
        System.out.println("cat: " + to_int("cat"));
        System.out.println("dog: " + to_int("dog"));
        System.out.println("potato: " + to_int("potato"));
    }
}

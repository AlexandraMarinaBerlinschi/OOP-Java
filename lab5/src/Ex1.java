public class Ex1 {
    public static void main(String[] args) {
        System.out.println("a".matches("[abc]"));
        System.out.println("d".matches("[^abc]"));
        System.out.println("m".matches("[a-z]"));
        System.out.println("G".matches("[a-zA-Z]"));
        System.out.println("hT".matches("[a-z][A-Z]"));
        System.out.println("abac".matches("[abc]+"));
        System.out.println("".matches("[abc]*"));
        System.out.println("abbcc".matches("[abc]{5}"));
        System.out.println("abcccc".matches("[abc]{5,}"));
        System.out.println("");
    }
}
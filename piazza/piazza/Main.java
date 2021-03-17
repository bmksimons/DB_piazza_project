package piazza;

public class Main {

    public static void main(String[] args) {
        PiazzaCtrl piazzaCtrl = new PiazzaCtrl();
        piazzaCtrl.logIn("ellen@hotmail.com", "123");
        piazzaCtrl.createPost("Lage en post", "Lurer på om jeg fikk det til",
        		null, null);
    }
}

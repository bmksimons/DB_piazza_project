package piazza;

public class Main {

    public static void main(String[] args) {
        PiazzaCtrl piazzaCtrl = new PiazzaCtrl();
        piazzaCtrl.logIn("ellen@hotmail.com", "123");
        piazzaCtrl.createNewPost("Lage en post", "Lurer på om jeg fikk det til",
        		null, null);
        piazzaCtrl.createNewPost("Lage post nr 2", "Tester om det funker med nr 2 også", null, null);
    }
}

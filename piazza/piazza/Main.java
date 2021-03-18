package piazza;

public class Main {

    public static void main(String[] args) {
        PiazzaCtrl piazzaCtrl = new PiazzaCtrl();
        piazzaCtrl.logIn("ellen@hotmail.com", "123");
        //piazzaCtrl.logIn("beate.kanutte@gmail.com", "HeiHei");
        //piazzaCtrl.createThread("Ny Thread", "funker dette", "Question", "beate.kanutte@gmail.com", "Exam");
        //piazzaCtrl.createPostInThread(1, "Ny post", "blaa", "beate.kanutte@gmail.com");
        System.out.println(piazzaCtrl.searchForKeyword("Hei"));
    }
}

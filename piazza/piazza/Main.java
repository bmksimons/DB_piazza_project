package piazza;

public class Main {

    public static void main(String[] args) {
    
        PiazzaCtrl piazzaCtrl = new PiazzaCtrl();
        //piazzaCtrl.logIn("ellen@hotmail.com", "123");
        piazzaCtrl.logIn("beate.kanutte@gmail.com", "HeiHei");
        piazzaCtrl.createFirstPostInThread("Ny Thread", "Hei", "Question", "beate.kanutte@gmail.com", "Exam");
        piazzaCtrl.createReply("Svar", "Dette er et svar", 1, "hallvar@gmail.com");
        System.out.println(piazzaCtrl.searchForKeyword("Hei"));
        piazzaCtrl.viewStatistics();
    }
}

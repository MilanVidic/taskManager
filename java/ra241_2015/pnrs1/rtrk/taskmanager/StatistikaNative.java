package ra241_2015.pnrs1.rtrk.taskmanager;


public class StatistikaNative {

    public native int izracunajStatistiku(int ukupanBr, int zavrsenBr);

    static {
        System.loadLibrary("statistika");
    }
}

package jobs;

import beans.Coupon;
import dao.CouponsDAO;
import dao.CouponsDAOImpl;
import facade.ClientFacade;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class DailyRemoverExpiredCoupons implements Runnable {

    private boolean quitFlag = false;
    private static CouponsDAO couponsDAO = new CouponsDAOImpl();

    private final int DAY = 86_400_000;

    public DailyRemoverExpiredCoupons() {
        this.couponsDAO = couponsDAO;
    }

    @Override
    public void run() {
        while (!quitFlag) {
            try {
                for (Coupon c : couponsDAO.getAllExpired()) {
                    couponsDAO.deleteCouponPurchaseByCouponId(c.getId());
                    couponsDAO.delete(c.getId());
                    System.out.println("coupon: "+c.getTitle()+" has successfully deleted");
                    break;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                Thread.sleep(DAY);
            } catch (InterruptedException ignored) {

            }
        }
    }


    public void stop() {

        this.quitFlag = true;
    }
}

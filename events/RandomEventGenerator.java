package events;

import ship.Ship;
import characters.Crewmember;
import goods.GoodsForSale;
import goods.PurchasedGoods;

public class RandomEventGenerator {

        public void generateEvent(Ship player) {
            ShipwreckEvent event = new ShipwreckEvent();
            EventOutcome outcome = event.transpire();
            sleep(2);
            for (GoodsForSale g : outcome.getGoodsReward()) {
                //player.getCargoBay().addCargo(g);
                if (!player.getCargoBay().isFull()) {
                    player.getCargoBay().addCargo(new PurchasedGoods(g,1,null));
                    System.out.println("Adding " + g.name + ".");
                } else System.out.println("No space for " + g.name + ".");
            }
            for (Crewmember c : outcome.getCrewReward()) {
                player.getCrew().add(c);
                System.out.println("Adding to crew.");
            }
            int newBalance = player.getMoney() + outcome.getMoneyReward();
            System.out.println("CREDS " + player.getMoney() + " --> " + newBalance);
            sleep(2);

            player.setMoney(newBalance);
            printTwoRows();
        }

        // TODO: Refactor these to a console or view class
        private void sleep(int seconds) {
            int milliseconds = seconds * 1000;
            try { Thread.sleep(milliseconds); } catch (Exception e) { e.printStackTrace(); }
        }

        private void printTwoRows() {
            System.out.println();
            System.out.println();
        }

}

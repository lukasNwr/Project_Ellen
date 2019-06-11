package sk.tuke.kpi.oop.game.Prototype;

import java.util.HashMap;
import java.util.Map;

public class MoneyFactory {
    private Map<MoneyType, Money> templates;

    public MoneyFactory() {
        this.templates = new HashMap<>();
        this.templates.put(MoneyType.COIN, new Money(MoneyType.COIN, 10));
        this.templates.put(MoneyType.BILL, new Money(MoneyType.BILL, 50));
    }

    public Money createMoney(MoneyType type) {
        return (Money)this.templates.get(type).createClone();
    }
}

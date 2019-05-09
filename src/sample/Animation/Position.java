package sample.Animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Position { // Данный класс реализует простую анимацию перемещения объекта

    private TranslateTransition translateTransition;

    public void changePositionX(Node node) {
        translateTransition = new TranslateTransition(Duration.millis(120), node);
        translateTransition.setFromX(0);
        translateTransition.setByX(10);
        translateTransition.setCycleCount(6);
        translateTransition.setAutoReverse(true);
        translateTransition.playFromStart();
    }

    /**
     * Перемещение объекта
     * от начального нулевого положения
     * на 10 пикселей, затем его возвращение
     * в исходное положение и повтор действия 6 раз
     * **/
}

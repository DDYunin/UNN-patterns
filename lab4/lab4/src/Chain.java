import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chain implements ICurve {
    private List<ICurve> curves = new ArrayList<>();

    public Chain(ICurve... curves) {
        // Проверяем совместимость кривых
        for (int i = 0; i < curves.length - 1; i++) {
            IPoint endPoint = new Point(0, 0);
            curves[i].getPoint(1, endPoint);

            IPoint startPoint = new Point(0, 0);
            curves[i+1].getPoint(0, startPoint);
        }

        Collections.addAll(this.curves, curves);
    }

    @Override
    public void getPoint(double t, IPoint p) {
        int segmentCount = curves.size();
        double segmentLength = 1.0 / segmentCount;

        // Определяем, какой сегмент кривой нужно использовать
        int segmentIndex = (int) (t * segmentCount);
        if (segmentIndex >= segmentCount) {
            segmentIndex = segmentCount - 1;
        }

        // Нормализуем t для выбранного сегмента
        double segmentT = (t - segmentIndex * segmentLength) * segmentCount;
        curves.get(segmentIndex).getPoint(segmentT, p);
    }
}
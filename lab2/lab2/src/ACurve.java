public abstract class ACurve implements ICurve {
    protected IPoint a;
    protected IPoint b;

    public ACurve(IPoint a, IPoint b) {
        this.a = a;
        this.b = b;
    }

    // Абстрактный метод для получения точки на кривой
    @Override
    public abstract void getPoint(double t, IPoint p);
}

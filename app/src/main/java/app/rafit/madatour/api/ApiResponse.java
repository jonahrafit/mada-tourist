package app.rafit.madatour.api;
public class ApiResponse<T> {
    public T data;
    public Throwable error;

    public ApiResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.data = null;
    }
}
